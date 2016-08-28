package ua.gram.controller.stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.event.LevelFinishedEvent;
import ua.gram.controller.event.PlayerDefeatedEvent;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Initializer;
import ua.gram.model.actor.misc.CounterButton;
import ua.gram.model.group.GameUIGroup;
import ua.gram.model.group.TowerControls;
import ua.gram.model.level.Level;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.ui.CounterButtonPrototype;
import ua.gram.model.prototype.ui.UIPrototype;
import ua.gram.model.window.DefeatWindow;
import ua.gram.model.window.PauseWindow;
import ua.gram.model.window.VictoryWindow;
import ua.gram.utils.Log;
import ua.gram.view.screen.GameScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class UIStage extends AbstractStage implements Initializer {

    private final DDGame game;
    private final Level level;
    private final PauseWindow pauseWindow;
    private final VictoryWindow victoryWindow;
    private final DefeatWindow defeatWindow;
    private final GameUIGroup gameUIGroup;
    private final CounterButton counter;
    private TowerControls towerControls;

    public UIStage(DDGame game, Level level, UIPrototype prototype) {
        super(game);
        this.game = game;
        this.level = level;

        gameUIGroup = new GameUIGroup(game, level);
        victoryWindow = new VictoryWindow(game, level, prototype.getWindow("victory"));
        pauseWindow = new PauseWindow(game);
        defeatWindow = new DefeatWindow(game, prototype.getWindow("defeat"));

        CounterButtonPrototype counterPrototype = game.getPrototype().levelConfig.counterButtonConfig;

        counter = new CounterButton(game, level, counterPrototype);

        Log.info("UIStage is OK");
    }

    @Override
    public void init() {
        Vector2 pos = level.getMap().getSpawn().getPosition();
        if (pos == null) throw new GdxRuntimeException("Missing map spawn point");

        counter.setPosition(
                pos.x * DDGame.TILE_HEIGHT + 10,
                pos.y * DDGame.TILE_HEIGHT + 10
        );

        gameUIGroup.setVisible(true);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
        defeatWindow.setVisible(false);

        debugListener.setStageHolder(stageHolder);

        gameUIGroup.setStageHolder(stageHolder);
        gameUIGroup.init();

        defeatWindow.setStageHolder(stageHolder);
        defeatWindow.init();

        pauseWindow.setStageHolder(stageHolder);
        pauseWindow.init();

        counter.init();

        TowerShopConfigPrototype config = game.getPrototype().levelConfig.towerShopConfig;
        TowerShop towerShop = new TowerShop(game, stageHolder, config);
        towerShop.init();
        towerShop.getTowerShopGroup().setVisible(true);

        addActor(gameUIGroup);
        addActor(victoryWindow);
        addActor(pauseWindow);
        addActor(defeatWindow);
        addActor(counter);

        gameUIGroup.add(towerShop.getTowerShopGroup())
                .colspan(3)
                .expand()
                .align(Align.bottomRight);
        Log.info("UIStage is initialized");
    }

    public void moveBy(float x, float y) {
        gameUIGroup.addAction(Actions.moveBy(x, y));
        victoryWindow.addAction(Actions.moveBy(x, y));
        defeatWindow.addAction(Actions.moveBy(x, y));
        pauseWindow.addAction(Actions.moveBy(x, y));
    }

    public void moveTo(float x, float y) {
        gameUIGroup.addAction(Actions.moveTo(
                x - (game.getCamera().viewportWidth / 2f),
                y - (game.getCamera().viewportHeight / 2f)));
        victoryWindow.addAction(Actions.moveTo(
                x - (victoryWindow.getWidth() / 2f),
                y - (victoryWindow.getHeight() / 2f)));
        defeatWindow.addAction(Actions.moveTo(
                x - (defeatWindow.getWidth() / 2f),
                y - (defeatWindow.getHeight() / 2f)));
        pauseWindow.addAction(Actions.moveTo(
                x - (pauseWindow.getWidth() / 2f),
                y - (pauseWindow.getHeight() / 2f)));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebugAll(DDGame.DEBUG);
        if (!DDGame.PAUSE && !level.isInterrupted()) {
            if (level.isDefeated()) {
                Log.info("Player is dead");
                level.setInterrupted(true);
                stageHolder.fire(new PlayerDefeatedEvent());
            } else if (level.isVictorious()) {
                Log.info("Player is victorious");
                level.setInterrupted(true);
                stageHolder.fire(new LevelFinishedEvent());
            }
        }
    }

    public TowerControls getTowerControls() {
        return towerControls;
    }

    public void setTowerControls(TowerControls controls) {
        if (towerControls != null)
            throw new IllegalArgumentException("Tower controls are already initialized");
        towerControls = controls;
        controls.setVisible(false);
        addActor(controls);
    }

    public Level getLevel() {
        return level;
    }

    public GameUIGroup getGameUIGroup() {
        return gameUIGroup;
    }

    public void showDefeatWindow() {
        disableNonUiProcessors();
        hideAllWindows();
        hideUI();
        defeatWindow.dispose();
        defeatWindow.setVisible(true);
    }

    public void hideDefeatWindow() {
        enableNonUiProcessors();
        showUI();
        defeatWindow.setVisible(false);
    }

    public void hideVictoryWindow() {
        enableNonUiProcessors();
        showUI();
        victoryWindow.setVisible(false);
    }

    public void hidePauseWindow() {
        enableNonUiProcessors();
        showUI();
        pauseWindow.setVisible(false);
    }

    public void showVictoryWindow() {
        disableNonUiProcessors();
        hideAllWindows();
        hideUI();
        victoryWindow.setVisible(true);
    }

    public void showPauseWindow() {
        disableNonUiProcessors();
        hideAllWindows();
        hideUI();
        pauseWindow.setVisible(true);
    }

    public void hideAllWindows() {
        defeatWindow.setVisible(false);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
    }

    public CounterButton getCounter() {
        return counter;
    }

    public void disableNonUiProcessors() {
        GameScreen screen = stageHolder.getGameScreen();
        screen.disableCameraProcessor();
        screen.disableBattleStageProcessor();
    }

    public void enableNonUiProcessors() {
        GameScreen screen = stageHolder.getGameScreen();
        screen.enableCameraListener();
        screen.enableBattleStageProcessor();
    }

    public void hideUI() {
        gameUIGroup.setVisible(false);
        counter.setVisible(false);
    }

    public void showUI() {
        gameUIGroup.setVisible(true);
        if (!counter.hasFinished()) {
            counter.setVisible(true);
        }
    }
}

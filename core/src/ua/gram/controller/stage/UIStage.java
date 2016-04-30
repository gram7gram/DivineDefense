package ua.gram.controller.stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
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

/**
 * TODO Display Current levelConfig at the beginning.
 * FIXME Change stats representation.
 *
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
    private TowerShop towerShop;
    private TowerControls towerControls;

    public UIStage(DDGame game, Level level, UIPrototype prototype) {
        super(game);
        this.game = game;
        this.level = level;

        gameUIGroup = new GameUIGroup(game, level);
        victoryWindow = new VictoryWindow(game, level, prototype.getWindow("victory"));
        pauseWindow = new PauseWindow(game);
        defeatWindow = new DefeatWindow(game, prototype.getWindow("defeat"));

        gameUIGroup.setVisible(true);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
        defeatWindow.setVisible(false);

        Vector2 pos = level.getMap().getSpawn().getPosition();
        if (pos == null) throw new GdxRuntimeException("Missing map spawn point");

        CounterButtonPrototype counterPrototype = game.getPrototype().levelConfig.counterButtonConfig;
        counterPrototype.tilePosition = new Vector2(pos.x, pos.y);

        counter = new CounterButton(game, level, counterPrototype);

        Log.info("UIStage is OK");
    }

    @Override
    public void init() {
        debugListener.setStageHolder(stageHolder);

        gameUIGroup.setStageHolder(stageHolder);
        gameUIGroup.init();

        defeatWindow.setStageHolder(stageHolder);
        defeatWindow.init();

        pauseWindow.setStageHolder(stageHolder);
        pauseWindow.init();

        counter.init();

        TowerShopConfigPrototype config = game.getPrototype().levelConfig.towerShopConfig;
        towerShop = new TowerShop(game, stageHolder, config);
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

    /**
     * Hides/Shows the window, shows/hides other ui elements.
     *
     * @param window show/hide this
     */
    public void toggleWindow(Actor window) {
        Log.info("Pause: " + DDGame.PAUSE);
        window.setVisible(!window.isVisible());
        gameUIGroup.setVisible(!window.isVisible());
        towerShop.getTowerShopGroup().setVisible(!window.isVisible());
        BattleStage battleStage = stageHolder.getBattleStage();
        if (window.isVisible()) {
            battleStage.removeListener(battleStage.getControlsListener());
        } else {
            battleStage.addListener(battleStage.getControlsListener());
        }
        Log.info(window.getClass().getSimpleName() + " is " + (window.isVisible() ? "" : "in") + "visible");
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
        if (!DDGame.PAUSE) {
            if (level.isDefeated() && !victoryWindow.isVisible()) {
                Log.info("Player is dead");
                DDGame.PAUSE = true;
                toggleDefeatWindow();
            } else if (level.isVictorious() && !defeatWindow.isVisible()) {
                Log.info("Player is victorious");
                for (Actor actor : stageHolder.getBattleStage().getActors()) {
                    if (!(actor instanceof Image)) {
                        actor.remove();
                    }
                }
                DDGame.PAUSE = true;
                toggleVictoryWindow();
            }
        }
    }

    public TowerControls getTowerControls() {
        return towerControls;
    }

    public void setTowerControls(TowerControls controls) {
        if (towerControls != null) throw new IllegalArgumentException("Already initialized");
        towerControls = controls;
        controls.setVisible(false);
        addActor(controls);
    }

    public Level getLevel() {
        return level;
    }

    public PauseWindow getPauseWindow() {
        return pauseWindow;
    }

    public GameUIGroup getGameUIGroup() {
        return gameUIGroup;
    }

    public void toggleDefeatWindow() {
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
        defeatWindow.resetObject();
        defeatWindow.setVisible(!defeatWindow.isVisible());
    }

    public void toggleVictoryWindow() {
        defeatWindow.setVisible(false);
        pauseWindow.setVisible(false);
        victoryWindow.setVisible(!victoryWindow.isVisible());
    }

    public void togglePauseWindow() {
        defeatWindow.setVisible(false);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(!pauseWindow.isVisible());
    }

    public CounterButton getCounter() {
        return counter;
    }
}

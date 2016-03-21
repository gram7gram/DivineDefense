package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Initializer;
import ua.gram.model.Level;
import ua.gram.model.group.GameUIGroup;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.prototype.UIPrototype;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.window.DefeatWindow;
import ua.gram.model.window.PauseWindow;
import ua.gram.model.window.VictoryWindow;

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
    private TowerShop towerShop;
    private TowerControlsGroup towerControls;

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
        TowerShopConfigPrototype config = game.getPrototype().levelConfig.towerShopConfig;
        towerShop = new TowerShop(game, stageHolder, config);
        towerShop.init();
        towerShop.getTowerShopGroup().setVisible(true);
        addActor(gameUIGroup);
        addActor(victoryWindow);
        addActor(pauseWindow);
        addActor(defeatWindow);
        addActor(towerShop.getTowerShopGroup());
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

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebugAll(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            if (level.isDefeated() && !victoryWindow.isVisible()) {
                Log.info("Player is dead");
                DDGame.PAUSE = true;
                toggleWindow(defeatWindow);
            } else if (level.isVictorious() && !defeatWindow.isVisible()) {
                Log.info("Player is victorious");
                stageHolder.getBattleStage().clear();
                DDGame.PAUSE = true;
                toggleWindow(victoryWindow);
            }
        }
    }

    public TowerControlsGroup getTowerControls() {
        return towerControls;
    }

    public void setTowerControls(TowerControlsGroup controls) {
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

    public TowerShop getTowerShop() {
        return towerShop;
    }

    public void hideAllWindows() {
        pauseWindow.setVisible(false);
        victoryWindow.setVisible(false);
        defeatWindow.setVisible(false);
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
}

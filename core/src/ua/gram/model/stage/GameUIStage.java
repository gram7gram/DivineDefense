package ua.gram.model.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Level;
import ua.gram.model.group.GameUIGroup;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.window.DefeatWindow;
import ua.gram.model.window.PauseWindow;
import ua.gram.model.window.VictoryWindow;

/**
 * TODO Display Current levelConfig at the beginning.
 * FIXME Change stats representation.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIStage extends AbstractStage {

    private final DDGame game;
    private final GameUIGroup gameUIGroup;
    private final Level level;
    private final PauseWindow pauseWindow;
    private final VictoryWindow victoryWindow;
    private final DefeatWindow defeatWindow;
    private TowerShop towerShop;
    private GameBattleStage stage_battle;
    private TowerControlsGroup towerControls;

    public GameUIStage(DDGame game, Level level) {
        super(game);
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;
        this.level = level;
        gameUIGroup = new GameUIGroup(game, this, level);
        victoryWindow = new VictoryWindow(game, level);
        pauseWindow = new PauseWindow(game, this);
        defeatWindow = new DefeatWindow(game, this);
        gameUIGroup.setVisible(true);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
        defeatWindow.setVisible(false);
        this.addActor(gameUIGroup);
        this.addActor(victoryWindow);
        this.addActor(pauseWindow);
        this.addActor(defeatWindow);
        Gdx.app.log("INFO", "GameUIStage is OK");
    }

    /**
     * Hides/Shows the window, shows/hides other ui elements.
     *
     * @param window show/hide this
     */
    public void toggleWindow(Window window) {
        Gdx.app.log("INFO", "Pause: " + DDGame.PAUSE);
        window.setVisible(!window.isVisible());
        gameUIGroup.setVisible(!window.isVisible());
        towerShop.getTowerShopGroup().setVisible(!window.isVisible());
        if (window.isVisible()) {
            if (window instanceof DefeatWindow) {
                ((DefeatWindow) window).update();
            }
            stage_battle.removeListener(stage_battle.getControlsListener());
        } else {
            stage_battle.addListener(stage_battle.getControlsListener());
        }
        Gdx.app.log("INFO", window.getClass().getSimpleName() + " is " + (window.isVisible() ? "" : "in") + "visible");
    }


    /**
     * Checks if Player is Dead or Victorious.
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDebugAll(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            if (level.isDefeated() && !victoryWindow.isVisible()) {
                Gdx.app.log("INFO", "Player is dead");
                DDGame.PAUSE = true;
                toggleWindow(defeatWindow);
            } else if (level.isVictorious() && !defeatWindow.isVisible()) {
                Gdx.app.log("INFO", "Player is victorious");
                stage_battle.clear();
                DDGame.PAUSE = true;
                toggleWindow(victoryWindow);
            }
        }
    }

    public TowerControlsGroup getTowerControls() {
        return towerControls;
    }

    public void setTowerControls(TowerControlsGroup towerControls) {
        this.towerControls = towerControls;
        towerControls.setVisible(false);
        this.addActor(towerControls);
    }

    public GameBattleStage getBattleStage() {
        return stage_battle;
    }

    public void setBattleStage(GameBattleStage stage_battle) {
        this.stage_battle = stage_battle;
        towerShop = new TowerShop(game, stage_battle, this);
        towerShop.getTowerShopGroup().setVisible(true);
        this.addActor(towerShop.getTowerShopGroup());
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
}

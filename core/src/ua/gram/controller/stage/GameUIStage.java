package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Level;
import ua.gram.model.group.GameUIGroup;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.view.window.DefeatWindow;
import ua.gram.view.window.PauseWindow;
import ua.gram.view.window.VictoryWindow;

/**
 * FIXME Change stats representation.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class GameUIStage extends Stage {

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
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.game = game;
        this.level = level;
        gameUIGroup = new GameUIGroup(game, this, level);
        victoryWindow = new VictoryWindow(game);
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
//    	float duration = .2f;
//    	window.addAction(window.isVisible() ? Actions.alpha(0, duration) : Actions.alpha(1, duration));
//    	gameUIGroup.addAction(window.isVisible() ? Actions.alpha(1, duration) : Actions.alpha(0, duration));
//    	towerShopGroup.addAction(window.isVisible() ? Actions.alpha(1, duration) : Actions.alpha(0, duration));
        if (window instanceof DefeatWindow && window.isVisible()) {
            ((DefeatWindow) window).update();
        }
        Gdx.app.log("INFO", window.getClass().getSimpleName() + " is " + (window.isVisible() ? "" : "in") + "visible");
    }


    /**
     * Checks if Player is Dead or Victorious.
     * FIXME Window is displayed at the moment the last wave has started
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        if (!DDGame.PAUSE) {
            super.act(delta);
            if (isDefeated()) {
                Gdx.app.log("INFO", "Player is dead");
                DDGame.PAUSE = true;
                toggleWindow(defeatWindow);
            } else if (isVictorious()) {
                Gdx.app.log("INFO", "Player is victorious");
                stage_battle.clear();
                DDGame.PAUSE = true;
                toggleWindow(victoryWindow);
            }
        }
    }

    private boolean isVictorious() {
        return !game.getPlayer().isDead()
                && level.isCleared
                && !stage_battle.hasEnemiesOnMap()
                && !victoryWindow.isVisible();
    }

    private boolean isDefeated() {
        return game.getPlayer().isDead()
                && !defeatWindow.isVisible();
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

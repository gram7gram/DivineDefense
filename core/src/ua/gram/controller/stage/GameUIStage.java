package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Level;
import ua.gram.view.group.GameUIGroup;
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
    private final TowerShop towerShop;
    private final GameBattleStage stage_battle;
    private final GameUIGroup gameControlsGroup;
    private final Level level;
    private final PauseWindow pauseWindow;
    private final VictoryWindow victoryWindow;
    private final DefeatWindow defeatWindow;

    public GameUIStage(DDGame game, GameBattleStage stage_battle, Level level) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        this.stage_battle = stage_battle;
        this.game = game;
        this.level = level;
        gameControlsGroup = new GameUIGroup(game, this, level);
        towerShop = new TowerShop(game, this);
        victoryWindow = new VictoryWindow(game);
        pauseWindow = new PauseWindow(game, "menu", this);
        defeatWindow = new DefeatWindow(game, this);
        gameControlsGroup.setVisible(true);
        victoryWindow.setVisible(false);
        pauseWindow.setVisible(false);
        defeatWindow.setVisible(false);
        towerShop.getTowerShopGroup().setVisible(true);
        this.addActor(towerShop.getTowerShopGroup());
        this.addActor(gameControlsGroup);
        this.addActor(victoryWindow);
        this.addActor(pauseWindow);
        this.addActor(defeatWindow);
        Gdx.app.log("INFO", "Game controls are OK");
    }

    /**
     * Hides/Shows the window, shows/hides other ui elements.
     *
     * @param window window to show/hide
     */
    public void toggleWindow(Window window) {
        DDGame.PAUSE = !DDGame.PAUSE;
        Gdx.app.log("INFO", "Pause: " + DDGame.PAUSE);
        window.setVisible(!window.isVisible());
        gameControlsGroup.setVisible(!window.isVisible());
        towerShop.getTowerShopGroup().setVisible(!window.isVisible());
//	float duration = .2f;
//	window.addAction(window.isVisible() ? Actions.alpha(0, duration) : Actions.alpha(1, duration));
//	gameControlsGroup.addAction(window.isVisible() ? Actions.alpha(1, duration) : Actions.alpha(0, duration));
//	towerShopGroup.addAction(window.isVisible() ? Actions.alpha(1, duration) : Actions.alpha(0, duration));
        if (window instanceof DefeatWindow && window.isVisible()) {
            ((DefeatWindow) window).update();
        }
        Gdx.app.log("INFO", window.getClass().getSimpleName() + " is " + (window.isVisible() ? "" : "in") + "visible");
    }

    /**
     * <pre>
     * Checks if Player is Dead or Victorious
     *
     * TODO Show options for Player, if he is dead:
     * 		Ressurect with max health: *** gems
     * 		Ressurect with 2 health: ** gems
     * 		Restart: Free.
     *
     * FIXME Window is displayed at the moment the last wave has started
     * </pre>
     *
     * @param delta
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (isDefeated()) {
                Gdx.app.log("INFO", "Player is dead");
//                stage_battle.clear();
                toggleWindow(defeatWindow);
            } else if (isVictorious()) {
                Gdx.app.log("INFO", "Player is victorious");
                stage_battle.clear();
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

    public GameBattleStage getBattleStage() {
        return stage_battle;
    }

    public Level getLevel() {
        return level;
    }

    public PauseWindow getPauseWindow() {
        return pauseWindow;
    }

    public GameUIGroup getGameControlsGroup() {
        return gameControlsGroup;
    }

}

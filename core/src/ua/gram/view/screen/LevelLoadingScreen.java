package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.Level;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads resources essential for the game process itself: map, external sprites - and creates a Level.
 * TODO implement hints for player
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelLoadingScreen extends AbstractLoadingScreen {

    private final Level level;

    public LevelLoadingScreen(DDGame game, Level level) {
        super(game);
        this.level = level;
        Gdx.app.log("INFO", "LevelLoadingScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.log("INFO", "Screen set to LevelLoadingScreen");
        Resources resources = this.getGame().getResources();
        resources.loadMap(level.getCurrentLevel());
        resources.loadTexture(Resources.WEAPON_START_BACK);
        resources.loadTexture(Resources.WEAPON_START_OVER);
        resources.loadTexture(Resources.WEAPON_MIDDLE_BACK);
        resources.loadTexture(Resources.WEAPON_MIDDLE_OVER);
        resources.loadTexture(Resources.WEAPON_END_BACK);
        resources.loadTexture(Resources.WEAPON_END_OVER);
        resources.loadTexture(Resources.AIM_TEXTURE);
        resources.loadTexture(Resources.RANGE_TEXTURE);
    }

    @Override
    public void doAction() {
        stage_ui.update(progress);
        this.getGame().setScreen(new GameScreen(this.getGame(), level));
    }

}

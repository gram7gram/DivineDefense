package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.builder.LevelBuilder;
import ua.gram.model.Level;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads resources essential for the game process itself: map, external sprites - and creates a Level.
 * TODO implement hints for player
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelLoadingScreen extends AbstractLoadingScreen {

    private final int lvl;

    public LevelLoadingScreen(DDGame game, int lvl) {
        super(game);
        this.lvl = lvl;
        Gdx.app.log("INFO", "Screen set to LevelLoadingScreen");
    }

    @Override
    public void show() {
        super.show();
        Resources resources = game.getResources();
        resources.loadMap(lvl);
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
        Gdx.app.log("INFO", "Loading " + progress + "%");
        Level level = LevelBuilder.create(lvl, 1);
        level.create(game, lvl);
        game.setScreen(new GameScreen(game, level));
    }

}

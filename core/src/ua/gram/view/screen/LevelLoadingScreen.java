package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.factory.LevelFactory;
import ua.gram.model.Level;
import ua.gram.view.AbstractLoadingScreen;

/**
 * <pre>
 * TODO implement hints for player
 * TODO Add progress bar
 * </pre>
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

    /**
     * NOTE: It is essential to call super.show() for stage to be created
     */
    @Override
    public void show() {
        super.show();
        game.getResources().loadMap(lvl);
        game.getResources().loadAtlas(Resources.ENEMIES_ATLAS);
        game.getResources().loadAtlas(Resources.TOWERS_ATLAS);
        game.getResources().loadTexture(Resources.LASER_START_BACK);
        game.getResources().loadTexture(Resources.LASER_START_OVER);
        game.getResources().loadTexture(Resources.LASER_MIDDLE_BACK);
        game.getResources().loadTexture(Resources.LASER_MIDDLE_OVER);
        game.getResources().loadTexture(Resources.LASER_END_BACK);
        game.getResources().loadTexture(Resources.LASER_END_OVER);
        game.getResources().loadTexture(Resources.RANGE_TEXTURE);
    }

    @Override
    public void doAction() {
        stage_ui.update(progress);
        Gdx.app.log("INFO", "Loading " + progress + "%");
        try {
            LevelFactory container = game.getFactory("data/levels/level" + lvl + ".json", LevelFactory.class);
            Level level = container.create(Level.class);
            level.create(game, lvl);
            game.setScreen(new GameScreen(game, level));
        } catch (NullPointerException e) {
            System.out.println(e + "\n" + e.getMessage() + Arrays.toString(e.getStackTrace()));
            game.setScreen(new ErrorScreen(game, "Could not get container of level " + lvl, e));
        }
    }

}

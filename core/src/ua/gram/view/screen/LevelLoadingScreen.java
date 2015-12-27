package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.Level;
import ua.gram.model.prototype.LevelConfigPrototype;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads resources essential for the game process itself: map, external sprites - and creates a Level.
 * TODO implement hints for player
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelLoadingScreen extends AbstractLoadingScreen {

    private final LevelConfigPrototype config;
    private final LevelPrototype prototype;

    public LevelLoadingScreen(DDGame game, LevelPrototype prototype) {
        super(game);
        this.config = game.getPrototype().level;
        this.prototype = prototype;
        Gdx.app.log("INFO", "LevelLoadingScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.log("INFO", "Screen set to LevelLoadingScreen");
        Resources resources = this.getGame().getResources();
        resources.loadMap(prototype.map.name);
        for (String resource : config.resources) {
            resources.loadTexture(resource);
        }
    }

    @Override
    public void doAction() throws Exception {
        stage_ui.update(progress);
        Level level = new Level(game, prototype);
        this.getGame().setScreen(new GameScreen(this.getGame(), level));
    }

}

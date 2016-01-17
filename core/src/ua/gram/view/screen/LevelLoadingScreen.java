package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.Log;
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
        this.config = game.getPrototype().levelConfig;
        this.prototype = prototype;
        Log.info("LevelLoadingScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        loadResources();
    }

    private void loadResources() {
        Resources resources = game.getResources();
        resources.loadMap(prototype.map.name);
        for (String resource : config.resources) {
            resources.loadTexture(resource);
        }
        Log.info("Loaded nessesary " + config.resources.length + " recources for the level");
    }

    @Override
    public void doAction() throws NullPointerException {
        uiStage.update(progress);
        Level level = new Level(game, prototype);
        game.setScreen(new GameScreen(game, level));
    }

}

package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.factory.LevelFactory;
import ua.gram.model.level.Level;
import ua.gram.model.player.Player;
import ua.gram.model.prototype.SoundStatePrototype;
import ua.gram.model.prototype.level.LevelConfigPrototype;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;
import ua.gram.view.AbstractLoadingScreen;

/**
 * Loads resources essential for the game process itself:
 * map, external sprites - and creates a Level.
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
        loadGlobalResources();
        loadLevelResources();
        loadTowerResources();
    }

    private void loadLevelResources() {
        Resources resources = game.getResources();
        if (prototype.bosses == null) return;

        for (String boss : prototype.bosses) {
            resources.loadAtlas("data/spine/bosses/"
                    + Player.SYSTEM_FACTION + "/" + boss + "/skeleton");
        }
        Log.info("Loaded level resources");
    }

    private void loadGlobalResources() {
        Resources resources = game.getResources();
        resources.loadMap(prototype.map.name);
        if (config.commonResources == null) return;

        for (String resource : config.commonResources) {
            resources.loadTexture(resource);
        }
        Log.info("Loaded nessesary " + config.commonResources.length + " resources for the level");
    }

    private void loadTowerResources() {
        Resources resources = game.getResources();
        int resourceCount = 0;
        for (TowerPrototype towerPrototype : game.getPrototype().towers) {
            if (towerPrototype.sounds == null) {
                Log.warn("No sounds available for " + towerPrototype.name);
                continue;
            }

            for (SoundStatePrototype soundState : towerPrototype.sounds) {
                resources.loadSound(soundState.sound);
                ++resourceCount;
            }
        }
        Log.info("Loaded nessesary " + resourceCount + " resources for the towers");
    }

    @Override
    public void onLoad() {
        loadingStage.update(progress);
        Level level = LevelFactory.create(game, prototype);
        game.setScreen(new GameScreen(game, level));
    }

}

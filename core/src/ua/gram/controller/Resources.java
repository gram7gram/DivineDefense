package ua.gram.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.view.screen.ErrorScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Resources implements Disposable {

    private final DDGame game;
    private final Skin skin;
    private final AssetManager manager;
    public static final String TOWERS_ATLAS = "data/images/towers.atlas";
    public static final String ENEMIES_ATLAS = "data/images/enemies.atlas";
    public static final String SKIN_FILE = "data/skin/style.json";
    public static final String LOCK_IMAGE = "data/images/misc/lock.png";
    public static final String RANGE_TEXTURE = "data/images/misc/range.png";
    public static final String LASER_START_BACK = "data/images/misc/laser/start_back.png";
    public static final String LASER_START_OVER = "data/images/misc/laser/start_over.png";
    public static final String LASER_MIDDLE_BACK = "data/images/misc/laser/middle_back.png";
    public static final String LASER_MIDDLE_OVER = "data/images/misc/laser/middle_over.png";
    public static final String LASER_END_BACK = "data/images/misc/laser/end_back.png";
    public static final String LASER_END_OVER = "data/images/misc/laser/end_over.png";

    public Resources(DDGame game) {
        this.game = game;
        manager = new AssetManager();
        skin = loadSkin(SKIN_FILE);
    }

    public void loadBasicFiles() {
        loadFont("ActionManShaded", 32, "black");//loading label
        loadFont("SfArchery", 32, "white"); //button labels
        loadFont("SfArchery", 16, "black"); //button labels
        loadFont("FffTusj", 64, "white");//Big labels
//	loadFont("BelligerentMadness", 32, "black"); //some phrases?
    }

    /**
     * <pre>
     * Loads the JSON and corresponding Atlas files to AssetManager.
     * Atlas should be named same as the Json file: style.json and style.atlas.
     * </pre>
     *
     * @param file - name of the Json
     * @return - new Skin, build with Json and Atlas
     */
    public Skin loadSkin(String file) {
        String atlas = file.substring(0, file.lastIndexOf(".")) + ".atlas";
        try {
            manager.load(atlas, TextureAtlas.class);
            manager.load(file, Skin.class, new SkinLoader.SkinParameter(atlas));
            manager.finishLoading();
        } catch (GdxRuntimeException e) {
            game.createCamera();
            game.createViewport();
            game.createBatch();
            game.setScreen(new ErrorScreen(game, "Could not load skin " + file, e));
        }
        return manager.get(file, Skin.class);
    }

    public void loadFont(String fontName, int size, String color) {
        try {
            manager.load("data/skin/fonts/" + fontName + size + color + ".fnt", BitmapFont.class);
        } catch (GdxRuntimeException e) {
            game.setScreen(new ErrorScreen(game, "Could not load font: " + fontName, e));
        }
    }

    public void loadMap(int level) {
        try {
            manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            manager.load("data/levels/maps/level" + level + ".tmx", TiledMap.class);
        } catch (GdxRuntimeException e) {
            game.setScreen(new ErrorScreen(game, "Could not load map for level: " + level, e));
        }
    }

    public boolean isMapLoaded(byte level) {
        return manager.isLoaded("data/levels/maps/level" + level + ".tmx", TiledMap.class);
    }

    public void loadAtlas(String file) {
        try {
            manager.load(file, TextureAtlas.class);
        } catch (GdxRuntimeException e) {
            game.setScreen(new ErrorScreen(game, "Not loaded: " + file, e));
        }
    }

    public void loadTexture(String file) {
        try {
            manager.load(file, Texture.class);
        } catch (GdxRuntimeException e) {
            game.setScreen(new ErrorScreen(game, "Not loaded: " + file, e));
        }
    }

    public AssetManager getManager() {
        return manager;
    }

    public Skin getSkin() {
        return skin;
    }

    public TiledMap getMap(int level) {
        return manager.get("data/levels/maps/level" + level + ".tmx", TiledMap.class);
    }

    public boolean isAtlasLoaded(String file) {
        return manager.isLoaded(file, TextureAtlas.class);
    }

    public TextureAtlas getAtlas(String file) {
        return manager.get(file, TextureAtlas.class);
    }

    public Texture getTexture(String file) {
        return manager.get(file, Texture.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
        skin.dispose();
    }
}

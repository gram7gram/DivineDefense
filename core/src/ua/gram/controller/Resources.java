package ua.gram.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import ua.gram.DDGame;
import ua.gram.view.screen.ErrorScreen;

/**
 * NOTE Sprites should not be present in TextureAtlas: lock, weapon etc.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class Resources implements Disposable {

    public static final String SKIN_FILE = "data/skin/style.json";
    public static final String MARKET = "data/market/market.json";
    public static final String BACKGROUND_TEXTURE = "data/images/misc/background.jpg";
    public static final String WEAPON_START_BACK = "data/images/misc/start_back.png";
    public static final String WEAPON_START_OVER = "data/images/misc/start_over.png";
    public static final String WEAPON_MIDDLE_BACK = "data/images/misc/middle_back.png";
    public static final String WEAPON_MIDDLE_OVER = "data/images/misc/middle_over.png";
    public static final String WEAPON_END_BACK = "data/images/misc/end_back.png";
    public static final String WEAPON_END_OVER = "data/images/misc/end_over.png";
    public static final String RANGE_TEXTURE = "data/images/misc/enemy_range.png";
    public static final String AIM_TEXTURE = "data/images/misc/enemy_aim.png";
    public static final String LEVELS = "data/levels/levels.json";
    public static DDGame game;
    public static String TOWERS = "data/world/towers.json";
    public static String ENEMIES = "data/world/enemies.json";
    private final AssetManager manager;
    private Skin skin;

    public Resources() {
        manager = new AssetManager();
        skin = loadSkin(SKIN_FILE);
    }

    public static FileHandle loadFile(String path) {
        FileHandle handle = null;
        try {
            handle = new FileHandle(path);
        } catch (Exception e) {
            game.setScreen(new ErrorScreen(game, "Missing config file: \r\n" + path, e));
        }
        return handle;
    }

    /**
     * Loads the JSON and corresponding Atlas files to AssetManager.
     * Atlas should be named same as the Json file: style.json and style.atlas.
     * Names are accessible through static Strings in Resources class.
     * Will display ErrorScreen it was not able to load skin.
     *
     * @param file - name of the Json
     * @return - new Skin, build with Json and Atlas, that matches Json file without extension
     */
    public Skin loadSkin(String file) throws GdxRuntimeException {
        Skin skin = null;
        try {
            String atlas = file.substring(0, file.lastIndexOf(".")) + ".atlas";
            manager.load(file, Skin.class, new SkinLoader.SkinParameter(atlas));
            manager.finishLoading();
            skin = manager.get(file, Skin.class);
        } catch (Exception e) {
            Gdx.app.error("ERROR", "Missing skin file: \r\n" + file + "\r\n" + e);
            Gdx.app.exit();
        }
        return skin;
    }

    /**
     * Load tiled map for specified level by file.
     * Will display ErrorScreen it was not able to load map.
     */
    public void loadMap(String file) {
        try {
            manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            manager.load(file, TiledMap.class);
        } catch (GdxRuntimeException e) {
            if (game.getCamera() == null) createDisplayComponents();
            game.setScreen(new ErrorScreen(game, "Could not load map for level: " + file, e));
        }
    }

    /**
     * Load tiled map for specified level.
     * Will display ErrorScreen it was not able to load map.
     *
     * @param level level number [1..n]
     */
    public void loadMap(int level) {
        try {
            manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            manager.load("data/levels/maps/level" + level + "@60.tmx", TiledMap.class);
        } catch (GdxRuntimeException e) {
            if (game.getCamera() == null) createDisplayComponents();
            game.setScreen(new ErrorScreen(game, "Could not load map for level: " + level, e));
        }
    }

    /**
     * Load Texture for specified filename.
     * Names are accessible through static Strings in Resources class.
     * Will display ErrorScreen it was not able to load map.
     *
     * @param file texture location
     */
    public void loadTexture(String file) {
        try {
            manager.load(file, Texture.class);
        } catch (GdxRuntimeException e) {
            if (game.getCamera() == null) createDisplayComponents();
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
        return manager.get("data/levels/maps/level" + level + "@60.tmx", TiledMap.class);
    }

    public TiledMap getMap(String map) {
        return manager.get(map, TiledMap.class);
    }

    public Texture getTexture(String file) {
        return manager.get(file, Texture.class);
    }

    public Texture getAtlasRegion(String region) {
        return skin.getRegion(region).getTexture();
    }

    /**
     * If there was an error in loading the resources, and DDGame did not create
     * nor Camera, nor Viewport, nor Batch - this method will create them for you,
     * so that you are able to display ErrorScreen and did not terminate the application.
     */
    private void createDisplayComponents() {
        game.createCamera();
        game.createViewport();
        game.createBatch();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

}

package ua.gram.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.model.Initializer;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Resources implements Disposable, Initializer {

    public static final String BACKGROUND_TEXTURE = "data/images/misc/background.jpg";
    private final DDGame game;
    private final AssetManager manager;
    private final String root;
    private Skin skin;

    public Resources(DDGame game) {
        this(game, "");
    }

    public Resources(DDGame game, String root) {
        this.game = game;
        this.root = root;
        manager = new AssetManager();
    }

    @Override
    public void init() {
        loadTexture(BACKGROUND_TEXTURE);
        skin = loadSkin(game.getParameters().skin);

        manager.finishLoading();
    }

    /**
     * Loads the JSON and corresponding Atlas files to AssetManager.
     * Atlas should be named same as the Json file: style.json and style.atlas.
     * Names are accessible through static Strings in Resources class.
     * Will display ErrorScreen it was not able to load skin.
     *
     * @param file - name of the Json
     * @return - new Skin, buy with Json and Atlas, that matches Json file without extension
     */
    public Skin loadSkin(String file) {
        String atlas = game.getParameters().atlas;
        if (atlas == null) {
            String name = file.substring(0, file.lastIndexOf("."));
            atlas = name + ".atlas";
            Log.warn("Atlas file not specified. Trying: " + atlas);
        }

        manager.load(getRootDirectory() + file, Skin.class,
                new SkinLoader.SkinParameter(getRootDirectory() + atlas));
        Log.info("Loading skin:" + file);

        manager.finishLoading();

        return manager.get(getRootDirectory() + file, Skin.class);
    }

    /**
     * Load tiled map for specified levelConfig by file.
     * Will display ErrorScreen it was not able to load map.
     */
    public void loadMap(String file) {
        try {
            manager.setLoader(TiledMap.class, new TmxMapLoader());
            manager.load(getRootDirectory() + file, TiledMap.class);
            Log.info("Loading tiled map:" + file);
        } catch (GdxRuntimeException e) {
            Log.exc("Could not load map for level: " + file, e);
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
            manager.load(getRootDirectory() + file, Texture.class);
            Log.info("Loading texture:" + file);
        } catch (GdxRuntimeException e) {
            Log.exc("Not loaded: " + file, e);
        }
    }

    public void loadSound(String file) {
        try {
            manager.load(getRootDirectory() + file, Sound.class);
            Log.info("Loading sound:" + file);
        } catch (GdxRuntimeException e) {
            Log.exc("Not loaded: " + file, e);
        }
    }

    public void loadMusic(String file) {
        try {
            manager.load(getRootDirectory() + file, Music.class);
            Log.info("Loading music:" + file);
        } catch (GdxRuntimeException e) {
            Log.exc("Not loaded: " + file, e);
        }
    }

    public AssetManager getManager() {
        return manager;
    }

    public Skin getSkin() {
        return skin;
    }

    public TiledMap getMap(String map) {
        return manager.get(getRootDirectory() + map, TiledMap.class);
    }

    public Texture getRegisteredTexture(String file) {
        return manager.get(getRootDirectory() + file, Texture.class);
    }

    public Sound getRegisteredSound(String file) {
        return manager.get(getRootDirectory() + file, Sound.class);
    }

    public Music getRegisteredMusic(String file) {
        return manager.get(getRootDirectory() + file, Music.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

    public void loadAtlas(String resource) {
        loadAtlas(resource, false);
    }

    public void loadAtlas(String resource, boolean finishLoading) {
        try {
            manager.load(getRootDirectory() + resource + ".atlas", TextureAtlas.class);
        } catch (GdxRuntimeException e) {
            Log.exc("Not loaded: " + resource, e);
        }

        if (finishLoading) {
            manager.finishLoading();
        }
    }

    public TextureAtlas getAtlas(String name) {
        return manager.get(getRootDirectory() + name + ".atlas", TextureAtlas.class);
    }

    public String getRootDirectory() {
        return root != null && !root.equals("") ? root + "/" : "";
    }
}

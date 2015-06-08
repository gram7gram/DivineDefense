package ua.gram;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.gram.controller.Resources;
import ua.gram.controller.security.SecurityHandler;
import ua.gram.model.Player;
import ua.gram.view.screen.ErrorScreen;
import ua.gram.view.screen.LaunchLoadingScreen;

/**
 * TODO Make different assets for 4x3 and 16x9(16x10) screens
 * TODO For 16x9(16x10) make StretchViewport
 * TODO Add to JSON pressed and disabled drawable for buttons
 * TODO Add copyrights
 * TODO Handle Android Menu, Back click events
 *
 * FIXME Logotype
 * FIXME Switch 16pt font with 20pt
 *
 * NOTE Enemies should be tough because the amount of towers is unlimited
 * NOTE Skin should not contain nonexistent values
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class DDGame extends Game {

    public static final String ANGEL = "Angel";
    public static final String DEMON = "Demon";
    public static final byte TILE_HEIGHT = 60;
    public static final byte DEFAULT_BUTTON_HEIGHT = 80;
    public static boolean DEBUG = false;
    public static boolean PAUSE = false;
    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    public static int MAX;
    private final SecurityHandler security;
    private byte gameSpeed = 1;
    private Resources resources;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport view;
    private Player player;

    public DDGame(SecurityHandler security) {
        this.security = security;
    }

    @Override
    public void create() {
        sayHello();
        Gdx.input.setCatchMenuKey(true);
        Gdx.input.setCatchBackKey(true);
//        Gdx.app.setLogLevel(Application.LOG_NONE);
        WORLD_WIDTH = Gdx.graphics.getWidth();
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        MAP_WIDTH = WORLD_WIDTH / TILE_HEIGHT;
        MAP_HEIGHT = WORLD_HEIGHT / TILE_HEIGHT;
        MAX = MAP_WIDTH * MAP_HEIGHT;//Maximum entities on the map
        resources = new Resources(this);
        this.setScreen(new LaunchLoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        PAUSE = true;
    }

    @Override
    public void resume() {
        PAUSE = false;
    }

    @Override
    public void dispose() {
//      security.save();
        resources.dispose();
        batch.dispose();
        Gdx.app.log("INFO", "Game disposed");
        sayGoodbye();
    }

    public void createCamera() {
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

    public void createBatch() {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void createViewport() {
        float RATIO = (float) (Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        view = new ExtendViewport(WORLD_WIDTH * RATIO, WORLD_HEIGHT, camera);
        view.apply();
    }

    /**
     * Creates Factory from Json representation of desired class.
     *
     * @param file name of the Json file, that holds information about T class
     * @param type desired type of class to load
     * @param <T> class that is named in type variable.
     * @return new object of the desired class with values from Json
     */
    public <T> T getFactory(String file, Class<T> type) {
        try {
            Json json = new Json();
            json.setTypeName(null);
            json.setUsePrototypes(false);
            json.setIgnoreUnknownFields(true);
            return json.fromJson(type, Gdx.files.internal(file));
        } catch (Exception e) {
            this.setScreen(new ErrorScreen(this, "Could not load factory: " + file, e));
        }
        return null;
    }

    public Resources getResources() {
        return resources;
    }

    public Viewport getViewport() {
        return view;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public AssetManager getManager() {
        return resources.getManager();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public SecurityHandler getSecurity() {
        return security;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public byte getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = (byte) gameSpeed;
    }

    private synchronized void sayGoodbye() {
        Gdx.app.log("INFO", "Thank you for choosing Divine Defense!");
        Gdx.app.log("INFO", "I hope you enjoyed it. Bye-bye (^ω^) / ");
    }

    private synchronized void sayHello() {
        Gdx.app.log("INFO", "Welcome to DivineDefense, by Gram <gram7gram@gmail.com>");
        Gdx.app.log("INFO", "Visit https://github.com/gram7gram/DivineDefense to view sources");
    }

}

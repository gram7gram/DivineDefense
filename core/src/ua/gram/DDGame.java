package ua.gram;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.gram.controller.Log;
import ua.gram.controller.Resources;
import ua.gram.controller.security.SecurityManager;
import ua.gram.model.Player;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.model.prototype.ParametersPrototype;
import ua.gram.view.screen.LaunchLoadingScreen;

/**
 * Game enter point
 * <br/>
 * TODO Make different assets for 4x3 and 16x9(16x10) screens
 * <br/>
 * TODO For 16x9(16x10) make StretchViewport
 * <br/>
 * TODO Add to JSON pressed and disabled drawable for buttons
 * <br/>
 * TODO Add copyrights
 * <br/>
 * TODO Handle Android Menu, Back click events
 * <br/>
 * FIXME Logotype
 * <br/>
 * FIXME Switch 16pt font with 20pt
 * <br/>
 * NOTE Enemies should be tough because the amount of towers is unlimited
 * <br/>
 * NOTE Skin should not contain nonexistent values
 * <br/>
 * NOTE Gdx is initialized only on create()
 * <br/>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class DDGame<P extends GamePrototype> extends Game {

    public static final String ANGEL = "Angel";
    public static final String DEMON = "Demon";
    public static final byte TILE_HEIGHT = 60;
    public static final byte DEFAULT_BUTTON_HEIGHT = 80;
    public static boolean DEBUG;
    public static boolean PAUSE = false;
    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    public static int MAX_ENTITIES;
    public static int MAX_LEVELS;
    private final P prototype;
    private final ParametersPrototype parameters;
    private SecurityManager security;
    private Resources resources;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport view;
    private Player player;
    private BitmapFont info;
    private float gameSpeed = 1;

    public DDGame(SecurityManager security, P prototype) {
        this.security = security;
        this.prototype = prototype;
        this.parameters = prototype.getParameters();
        DEBUG = parameters.debugging;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(parameters.logLevel);
        sayHello();
        WORLD_WIDTH = Gdx.graphics.getWidth();
        WORLD_HEIGHT = Gdx.graphics.getHeight();
        MAP_WIDTH = WORLD_WIDTH / TILE_HEIGHT;
        MAP_HEIGHT = WORLD_HEIGHT / TILE_HEIGHT;
        MAX_ENTITIES = MAP_WIDTH * MAP_HEIGHT;//Maximum entities on the map
        MAX_LEVELS = prototype.levelConfig.levels.length;
        resources = new Resources(this);
        info = new BitmapFont();
        info.setColor(1, 1, 1, 1);
        this.setScreen(new LaunchLoadingScreen(this, prototype));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        security.save();
        resources.dispose();
        batch.dispose();
        Log.info("Game disposed");
        sayGoodbye();
    }

    public void createCamera() {
        if (camera != null) return;
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

    public void createBatch() {
        if (batch != null) return;
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void createViewport() {
        if (view != null) return;
        float RATIO = (float) (Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        view = new ExtendViewport(WORLD_WIDTH * RATIO, WORLD_HEIGHT, camera);
        view.apply();
    }

    public BitmapFont getInfo() {
        return info;
    }

    public ParametersPrototype getParameters() {
        return prototype.getParameters();
    }

    public ParametersPrototype getAbstractParameters() {
        return parameters;
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

    public AssetManager getAssetManager() {
        return resources.getManager();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public SecurityManager getSecurity() {
        return security;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(float gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public float increaseGameSpeed() {
        gameSpeed = .5f;
        return gameSpeed;
    }

    public float decreaseGameSpeed() {
        gameSpeed = 1;
        return gameSpeed;
    }

    public void resetGameSpeed() {
        this.gameSpeed = 1;
    }

    public GamePrototype getPrototype() {
        return prototype;
    }

    public LevelPrototype getLevelPrototype(int lvl) {
        LevelPrototype[] prototypes = prototype.levelConfig.levels;

        if (lvl <= 0 || prototypes.length < lvl)
            throw new IndexOutOfBoundsException("Cannot get level prototype");

        return prototypes[lvl - 1];
    }

    private synchronized void sayGoodbye() {
        for (String text : parameters.consoleBye) {
            Log.info(parameters.processString(text));
        }
    }

    private synchronized void sayHello() {
        for (String text : parameters.consoleHello) {
            Log.info(parameters.processString(text));
        }
    }
}

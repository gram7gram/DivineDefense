package ua.gram;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.graphics.GL20;

import org.mockito.Mockito;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameTestCase {

    protected final String root = System.getenv("PROJECT_DIR") + "/../android/assets/";

    public GameTestCase() {

        HeadlessNativesLoader.load();
        Gdx.files = new LwjglFiles();
        Gdx.graphics = new MockGraphics();
        Gdx.net = new HeadlessNet();
        Gdx.input = new MockInput();
        Gdx.gl = Mockito.mock(GL20.class);

//        DesktopModule module = new DesktopModule(root, "data/parameters.json");
//        module.initModule();
//        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
//
//        Application app = new HeadlessApplication(module.getApp(), config);

//        Gdx.app = app;
//
//        ApplicationListener game = app.getApplicationListener();
//
//        if (!(game instanceof DDGame))
//            throw new GdxRuntimeException("App instance is not a DDGame");
//
//        this.game = (DDGame) game;

    }
}

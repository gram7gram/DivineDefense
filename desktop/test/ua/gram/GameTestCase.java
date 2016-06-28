package ua.gram;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.graphics.GL20;

import org.mockito.Mockito;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameTestCase {

    protected final String root;

    public GameTestCase() {

        String currentProjectDirectory = System.getenv("PROJECT_DIR");
        if (currentProjectDirectory == null) {
            throw new NullPointerException("Missing env variable: PROJECT_DIR");
        }

        root = currentProjectDirectory + "/android/assets/";

        HeadlessNativesLoader.load();
        Gdx.files = new HeadlessFiles();
        Gdx.graphics = new MockGraphics();
        Gdx.net = new HeadlessNet();
        Gdx.input = new MockInput();
        Gdx.gl = Mockito.mock(GL20.class);


    }

}

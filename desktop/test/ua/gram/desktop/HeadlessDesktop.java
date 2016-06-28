package ua.gram.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.GameTestCase;

public abstract class HeadlessDesktop extends GameTestCase {

    protected final DDGame game;

    public HeadlessDesktop() {

        DesktopModule module = new DesktopModule(root, "data/parameters.json");
        module.initModule("test");

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        Application app = new HeadlessApplication(module.getApp(), config);

        Gdx.app = app;

        ApplicationListener game = app.getApplicationListener();

        if (!(game instanceof DDGame))
            throw new GdxRuntimeException("App instance is not a DDGame");

        this.game = (DDGame) game;
    }
}
package ua.gram.android;

import android.content.res.AssetManager;
import android.os.Build;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.Module;
import ua.gram.android.prototype.AndroidGamePrototype;
import ua.gram.controller.security.SecurityManager;
import ua.gram.model.prototype.ParametersPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AndroidModule implements Module {

    private final AndroidGamePrototype prototype;
    private final SecurityManager<AndroidGamePrototype> securityManager;
    private ApplicationListener app;

    public AndroidModule(AssetManager assetManager) {
        securityManager = new SecurityManager<AndroidGamePrototype>();
        securityManager.setFileHandle(new AndroidFiles(assetManager));

        prototype = securityManager.load(
                AndroidGamePrototype.class,
                "data/parameters.json");

        securityManager.setPrototype(prototype);

        securityManager.cleanup();

        initParameters();
        initConfig();
    }

    @Override
    public void initParameters() {
        if (prototype == null)
            throw new GdxRuntimeException("Game prototype was not loaded");

        ParametersPrototype parameters = prototype.getParameters();
        parameters.gameModule = getName();
        parameters.osName = getName() + "; "
                + Build.VERSION.CODENAME + "; "
                + Build.VERSION.RELEASE + "; "
                + Build.VERSION.SDK_INT;
        parameters.id = android.provider.Settings.Secure.ANDROID_ID;
        parameters.configPath = null;
    }

    @Override
    public void initConfig() {
        if (prototype == null)
            throw new GdxRuntimeException("Game prototype was not loaded");
        if (prototype.gameConfig == null)
            throw new GdxRuntimeException("Parameters config was not loaded");

//        AndroidApplicationConfiguration gameConfig = prototype.gameConfig.android;
    }

    @Override
    public String getName() {
        return "android";
    }

    @Override
    public void initModule(String env) {
        if (env != null) {
            prototype.parameters.env = env;
        }
        app = new DDGame<AndroidGamePrototype>(securityManager, prototype);
    }

    @Override
    public ApplicationListener getApp() {
        if (app == null) throw new GdxRuntimeException("Module is not initialized yet");
        return app;
    }

    public AndroidApplicationConfiguration getConfig() {
        if (prototype == null)
            throw new GdxRuntimeException("Game prototype was not loaded");
        if (prototype.gameConfig == null)
            throw new GdxRuntimeException("Parameters config was not loaded");

        return prototype.gameConfig.android;
    }

}

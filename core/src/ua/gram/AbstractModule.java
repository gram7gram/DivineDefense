package ua.gram;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.controller.Log;
import ua.gram.controller.loader.LoaderFactory;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractModule<P extends GamePrototype> {

    private GamePrototype defaultPrototype;

    protected abstract void initModule();

    /**
     * Load default internal prototype first.
     * Try to load external saved configuration or, if failed, a remote one.
     */
    @SuppressWarnings("unchecked")
    protected P loadPrototype(Class<P> type, String fallback) {
        LoaderFactory factory = new LoaderFactory();

        defaultPrototype = getFromInternal(fallback, factory, type);

        GamePrototype actualPrototype;
        try {
            actualPrototype = getFromExternal(factory, type);
        } catch (Exception e1) {
            Log.warn("Could not get external game configuration");
            try {
                actualPrototype = getFromRemote(factory, type);
            } catch (Exception e2) {
                Log.warn("Could not get remote game configuration. Used default");
                actualPrototype = defaultPrototype;
            }
        }

        if (actualPrototype == null)
            throw new GdxRuntimeException("Unable to load game prototype from any storage!");

        return (P) actualPrototype;
    }

    @SuppressWarnings("unchecked")
    private P getFromInternal(String path, LoaderFactory factory, Class<P> type) {
        Object prototype = factory.create(LoaderFactory.Type.STORAGE)
                .load(type, path);

        if (prototype == null)
            throw new GdxRuntimeException("Could not load prototype from internal storage");

        return (P) prototype;
    }

    @SuppressWarnings("unchecked")
    private P getFromRemote(LoaderFactory factory, Class<P> type) {
        if (defaultPrototype == null)
            throw new GdxRuntimeException("Could not get remote configuration: missing default prototype");
        return (P) factory
                .create(LoaderFactory.Type.INTERNET)
                .load(type, defaultPrototype.remoteConfig);
    }

    @SuppressWarnings("unchecked")
    private P getFromExternal(LoaderFactory factory, Class<P> type) {
        return (P) factory
                .create(LoaderFactory.Type.STORAGE)
                .load(type, defaultPrototype.getParameters().configPath);
    }
}

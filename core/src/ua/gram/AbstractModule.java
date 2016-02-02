package ua.gram;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.controller.Log;
import ua.gram.controller.loader.LoaderFactory;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractModule<P extends GamePrototype> {

    private P defaultPrototype;

    protected abstract void initModule();

    /**
     * Load default internal prototype first.
     * Try to load external saved configuration or, if failed, a remote one.
     */
    protected P loadPrototype(Class<P> type, String fallback) {
        LoaderFactory factory = new LoaderFactory();

        defaultPrototype = getFromInternal(fallback, factory, type);

        P actualPrototype;
        try {
            actualPrototype = getFromRemote(factory, type);
            Log.info("Received game configuration from remote source");
        } catch (Exception e1) {
            Log.warn("Could not get remote game configuration");
            try {
                actualPrototype = getFromExternal(factory, type);
                Log.info("Received game configuration from external source");
            } catch (Exception e2) {
                Log.exc("Could not get external game configuration", e2);
                actualPrototype = defaultPrototype;
                Log.warn("Used default game configuration");
            }
        }

        if (actualPrototype == null)
            throw new GdxRuntimeException("Unable to load game prototype from any source!");

        return actualPrototype;
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
        return (P) factory.create(LoaderFactory.Type.INTERNET)
                .load(type, defaultPrototype.remoteConfig);
    }

    @SuppressWarnings("unchecked")
    private P getFromExternal(LoaderFactory factory, Class<P> type) {
        return (P) factory.create(LoaderFactory.Type.STORAGE)
                .load(type, defaultPrototype.getFullConfigPath());
    }
}

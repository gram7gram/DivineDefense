package ua.gram.controller.security;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonWriter;

import ua.gram.controller.Json;
import ua.gram.controller.Log;
import ua.gram.controller.factory.LoaderFactory;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SecurityManager<P extends GamePrototype> {

    private final Json json;
    private P prototype;

    public SecurityManager() {
        json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        Log.info("Security initialized");
    }

    public P load(Class<P> type, String fallback) {
        LoaderFactory factory = new LoaderFactory();

        prototype = getFromInternal(fallback, factory, type);

        P actualPrototype;
        if (prototype.getParameters().debugging) {
            actualPrototype = prototype;
            Log.warn("Debugging is ON. Used default game configuration");
        } else {
            try {
                actualPrototype = getFromRemote(factory, type);
                Log.info("Recieved game configuration from remote source");
            } catch (Exception e1) {
                Log.warn("Could not get remote game configuration");
                try {
                    actualPrototype = getFromExternal(factory, type);
                    Log.info("Recieved game configuration from external source");
                } catch (Exception e2) {
                    Log.warn("Could not get external game configuration");
                    actualPrototype = prototype;
                    Log.warn("Used default game configuration");
                }
            }
        }

        if (actualPrototype == null)
            throw new GdxRuntimeException("Unable to load game prototype from any source!");

        prototype = actualPrototype;

        return actualPrototype;
    }

    public void init(P prototype) {
        this.prototype = prototype;
    }

    public void save() {
        String path = prototype.getFullConfigPath();
        try {
            json.toJson(prototype, new FileHandle(path));
            Log.info("Player saved successfully to: " + path);
        } catch (Exception e) {
            Log.exc("Could not save player", e);
        }
    }

    public boolean sendBugReport(String error) {
        return new BugReport(prototype).sendReport(error);
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
        if (prototype == null)
            throw new GdxRuntimeException("Could not get remote configuration: missing default prototype");
        return (P) factory.create(LoaderFactory.Type.INTERNET)
                .load(type, prototype.remoteConfig);
    }

    @SuppressWarnings("unchecked")
    private P getFromExternal(LoaderFactory factory, Class<P> type) {
        return (P) factory.create(LoaderFactory.Type.STORAGE)
                .load(type, prototype.getFullConfigPath());
    }

}

package ua.gram.controller.security;

import com.badlogic.gdx.Files;
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
    private Files fileHandle;

    public SecurityManager() {
        json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        Log.info("Security initialized");
    }

    public P load(Class<P> type, String path) {
        if (fileHandle == null)
            throw new NullPointerException("Register handlers before using the method");

        LoaderFactory.Type loader = LoaderFactory.Type.INTERNAL;
        FileHandle internal = fileHandle.internal(path);
        if (internal == null)
            throw new NullPointerException("FileHandle for " + loader.name() + " not specified");

        prototype = get(internal, loader, type);
        if (prototype == null) throw new GdxRuntimeException("Cannot load prototype");

        P actualPrototype;
        if (!prototype.getParameters().debugging) {
            loader = LoaderFactory.Type.EXTERNAL;
            FileHandle external = fileHandle.external(prototype.getFullConfigPath());
            if (external == null)
                throw new NullPointerException("FileHandle for " + loader.name() + " not specified");

            try {
                actualPrototype = get(external, loader, type);
                Log.info("Recieved game configuration from " + loader.name() + " source");
            } catch (Exception e2) {
                Log.warn("Could not get " + loader.name() + " game configuration");
                actualPrototype = prototype;
                Log.warn("Used default game configuration");
            }
        } else {
            actualPrototype = prototype;
            Log.warn("Debugging is ON. Used default game configuration");
        }

        if (actualPrototype == null)
            throw new GdxRuntimeException("Unable to load game prototype from any source!");

        prototype = actualPrototype;

        return actualPrototype;
    }

    public void setPrototype(P prototype) {
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
    private P get(FileHandle path, LoaderFactory.Type loaderType, Class<P> type) {
        Object prototype = LoaderFactory.create(loaderType).load(type, path);

        if (prototype == null)
            throw new GdxRuntimeException("Could not load prototype from internal storage");

        return (P) prototype;
    }

    public void setFileHandle(Files fileHandle) {
        this.fileHandle = fileHandle;
    }

    public void cleanup() {
        fileHandle = null;
    }
}

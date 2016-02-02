package ua.gram.controller.security;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonWriter;

import ua.gram.controller.Json;
import ua.gram.controller.Log;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SecurityHandler<P extends GamePrototype> {

    private final P prototype;
    private final Json json;

    public SecurityHandler(P prototype) {
        this.prototype = prototype;
        json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        Log.info("Security initialized");
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
        return new BugReport(prototype.getParameters()).sendReport(error);
    }
}

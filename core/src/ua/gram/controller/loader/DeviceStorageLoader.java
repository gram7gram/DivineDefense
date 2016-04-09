package ua.gram.controller.loader;

import com.badlogic.gdx.files.FileHandle;

import ua.gram.model.prototype.RemoteConfigurationPrototype;
import ua.gram.utils.Json;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DeviceStorageLoader implements LoaderInterface {

    private final Json json;

    public DeviceStorageLoader() {
        json = new Json();
        json.setIgnoreUnknownFields(true);
    }

    @Override
    public Object load(Class type, FileHandle file) {
        return json.fromJson(type, file);
    }

    @Override
    @Deprecated
    public Object load(Class type, RemoteConfigurationPrototype remote) {
        throw new IllegalStateException("Remote configuration not supported for storage loader");
    }
}

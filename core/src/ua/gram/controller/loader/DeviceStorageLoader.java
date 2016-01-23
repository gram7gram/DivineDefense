package ua.gram.controller.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import ua.gram.model.prototype.RemoteConfigurationPrototype;

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
    public Object load(Class type, String url) {
        return json.fromJson(type, new FileHandle(url));
    }

    @Override
    public Object load(Class type, RemoteConfigurationPrototype remote) {
        throw new IllegalStateException("Remote configuration not supported for storage loader");
    }
}

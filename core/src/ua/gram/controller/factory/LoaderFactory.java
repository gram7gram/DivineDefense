package ua.gram.controller.factory;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.controller.loader.DeviceStorageLoader;
import ua.gram.controller.loader.LoaderInterface;
import ua.gram.controller.loader.RemoteLoader;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LoaderFactory {

    public LoaderInterface create(Type type) {
        switch (type) {
            case STORAGE:
                return new DeviceStorageLoader();
            case INTERNET:
                return new RemoteLoader();
            default:
                throw new GdxRuntimeException(type.name() + " loader not implemented");
        }
    }

    public enum Type {
        INTERNET, STORAGE
    }
}

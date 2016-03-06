package ua.gram.controller.factory;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.controller.loader.DeviceStorageLoader;
import ua.gram.controller.loader.LoaderInterface;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LoaderFactory {

    public static LoaderInterface create(Type type) {
        switch (type) {
            case INTERNAL:
            case EXTERNAL:
                return new DeviceStorageLoader();
            case NETWORK:
            default:
                throw new GdxRuntimeException(type.name() + " loader not implemented");
        }
    }

    public enum Type {
        NETWORK, EXTERNAL, INTERNAL
    }
}

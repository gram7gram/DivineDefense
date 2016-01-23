package ua.gram.controller.loader;

import ua.gram.model.prototype.RemoteConfigurationPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface LoaderInterface {

    Object load(Class type, String url);

    Object load(Class type, RemoteConfigurationPrototype remote);
}

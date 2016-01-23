package ua.gram.controller.loader;

import ua.gram.model.prototype.RemoteConfigurationPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class RemoteLoader implements LoaderInterface {

    @Override
    public Object load(Class type, String url) {
        throw new IllegalStateException("Remote configuration loader is not yet implemented");
    }

    @Override
    public Object load(Class type, RemoteConfigurationPrototype remote) {
        throw new IllegalStateException("Remote configuration loader is not yet implemented");
    }
}

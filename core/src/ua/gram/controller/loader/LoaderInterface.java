package ua.gram.controller.loader;

import com.badlogic.gdx.files.FileHandle;

import ua.gram.model.prototype.RemoteConfigurationPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface LoaderInterface<A> {

    Object load(Class<A> type, FileHandle file);

    Object load(Class<A> type, RemoteConfigurationPrototype remote);
}

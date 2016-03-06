package ua.gram.controller.loader;

import com.badlogic.gdx.files.FileHandle;

import ua.gram.model.prototype.RemoteConfigurationPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface LoaderInterface {

    Object load(Class type, FileHandle file);

    Object load(Class type, RemoteConfigurationPrototype remote);
}

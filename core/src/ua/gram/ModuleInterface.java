package ua.gram;

import com.badlogic.gdx.ApplicationListener;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface ModuleInterface {

    ApplicationListener getApp();
    void initModule();

    void initParameters();

    void initConfig();

    String getName();
}

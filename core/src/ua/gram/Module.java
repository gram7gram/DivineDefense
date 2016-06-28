package ua.gram;

import com.badlogic.gdx.ApplicationListener;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public interface Module {

    ApplicationListener getApp();

    void initModule(String env);

    void initParameters();

    void initConfig();

    String getName();
}

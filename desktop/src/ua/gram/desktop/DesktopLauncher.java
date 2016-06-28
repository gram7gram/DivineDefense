package ua.gram.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopLauncher {

    public static void main(String[] arg) {
        DesktopModule module = new DesktopModule();
        module.initModule(null);
        new LwjglApplication(module.getApp(), module.getConfig());
    }

}

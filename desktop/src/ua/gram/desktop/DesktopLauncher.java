package ua.gram.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ua.gram.DDGame;
import ua.gram.controller.security.SecurityHandler;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopLauncher {

    public static void main(String[] arg) {

//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 4096;
//        settings.maxHeight = 4096;
//        TexturePacker.process(settings, "toPack/all", "data/skin", "style");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = false;
        config.resizable = true;
        config.vSyncEnabled = false;
        config.width = 900;
        config.height = 600;
        config.fullscreen = false;
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;
        config.x = 0;
        config.y = 0;
        config.title = "Divine Defense";
        config.addIcon("logo_min128.png", Files.FileType.Internal);//osx
        config.addIcon("logo_min32.png", Files.FileType.Internal);//windows, linux
        config.addIcon("logo_min16.png", Files.FileType.Internal);//windows

        DesktopGamePrototype prototype = new DesktopGamePrototype();
        prototype.gameModule = "Android";
        prototype.id = getMAC();
        prototype.gameModule = "Desktop";
        prototype.osName = System.getProperty("os.name");
        prototype.osVersion = System.getProperty("os.version");
        prototype.configPath = getPrefs();

        new LwjglApplication(new DDGame(new SecurityHandler(prototype), prototype), config);
    }

    /**
     * TODO Get list of available NetworkInterfaces and check for active one.
     *
     * @return
     */
    public static String getMAC() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                byte[] mac = interfaces.nextElement().getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            System.out.println("WARN: Could not get MAC-address: " + e.getClass().getSimpleName() + ". Set to 'undefined'");
        }
        return "undefined";
    }

    public static String getPrefs() {
        return System.getProperty("user.home")
                + (System.getProperty("os.name").contains("Windows") ? "\\.prefs\\" : "/.prefs/");
    }
}

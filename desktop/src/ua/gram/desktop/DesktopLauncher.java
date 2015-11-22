package ua.gram.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import ua.gram.DDGame;
import ua.gram.controller.security.SecurityHandler;
import ua.gram.desktop.prototype.DesktopGamePrototype;
import ua.gram.desktop.prototype.DesktopParametersPrototype;

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

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        DesktopGamePrototype prototype = json.fromJson(
                DesktopGamePrototype.class,
                new FileHandle("data/parameters.json"));

        DesktopParametersPrototype parameters = prototype.parameters;

        parameters.id = getMAC();
        parameters.gameModule = "Desktop";
        parameters.osName = System.getProperty("os.name");
        parameters.osVersion = System.getProperty("os.version");
        parameters.configPath = getPrefs();

        LwjglApplicationConfiguration config = prototype.config.desktop;
        config.addIcon(prototype.config.logo128, Files.FileType.Internal);
        config.addIcon(prototype.config.logo32, Files.FileType.Internal);
        config.addIcon(prototype.config.logo16, Files.FileType.Internal);

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

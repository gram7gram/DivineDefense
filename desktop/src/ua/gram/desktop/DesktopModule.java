package ua.gram.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.net.NetworkInterface;
import java.util.Enumeration;

import ua.gram.DDGame;
import ua.gram.ModuleInterface;
import ua.gram.controller.Log;
import ua.gram.controller.security.SecurityManager;
import ua.gram.desktop.prototype.DesktopGamePrototype;
import ua.gram.desktop.prototype.DesktopParametersPrototype;
import ua.gram.model.prototype.TexturePackerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DesktopModule implements ModuleInterface {

    private final DesktopGamePrototype prototype;
    private final LwjglApplicationConfiguration config;
    private final SecurityManager<DesktopGamePrototype> securityManager;

    public DesktopModule() {
        securityManager = new SecurityManager<>();
        prototype = securityManager.load(DesktopGamePrototype.class, "data/parameters.json");
        securityManager.init(prototype);
        initParameters();
        config = initConfig();
    }

    @Override
    public void initModule() {
        new LwjglApplication(new DDGame<>(securityManager, prototype), config);
    }

    private LwjglApplicationConfiguration initConfig() {
        if (prototype == null || prototype.gameConfig == null)
            throw new GdxRuntimeException("Game prototype was not loaded");

        LwjglApplicationConfiguration graphics = prototype.gameConfig.desktop;
        graphics.addIcon(prototype.gameConfig.logo128, Files.FileType.Internal);
        graphics.addIcon(prototype.gameConfig.logo32, Files.FileType.Internal);
        graphics.addIcon(prototype.gameConfig.logo16, Files.FileType.Internal);

        if (prototype.gameConfig.reloadTextures) {
            TexturePackerPrototype packer = prototype.gameConfig.texturePacker;
            TexturePacker.process(packer.config, packer.from, packer.to, packer.name);
        }

        return graphics;
    }

    private void initParameters() {
        if (prototype == null) throw new GdxRuntimeException("Game prototype was not loaded");

        DesktopParametersPrototype parameters = prototype.parameters;
        parameters.id = getMAC();
        parameters.gameModule = "Desktop";
        parameters.osName = System.getProperty("os.name");
        parameters.osVersion = System.getProperty("os.version");
    }

    private String getMAC() {
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
            Log.warn("Could not get MAC-address. Used 'undefined'");
        }
        return "undefined";
    }
}

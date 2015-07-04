package ua.gram.android;

import android.os.Build;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import ua.gram.DDGame;
import ua.gram.controller.security.SecurityHandler;

import java.util.HashMap;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        TexturePacker.process(settings, "toPack/all", "data/skin", "style");
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.hideStatusBar = true;
        HashMap<String, String> device = new HashMap<String, String>();
        device.put("game.module", "Android");
        device.put("os.codename", Build.VERSION.CODENAME);
        device.put("os.release", Build.VERSION.RELEASE);
        device.put("api.version", String.valueOf(Build.VERSION.SDK_INT));
        device.put("device.id", android.provider.Settings.Secure.ANDROID_ID);
        initialize(new DDGame(new SecurityHandler(device)), config);
    }
}

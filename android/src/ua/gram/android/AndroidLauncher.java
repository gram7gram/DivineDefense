package ua.gram.android;

import android.os.Build;
import android.os.Bundle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import ua.gram.DDGame;
import ua.gram.controller.security.SecurityHandler;
import ua.gram.model.prototype.GamePrototype;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 4096;
//        settings.maxHeight = 4096;
//        TexturePacker.process(settings, "toPack/all", "data/skin", "style");

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.hideStatusBar = true;

        AndroidGamePrototype prototype = new Json().fromJson(
                AndroidGamePrototype.class,
                new FileHandle("data/config.json"));
        prototype.gameModule = "Android";
        prototype.id = android.provider.Settings.Secure.ANDROID_ID;
        prototype.apiVersion = String.valueOf(Build.VERSION.SDK_INT);
        prototype.osName = "Android";
        prototype.osCodename = Build.VERSION.CODENAME;
        prototype.osRelease = Build.VERSION.RELEASE;
        prototype.configPath = null;

        initialize(new DDGame(new SecurityHandler(prototype), prototype), config);
    }
}

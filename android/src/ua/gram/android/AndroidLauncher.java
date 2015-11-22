package ua.gram.android;

import android.os.Build;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import ua.gram.DDGame;
import ua.gram.android.prototype.AndroidGamePrototype;
import ua.gram.android.prototype.AndroidParametersPrototype;
import ua.gram.controller.security.SecurityHandler;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 4096;
//        settings.maxHeight = 4096;
//        TexturePacker.process(settings, "toPack/all", "data/skin", "style");

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        AndroidGamePrototype prototype = json.fromJson(
                AndroidGamePrototype.class,
                new FileHandle("data/parameters.json"));

        AndroidParametersPrototype parameters = prototype.parameters;
        parameters.gameModule = "Android";
        parameters.id = android.provider.Settings.Secure.ANDROID_ID;
        parameters.apiVersion = Build.VERSION.SDK_INT + "";
        parameters.osName = "Android";
        parameters.osCodename = Build.VERSION.CODENAME;
        parameters.osRelease = Build.VERSION.RELEASE;
        parameters.configPath = null;

        AndroidApplicationConfiguration config = prototype.config.android;

        initialize(new DDGame(new SecurityHandler(prototype), prototype), config);
    }
}

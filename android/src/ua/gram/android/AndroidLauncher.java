package ua.gram.android;

import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Json;

import ua.gram.DDGame;
import ua.gram.android.prototype.AndroidGamePrototype;
import ua.gram.android.prototype.AndroidParametersPrototype;
import ua.gram.controller.security.SecurityManager;
import ua.gram.model.prototype.TexturePackerPrototype;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        AndroidGamePrototype prototype = json.fromJson(
                AndroidGamePrototype.class,
                new FileHandle("data/parameters.json"));

        AndroidParametersPrototype parameters = prototype.parameters;
        parameters.gameModule = "Android";
        parameters.osName = "Android";
        parameters.id = android.provider.Settings.Secure.ANDROID_ID;
        parameters.apiVersion = Build.VERSION.SDK_INT + "";
        parameters.osCodename = Build.VERSION.CODENAME;
        parameters.osRelease = Build.VERSION.RELEASE;
        parameters.configPath = null;

        AndroidApplicationConfiguration config = prototype.config.android;

        if (prototype.config.reloadTextures) {
            TexturePackerPrototype packer = prototype.config.texturePacker;
            TexturePacker.process(packer.config, packer.from, packer.to, packer.name);
        }

        initialize(new DDGame(new SecurityManager(prototype), prototype), config);
    }
}

package ua.gram.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidModule module = new AndroidModule(getAssets());
        module.initModule();
        initialize(module.getApp(), module.getConfig());
    }
}

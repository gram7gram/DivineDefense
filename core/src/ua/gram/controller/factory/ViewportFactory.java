package ua.gram.controller.factory;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ViewportFactory {

    public static Viewport create(String name, float w, float h, Camera camera) {
        switch (name) {
            case "ExtendViewport":
                return new ExtendViewport(w, h, camera);
            case "FitViewport":
                return new FitViewport(w, h, camera);
            case "FillViewport":
                return new FillViewport(w, h, camera);
            case "ScreenViewport":
                return new ScreenViewport();
            case "StretchViewport":
                return new StretchViewport(w, h, camera);
            default:
                throw new IllegalArgumentException("Not implemented for" + name);
        }
    }
}

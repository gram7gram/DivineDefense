package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DebugListener extends InputListener {

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Input.Keys.D) {
            DDGame.DEBUG = !DDGame.DEBUG;
            Gdx.app.log("INFO", "Debuging is " + (DDGame.DEBUG ? "on" : "off"));
        } else if (keycode == Input.Keys.PLUS) {
            DDGame.DEBUG = true;
            Gdx.app.log("INFO", "Debuging is on");
        } else if (keycode == Input.Keys.MINUS) {
            DDGame.DEBUG = false;
            Gdx.app.log("INFO", "Debuging is off");
        } else if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.log("WARN", "Pressed ESC in debug mode. Will exit");
            Gdx.app.exit();
        }
        return super.keyDown(event, keycode);
    }
}

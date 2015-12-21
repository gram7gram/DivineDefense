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
        switch (keycode) {
            case Input.Keys.PLUS:
                DDGame.DEBUG = true;
                Gdx.app.log("INFO", "Debuging is on");
                break;
            case Input.Keys.MINUS:
                DDGame.DEBUG = false;
                Gdx.app.log("INFO", "Debuging is off");
                break;
            case Input.Keys.ESCAPE:
                Gdx.app.log("WARN", "Pressed ESC in debug mode. Will exit");
                Gdx.app.exit();
                break;
        }
        return super.keyDown(event, keycode);
    }
}

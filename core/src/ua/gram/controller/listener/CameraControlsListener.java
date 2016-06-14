package ua.gram.controller.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CameraControlsListener extends InputAdapter {

    private final DDGame game;

    public CameraControlsListener(DDGame game) {
        this.game = game;
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.SLASH:
                game.getCamera().zoom += 1;
                break;
            case Input.Keys.STAR:
                game.getCamera().zoom -= 1;
                break;
        }

        return super.keyUp(keycode);
    }
}

package ua.gram.view.stage.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Player;
import ua.gram.view.screen.MainMenuScreen;

/**
 * FIXME Set MessageLabel's font to 32 < font < 64.
 *
 * @author gram
 */
public class ConfirmationWindow extends Window {

    public ConfirmationWindow(Skin skin) {
        super("", skin, "default");
        this.setSize(480, 250);
        this.setPosition((DDGame.WORLD_WIDTH - this.getWidth()) / 2f, (DDGame.WORLD_HEIGHT - this.getHeight()) / 2f);
        this.setMovable(false);
        this.setTouchable(Touchable.disabled);
    }
}

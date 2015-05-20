package ua.gram.view.window;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ua.gram.DDGame;

/**
 * FIXME Set MessageLabel's font to 32 < font < 64.
 *
 * @author gram
 */
public class ConfirmationWindow extends Window {

    public ConfirmationWindow(Skin skin, String style) {
        super("", skin, style);
        this.setSize(480, 250);
        this.setPosition((DDGame.WORLD_WIDTH - this.getWidth()) / 2f, (DDGame.WORLD_HEIGHT - this.getHeight()) / 2f);
        this.setMovable(false);
        this.setTouchable(Touchable.disabled);
    }
}

package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractWindow extends Window {

    public AbstractWindow(String title, Skin skin) {
        this(title, skin, "window-dark-gold");
    }

    public AbstractWindow(String title, Skin skin, String styleName) {
        super(title.toUpperCase(), skin, styleName);
        setBounds(
                DDGame.DEFAULT_BUTTON_HEIGHT / 3f,
                DDGame.DEFAULT_BUTTON_HEIGHT / 2f,
                DDGame.WORLD_WIDTH - DDGame.DEFAULT_BUTTON_HEIGHT * 2 / 3f,
                DDGame.WORLD_HEIGHT - DDGame.DEFAULT_BUTTON_HEIGHT);
        pad(30);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
    }

}

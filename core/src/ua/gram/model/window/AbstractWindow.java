package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;
import ua.gram.model.prototype.ui.window.WindowPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractWindow extends Window {

    public AbstractWindow(DDGame game, WindowPrototype prototype) {
        super("", game.getResources().getSkin(), prototype.style);
        setSize(prototype.width, prototype.height);
        setPosition(
                game.getCamera().position.x - (getWidth() / 2f),
                game.getCamera().position.y - (getHeight() / 2f));
        setVisible(true);
        setMovable(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
    }

}

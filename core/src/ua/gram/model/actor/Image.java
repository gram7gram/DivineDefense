package ua.gram.model.actor;

import com.badlogic.gdx.graphics.Texture;

import ua.gram.DDGame;
import ua.gram.model.prototype.level.ResoursePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image {

    private final ResoursePrototype prototype;

    public Image(Texture texture, ResoursePrototype prototype) {
        super(texture);
        this.prototype = prototype;

        setPosition(prototype.x, prototype.y);
        setSize(prototype.width, prototype.height);
        setVisible(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG);
    }

    public ResoursePrototype getPrototype() {
        return prototype;
    }
}

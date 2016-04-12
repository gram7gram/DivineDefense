package ua.gram.model.actor;

import com.badlogic.gdx.graphics.Texture;

import ua.gram.model.prototype.level.ResoursePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ForegroundImage extends Image {

    public ForegroundImage(Texture texture, ResoursePrototype prototype) {
        super(texture, prototype);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        toFront();
    }
}

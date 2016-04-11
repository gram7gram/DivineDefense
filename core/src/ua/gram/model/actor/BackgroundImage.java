package ua.gram.model.actor;

import com.badlogic.gdx.graphics.Texture;

import ua.gram.model.prototype.level.ResoursePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BackgroundImage extends Image {
    public BackgroundImage(Texture texture, ResoursePrototype prototype) {
        super(texture, prototype);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        toBack();
    }
}

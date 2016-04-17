package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PoolableAnimation implements Pool.Poolable {

    private final Animation animation;
    private final String name;

    public PoolableAnimation(float delay, TextureRegion[] tiles, String name) {
        animation = new Animation(delay, tiles);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        this.name = name;
    }

    @Override
    public void reset() {

    }

    public String getName() {
        return name;
    }

    public Animation getAnimation() {
        return animation;
    }
}

package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PoolableAnimation implements Pool.Poolable {

    private final Animation a;

    public PoolableAnimation(float delay, TextureRegion[] tiles) {
        this.a = new Animation(delay, tiles);
        this.a.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void reset() {

    }

    public Animation getAnimation() {
        return a;
    }
}

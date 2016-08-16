package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

import ua.gram.model.PoolableAnimation;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AnimationPool extends Pool<PoolableAnimation> {

    private final TextureRegion[] tiles;
    private final String name;
    private final float DEFAULT_DELAY = .1f;
    private float delay = DEFAULT_DELAY;

    public AnimationPool(TextureRegion[] tiles, String name) {
        super(4);
        if (tiles == null || tiles[0] == null) {
            throw new NullPointerException("Passed empty tiles to constructor");
        }
        this.tiles = tiles;
        this.name = name;
    }

    @Override
    public PoolableAnimation newObject() {
        return new PoolableAnimation(delay, tiles, name);
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public void increaseDelay() {
        delay = DEFAULT_DELAY * 2;
    }

    public void resetDelay() {
        delay = DEFAULT_DELAY;
    }
}
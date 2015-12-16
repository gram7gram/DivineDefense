package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import ua.gram.model.PollableAnimation;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AnimationPool extends Pool<PollableAnimation> {

    private final TextureRegion[] tiles;

    public AnimationPool(TextureRegion[] tiles) {
        super(4);
        if (tiles == null || tiles[0] == null) throw new NullPointerException("Passed empty tiles to constructor");
        this.tiles = tiles;
    }

    @Override
    public PollableAnimation newObject() {
        return new PollableAnimation(tiles);
    }
}
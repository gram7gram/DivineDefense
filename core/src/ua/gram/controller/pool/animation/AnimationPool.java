package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import ua.gram.controller.enemy.EnemyAnimationProvider;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AnimationPool extends Pool<Animation> {

    private TextureRegion[] tiles;

    public AnimationPool(TextureRegion[] tiles) {
        super(4);
        this.tiles = tiles;
    }

    @Override
    public Animation newObject() {
        Animation a = new Animation(EnemyAnimationProvider.DELAY, tiles);
        a.setPlayMode(Animation.PlayMode.LOOP);
        return a;
    }

}

package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import ua.gram.controller.enemy.EnemyAnimationProvider;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PollableAnimation implements Pool.Poolable {

    private final Animation a;

    public PollableAnimation(TextureRegion[] tiles) {
        this.a = new Animation(EnemyAnimationProvider.DELAY, tiles);
        this.a.setPlayMode(Animation.PlayMode.LOOP);
    }

    @Override
    public void reset() {

    }

    public Animation getAnimation() {
        return a;
    }
}

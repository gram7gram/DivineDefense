package ua.gram.controller.pool.animation;

import ua.gram.controller.enemy.EnemyAnimationController;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationPool extends Pool<Animation> {

    private final TextureRegion[] tiles;

    public EnemyAnimationPool(TextureRegion[] tiles) {
        super(4); //Number of directions for the Enemy
        this.tiles = tiles;
    }

    @Override
    public Animation newObject() {
        Animation a = new Animation(EnemyAnimationController.DELAY, tiles);
        a.setPlayMode(Animation.PlayMode.LOOP);
        return a;
    }

}

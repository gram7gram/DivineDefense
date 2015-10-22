package ua.gram.controller.pool.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.model.Animator;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDirectionAnimationPool implements DirectionPool{

    protected final AnimationPool leftPool;
    protected final AnimationPool rightPool;
    protected final AnimationPool downPool;
    protected final AnimationPool upPool;

    public EnemyDirectionAnimationPool(Animator.Types type, EnemyAnimation provider) {
        leftPool = new AnimationPool(provider.setAnimationRegion(
                type, Path.Types.LEFT, null));
        rightPool = new AnimationPool(provider.setAnimationRegion(
                type, Path.Types.RIGHT, null));
        downPool = new AnimationPool(provider.setAnimationRegion(
                type, Path.Types.DOWN, null));
        upPool = new AnimationPool(provider.setAnimationRegion(
                type, Path.Types.UP, null));
    }

    public AnimationPool get(Path.Types direction) throws IllegalArgumentException{
        if (direction == null) {
            Gdx.app.log("WARN", "Direction type is NULL. Using default");
            return downPool;
        }
        switch (direction) {
            case UP:
                return upPool;
            case RIGHT:
                return rightPool;
            case LEFT:
                return leftPool;
            case DOWN:
                return downPool;
            default: {
                Gdx.app.log("WARN", "Direction type [" + direction + "] not found. Using default");
                return downPool;
            }
        }
    }

}

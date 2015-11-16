package ua.gram.controller.pool.animation;

import com.badlogic.gdx.Gdx;
import ua.gram.model.Animator;
import ua.gram.model.map.Path;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbilityUserDirectionAnimationPool implements DirectionPoolInterface {

    private final AnimationPool leftPool;
    private final AnimationPool rightPool;
    private final AnimationPool downPool;
    private final AnimationPool upPool;

    public AbilityUserDirectionAnimationPool(Animator.Types type, EnemyAnimation provider) {
        leftPool = new AnimationPool(provider.setAnimationRegion(
                Animator.Types.WALKING, Path.Types.LEFT, type));
        rightPool = new AnimationPool(provider.setAnimationRegion(
                Animator.Types.WALKING, Path.Types.RIGHT, type));
        downPool = new AnimationPool(provider.setAnimationRegion(
                Animator.Types.WALKING, Path.Types.DOWN, type));
        upPool = new AnimationPool(provider.setAnimationRegion(
                Animator.Types.WALKING, Path.Types.UP, type));
        Gdx.app.log("INFO", "DirectionPool for " + type + " is OK");
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

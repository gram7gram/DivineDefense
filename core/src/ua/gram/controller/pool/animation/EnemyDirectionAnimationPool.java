package ua.gram.controller.pool.animation;

import ua.gram.controller.Log;
import ua.gram.model.Animator;
import ua.gram.model.map.Path;

/**
 * Holds pools for each direction, available in the game.
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDirectionAnimationPool implements DirectionPoolInterface {

    protected AnimationPool leftPool;
    protected AnimationPool rightPool;
    protected AnimationPool downPool;
    protected AnimationPool upPool;

    public EnemyDirectionAnimationPool(EnemyAnimation provider, Animator.Types type) {
        leftPool = new AnimationPool(provider.getAnimationRegion(type, Path.Types.LEFT));
        rightPool = new AnimationPool(provider.getAnimationRegion(type, Path.Types.RIGHT));
        downPool = new AnimationPool(provider.getAnimationRegion(type, Path.Types.DOWN));
        upPool = new AnimationPool(provider.getAnimationRegion(type, Path.Types.UP));
        Log.info("DirectionPool for " + type + " is OK");
    }

    /**
     * Get a pool by direction enum
     *
     * @param direction
     * @return corresponding to direction pool
     */
    public AnimationPool get(Path.Types direction) {
        if (direction == null) {
            Log.warn("Direction type is NULL. Using default");
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
            default:
                throw new IllegalArgumentException("Direction type [" + direction + "] not found");
        }
    }

}

package ua.gram.controller.pool.animation;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.utils.Log;

/**
 * Holds pools for Enemy and each direction, available in the game.
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyDirectionAnimationPool implements DirectionPoolInterface {

    private final EnumMap<Path.Types, AnimationPool> identityMap;

    public EnemyDirectionAnimationPool(EnemyPrototype prototype,
                                       EnemyAnimationController provider,
                                       Types.EnemyState type) {
        identityMap = new EnumMap<Path.Types, AnimationPool>(Path.Types.class);

        for (Path.Types direction : EnumSet.allOf(Path.Types.class)) {
            identityMap.put(direction, new AnimationPool(
                    provider.getAnimationRegion(prototype, type, direction)));
        }

        Log.info("DirectionPool for " + prototype.name + " " + type.name() + " is OK");
    }

    /**
     * Get a pool by direction enum
     */
    public AnimationPool get(Path.Types direction) {
        if (direction == null) {
            Log.warn("Direction type is NULL. Using default");
            return identityMap.get(Path.Types.DOWN);
        }
        return identityMap.get(direction);
    }

}

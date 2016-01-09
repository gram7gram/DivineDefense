package ua.gram.controller.tower;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import ua.gram.controller.Log;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.TowerAnimationController;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.TowerPrototype;

/**
 * Holds Pools of Animations for each level and state of the TowerState.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerLevelAnimationPool {

    private final Map<Types.TowerLevels, AnimationPool> identityMap;

    public TowerLevelAnimationPool(TowerPrototype prototype,
                                   TowerAnimationController controller,
                                   Types.TowerState type) {
        identityMap = new EnumMap<>(Types.TowerLevels.class);
        for (Types.TowerLevels level : EnumSet.allOf(Types.TowerLevels.class)) {
            identityMap.put(level, new AnimationPool(controller.getAnimationRegion(prototype, type, level)));
        }
        Log.info("LevelAnimationPool for " + prototype.name + " " + type + " is OK");
    }

    /**
     * Get a pool by direction enum
     */
    public AnimationPool get(Types.TowerLevels lvl) {
        if (lvl == null) {
            Log.warn("Tower level is not set. Using default");
            return identityMap.get(0);
        }
        return identityMap.get(lvl);
    }

}

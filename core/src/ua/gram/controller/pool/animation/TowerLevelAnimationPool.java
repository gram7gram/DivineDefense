package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import ua.gram.controller.animation.tower.TowerAnimationManager;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;

/**
 * Holds Pools of Animations for each levelConfig and state of the TowerState.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerLevelAnimationPool {

    private final Map<Types.TowerLevels, AnimationPool> identityMap;

    public TowerLevelAnimationPool(TowerPrototype prototype,
                                   TowerAnimationManager controller,
                                   Types.TowerState type) {
        identityMap = new EnumMap<>(Types.TowerLevels.class);
        for (Types.TowerLevels level : EnumSet.allOf(Types.TowerLevels.class)) {
            TextureRegion[] regions = controller.getAnimationRegion(prototype, type, level);
            String name = controller.getAnimationName(prototype, type, level);
            identityMap.put(level, new AnimationPool(regions, name));
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

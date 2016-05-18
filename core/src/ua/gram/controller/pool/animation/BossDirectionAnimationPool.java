package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.animation.boss.BossAnimationManager;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossDirectionAnimationPool implements DirectionPool {

    private final EnumMap<Path.Direction, AnimationPool> identityMap;

    public BossDirectionAnimationPool(BossPrototype prototype,
                                      BossAnimationManager provider,
                                      Types.BossState type) {
        identityMap = new EnumMap<Path.Direction, AnimationPool>(Path.Direction.class);

        for (Path.Direction direction : EnumSet.allOf(Path.Direction.class)) {
            TextureRegion[] regions = provider.getAnimationRegion(prototype, type, direction);
            String name = provider.getAnimationName(prototype, type, direction);
            identityMap.put(direction, new AnimationPool(regions, name));
        }

        Log.info("DirectionPool for " + prototype.name + " " + type.name() + " is OK");
    }

    public AnimationPool get(Path.Direction direction) {
        if (direction == null) {
            direction = Path.Direction.RIGHT;
            Log.warn("Direction is not set. Using default: " + direction.name());
        }
        return identityMap.get(direction);
    }
}

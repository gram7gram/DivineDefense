package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.Log;
import ua.gram.model.Player;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.enemy.AbilityUserPrototype;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationController implements AnimationControllerInterface<EnemyPrototype, Types.EnemyState, Path.Types> {

    private final EnumMap<Types.EnemyState, EnemyDirectionAnimationPool> identityMap;
    private final Skin skin;

    public EnemyAnimationController(Skin skin, EnemyPrototype prototype) {
        this.skin = skin;
        identityMap = new EnumMap<>(Types.EnemyState.class);
        init(prototype);
    }

    @Override
    public boolean init(EnemyPrototype prototype) {
        boolean initialized = true;
        for (Types.EnemyState type : EnumSet.allOf(Types.EnemyState.class)) {
            if (!isCompatible(prototype, type)) continue;

            try {
                identityMap.put(type, new EnemyDirectionAnimationPool(prototype, this, type));
            } catch (IllegalArgumentException e) {
                initialized = false;
                Log.exc("Error at loading " + type.name()
                        + " animation type for " + prototype.name, e);
            }
        }

        if (initialized)
            Log.info("AnimationController for " + prototype.name + " is OK");

        return initialized;
    }

    private boolean isCompatible(EnemyPrototype prototype, Types.EnemyState type) {
        return type != Types.EnemyState.ABILITY || prototype instanceof AbilityUserPrototype;
    }

    @Override
    public TextureRegion[] getAnimationRegion(EnemyPrototype prototype,
                                              Types.EnemyState type,
                                              Path.Types direction) {
        if (prototype == null || skin == null)
            throw new NullPointerException("Missing required parameters");

        String region = "enemies"
                + "/" + prototype.name
                + "/" + Player.SYSTEM_FRACTION
                + "/" + type
                + "/" + direction;

        TextureRegion texture = skin.getRegion(region);

        if (texture == null)
            throw new NullPointerException("Texture not found: " + region);

        TextureRegion[][] regions = texture.split(
                (int) prototype.width,
                (int) prototype.height);

        if (regions == null || regions[0] == null)
            throw new NullPointerException("Texture not loaded: " + region);

        return regions[0];
    }

    public EnemyDirectionAnimationPool getPool(Types.EnemyState type) {
        if (identityMap.isEmpty())
            throw new NullPointerException("Put some pools to indentity map first to use getPool()");
        EnemyDirectionAnimationPool pool = identityMap.get(type);
        if (pool == null)
            throw new NullPointerException("Identity map does not contain pool for type: " + type);
        return pool;
    }

    @Override
    public AnimationPool get(Types.EnemyState type, Path.Types path) {
        return this.getPool(type).get(path);
    }
}

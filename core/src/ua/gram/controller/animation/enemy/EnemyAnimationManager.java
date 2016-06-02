package ua.gram.controller.animation.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.animation.AnimationManager;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.EnemyDirectionAnimationPool;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.player.Player;
import ua.gram.model.prototype.enemy.AbilityUserPrototype;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationManager implements AnimationManager<EnemyPrototype, Types.EnemyState, Path.Direction> {

    private final EnumMap<Types.EnemyState, EnemyDirectionAnimationPool> identityMap;
    private final Skin skin;
    private final EnemyPrototype prototype;

    public EnemyAnimationManager(Skin skin, EnemyPrototype prototype) {
        this.skin = skin;
        this.prototype = prototype;
        identityMap = new EnumMap<Types.EnemyState, EnemyDirectionAnimationPool>(Types.EnemyState.class);
    }

    @Override
    public void init() {
        if (prototype == null)
            throw new NullPointerException("Missing prototype");

        for (Types.EnemyState type : EnumSet.allOf(Types.EnemyState.class)) {
            if (!isCompatible(prototype, type)) continue;

            identityMap.put(type, new EnemyDirectionAnimationPool(prototype, this, type));
        }

        Log.info("AnimationManager for " + prototype.name + " is OK");

    }

    private boolean isCompatible(EnemyPrototype prototype, Types.EnemyState type) {
        return type != Types.EnemyState.ABILITY || prototype instanceof AbilityUserPrototype;
    }

    @Override
    public String getAnimationName(EnemyPrototype prototype, Types.EnemyState type, Path.Direction direction) {
        return "enemies"
                + "/" + prototype.name
                + "/" + Player.SYSTEM_FACTION
                + "/" + type
                + "/" + direction;
    }

    @Override
    public TextureRegion[] getAnimationRegion(EnemyPrototype prototype,
                                              Types.EnemyState type,
                                              Path.Direction direction) {
        if (prototype == null || skin == null)
            throw new NullPointerException("Missing required parameters");

        String region = getAnimationName(prototype, type, direction);

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
    public AnimationPool get(Types.EnemyState type, Path.Direction path) {
        return this.getPool(type).get(path);
    }

    @Override
    public void dispose() {

    }
}

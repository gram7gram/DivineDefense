package ua.gram.controller.animation.boss;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.animation.AnimationManager;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.BossDirectionAnimationPool;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.model.player.Player;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossAnimationManager
        implements AnimationManager<BossPrototype, Types.BossState, Path.Direction> {

    private final EnumMap<Types.BossState, BossDirectionAnimationPool> identityMap;
    private final Skin skin;
    private final BossPrototype prototype;

    public BossAnimationManager(Skin skin, BossPrototype prototype) {
        this.skin = skin;
        this.prototype = prototype;
        identityMap = new EnumMap<Types.BossState, BossDirectionAnimationPool>(Types.BossState.class);
    }

    @Override
    public void init() {
        for (Types.BossState type : EnumSet.allOf(Types.BossState.class)) {
            identityMap.put(type, new BossDirectionAnimationPool(prototype, this, type));
        }

        Log.info("AnimationManager for " + prototype.name + " is OK");
    }

    @Override
    public TextureRegion[] getAnimationRegion(BossPrototype prototype, Types.BossState type, Path.Direction direction) {
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

    @Override
    public String getAnimationName(BossPrototype prototype, Types.BossState state, Path.Direction direction) {
        return "bosses"
                + "/" + prototype.name
                + "/" + Player.SYSTEM_FACTION
                + "/" + state
                + "/" + direction;
    }

    @Override
    public AnimationPool get(Types.BossState type, Path.Direction direction) {
        if (identityMap.isEmpty())
            throw new NullPointerException("Put some pools to indentity map first to use get()");

        BossDirectionAnimationPool pool = identityMap.get(type);

        if (pool == null)
            throw new NullPointerException("Identity map does not contain pool for type: " + type);

        return pool.get(direction);
    }
}

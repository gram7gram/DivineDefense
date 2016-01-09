package ua.gram.controller.pool.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerLevelAnimationPool;
import ua.gram.model.Player;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationController implements AnimationControllerInterface<TowerPrototype, Types.TowerState, Types.TowerLevels> {

    private final EnumMap<Types.TowerState, TowerLevelAnimationPool> identityMap;
    private final Skin skin;

    public TowerAnimationController(Skin skin, TowerPrototype prototype) {
        this.skin = skin;
        identityMap = new EnumMap<>(Types.TowerState.class);
        init(prototype);
    }

    @Override
    public boolean init(TowerPrototype prototype) {
        boolean initialized = true;
        for (Types.TowerState type : EnumSet.allOf(Types.TowerState.class)) {
            try {
                identityMap.put(type, new TowerLevelAnimationPool(prototype, this, type));
            } catch (Exception e) {
                initialized = false;
                Log.exc("Error at loading " + type.name() + " animation type for " + prototype.name, e);
            }
        }

        if (initialized) Log.info("AnimationController for " + prototype.name + " is OK");

        return initialized;
    }

    @Override
    public TextureRegion[] getAnimationRegion(TowerPrototype prototype,
                                              Types.TowerState type,
                                              Types.TowerLevels level) {
        if (prototype == null || skin == null)
            throw new NullPointerException("Missing required parameters");

        String region = "towers"
                + "/" + prototype.name
                + "/" + Player.PLAYER_FRACTION
                + "/" + level.name()
                + "/" + type.name();

        TextureRegion texture = skin.getRegion(region);

        if (texture == null)
            throw new NullPointerException("Texture not found: " + region);

        TextureRegion[][] regions = texture.split(
                (int) prototype.width, (int) prototype.height);

        if (regions == null || regions[0] == null)
            throw new NullPointerException("Texture not loaded: " + region);

        return regions[0];
    }

    @Override
    public AnimationPool get(Types.TowerState type, Types.TowerLevels lvl) {
        return this.getPool(type).get(lvl);
    }

    private TowerLevelAnimationPool getPool(Types.TowerState type) {
        if (identityMap.isEmpty())
            throw new NullPointerException("Put some pools to indentity map first to use getPool()");
        TowerLevelAnimationPool pool = identityMap.get(type);
        if (pool == null)
            throw new NullPointerException("Identity map does not contain pool for type: " + type.name());
        return pool;
    }
}

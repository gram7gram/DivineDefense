package ua.gram.controller.animation.tower;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.EnumMap;
import java.util.EnumSet;

import ua.gram.controller.animation.AnimationManager;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.pool.animation.TowerLevelAnimationPool;
import ua.gram.model.enums.Types;
import ua.gram.model.player.Player;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationManager implements AnimationManager<TowerPrototype, Types.TowerState, Types.TowerLevels> {

    private final EnumMap<Types.TowerState, TowerLevelAnimationPool> identityMap;
    private final Skin skin;
    private final TowerPrototype prototype;

    public TowerAnimationManager(Skin skin, TowerPrototype prototype) {
        this.skin = skin;
        this.prototype = prototype;
        identityMap = new EnumMap<Types.TowerState, TowerLevelAnimationPool>(Types.TowerState.class);
    }

    @Override
    public void init() {

        for (Types.TowerState type : EnumSet.allOf(Types.TowerState.class)) {
            identityMap.put(type, new TowerLevelAnimationPool(prototype, this, type));
        }

        Log.info("AnimationController for " + prototype.name + " is OK");

    }

    @Override
    public String getAnimationName(TowerPrototype prototype, Types.TowerState type, Types.TowerLevels level) {
        return "towers"
                + "/" + prototype.name
                + "/" + Player.PLAYER_FACTION
                + "/" + level.name()
                + "/" + type.name();
    }

    @Override
    public TextureRegion[] getAnimationRegion(TowerPrototype prototype,
                                              Types.TowerState type,
                                              Types.TowerLevels level) {
        if (prototype == null || skin == null)
            throw new NullPointerException("Missing required parameters");

        String region = getAnimationName(prototype, type, level);

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
            throw new NullPointerException("Identity map does not contain pool for type: " + type);
        return pool;
    }
}

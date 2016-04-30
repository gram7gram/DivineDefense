package ua.gram.controller.weapon;

import java.util.HashMap;

import ua.gram.DDGame;
import ua.gram.controller.pool.WeaponPool;
import ua.gram.model.Initializer;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WeaponProvider implements Initializer {

    private final HashMap<WeaponPrototype, WeaponPool> identityMap;
    private final DDGame game;
    private final WeaponPrototype[] prototypes;

    public WeaponProvider(DDGame game, WeaponPrototype[] prototypes) {
        identityMap = new HashMap<>(prototypes.length);
        this.game = game;
        this.prototypes = prototypes;
    }

    @Override
    public void init() {
        for (WeaponPrototype prototype : prototypes) {
            identityMap.put(prototype, new WeaponPool(game, this, prototype));
        }
    }

    public WeaponPool getPool(String type) {
        WeaponPool pool = null;
        for (WeaponPrototype prototype : identityMap.keySet()) {
            if (prototype.type.equals(type)) {
                pool = identityMap.get(prototype);
                break;
            }
        }

        if (pool == null)
            throw new NullPointerException("Cannot find weapon pool for " + type);

        return pool;
    }

}

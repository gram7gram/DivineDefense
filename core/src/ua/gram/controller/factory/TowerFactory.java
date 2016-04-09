package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerFactory {

    public static Types.Tower stringToEnum(String type) {
        type = type.toUpperCase();
        if (Types.Tower.PRIMARY.name().equals(type)) return Types.Tower.PRIMARY;
        else if (Types.Tower.SECONDARY.name().equals(type)) return Types.Tower.SECONDARY;
        else if (Types.Tower.SPECIAL.name().equals(type)) return Types.Tower.SPECIAL;
        else if (Types.Tower.STUN.name().equals(type)) return Types.Tower.STUN;
        else throw new IllegalArgumentException("Unknown tower type: " + type);
    }

    public static Tower create(DDGame game, TowerShop shop, TowerPrototype prototype) {

        if (prototype.name == null)
            throw new NullPointerException("Missing prototype name in factory");

        Types.Tower type = stringToEnum(prototype.name);
        Log.info("Factory creates " + type.name() + "...");
        switch (type) {
            case PRIMARY:
                return new TowerPrimary(game, shop, prototype);
            case SECONDARY:
                return new TowerSecondary(game, shop, prototype);
            case STUN:
                return new TowerStun(game, shop, prototype);
            case SPECIAL:
                return new TowerSpecial(game, shop, prototype);
            default:
                throw new NullPointerException("Factory couldn't create: " + type.name());
        }
    }
}

package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerFactory {

    public static Tower create(DDGame game, TowerPrototype prototype) {
        Tower towerType;
        String type = prototype.type;
        if (type.equals("TowerPrimary")) {
            towerType = new TowerPrimary(game, prototype);
        } else if (type.equals("TowerSecondary")) {
            towerType = new TowerSecondary(game, prototype);
        } else if (type.equals("TowerStun")) {
            towerType = new TowerStun(game, prototype);
        } else if (type.equals("TowerSpecial")) {
            towerType = new TowerSpecial(game, prototype);
        } else {
            throw new NullPointerException("Factory couldn't create: " + type);
        }
        Gdx.app.log("INFO", "Factory creates " + type);
        return towerType;
    }
}

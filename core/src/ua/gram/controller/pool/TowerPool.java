package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool extends Pool<Tower> {

    private final TowerPrototype prototype;
    private final DDGame game;

    public TowerPool(DDGame game, TowerPrototype prototype) {
        super(5, DDGame.MAX_ENTITIES);
        this.game = game;
        this.prototype = prototype;
        Gdx.app.log("INFO", "Pool for " + prototype.name + " is created");
    }

    @Override
    protected Tower newObject() {
        switch (prototype.name) {
            case "TowerPrimary":
                return new TowerPrimary(game, prototype);
            case "TowerSecondary":
                return new TowerSecondary(game, prototype);
            case "TowerStun":
                return new TowerStun(game, prototype);
            case "TowerSpecial":
                return new TowerSpecial(game, prototype);
            default:
                throw new NullPointerException("Unknown TowerState type: " + prototype.name);
        }
    }
}

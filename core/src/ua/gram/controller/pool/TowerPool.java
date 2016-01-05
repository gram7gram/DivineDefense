package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool<T extends Tower> extends Pool<Tower> {

    private final String type;
    private final DDGame game;
    private HashMap<String, TowerPrototype> map;

    public TowerPool(DDGame game, String type) {
        super(5, DDGame.MAX_ENTITIES);
        this.game = game;
        this.type = type;
        map = new HashMap<String, TowerPrototype>();
        TowerPrototype[] prototypes = game.getPrototype().towers;
        for (TowerPrototype prototype : prototypes) {
            map.put(prototype.name, prototype);
        }
        Gdx.app.log("INFO", "Pool for " + type + " is created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        switch (type) {
            case "TowerPrimary":
                return (T) new TowerPrimary(game, map.get(type));
            case "TowerSecondary":
                return (T) new TowerSecondary(game, map.get(type));
            case "TowerStun":
                return (T) new TowerStun(game, map.get(type));
            case "TowerSpecial":
                return (T) new TowerSpecial(game, map.get(type));
            default:
                throw new NullPointerException("Couldn't get configuration for: " + type);
        }
    }
}

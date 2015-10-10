package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.actor.tower.*;
import ua.gram.model.prototype.TowerPrototype;

import java.util.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool<T extends Tower> extends Pool<Tower> {

    private final String type;
    private final DDGame game;
    private Map<String, TowerPrototype> map;
    ;

    public TowerPool(DDGame game, String type) {
        super(5, DDGame.MAX_ENTITIES);
        this.game = game;
        this.type = type;
        TowerPrototype[] prototypes = game.deserialize(Resources.TOWERS, TowerPrototype[].class, true);
        for (TowerPrototype prototype : prototypes) {
            map.put(prototype.name, prototype);
        }
        Gdx.app.log("INFO", "Pool for " + type + " is created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        if (type.equals("TowerPrimary")) {
            return (T) new TowerPrimary(game, map.get(type));
        } else if (type.equals("TowerSecondary")) {
            return (T) new TowerSecondary(game, map.get(type));
        } else if (type.equals("TowerStun")) {
            return (T) new TowerStun(game, map.get(type));
        } else if (type.equals("TowerSpecial")) {
            return (T) new TowerSpecial(game, map.get(type));
        } else {
            throw new NullPointerException("Couldn't get configuration for: " + type);
        }
    }
}

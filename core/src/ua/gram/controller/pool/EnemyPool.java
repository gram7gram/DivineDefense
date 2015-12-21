package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.prototype.EnemyPrototype;

import java.util.HashMap;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyPool<T extends Enemy> extends Pool<Enemy> {

    private final String type;
    private final DDGame game;
    private HashMap<String, EnemyPrototype> map;

    public EnemyPool(DDGame game, String type) {
        super(5, DDGame.MAX_ENTITIES);
        this.game = game;
        this.type = type;
        map = new HashMap<>();
        EnemyPrototype[] prototypes = game.getPrototype().enemies;
        for (EnemyPrototype prototype : prototypes) {
            map.put(prototype.name, prototype);
        }
        Gdx.app.log("INFO", "Pool for " + type + " is created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        switch (type) {
            case "EnemyRunner":
                return (T) new EnemyRunner(game, map.get(type));
            case "EnemySoldier":
                return (T) new EnemySoldier(game, map.get(type));
            case "EnemySoldierArmored":
                return (T) new EnemySoldierArmored(game, map.get(type));
            case "EnemySummoner":
                return (T) new EnemySummoner(game, map.get(type));
            case "EnemyWarrior":
                return (T) new EnemyWarrior(game, map.get(type));
            default:
                throw new NullPointerException("Couldn't get configuration for: " + type);
        }
    }
}

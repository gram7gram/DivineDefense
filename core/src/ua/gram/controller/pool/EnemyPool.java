package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

import ua.gram.DDGame;
import ua.gram.controller.factory.EnemyFactory;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyPool extends Pool<Enemy> {

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
    protected Enemy newObject() {
        return EnemyFactory.create(game, map.get(type));
    }
}

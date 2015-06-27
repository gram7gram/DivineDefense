package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.factory.EnemyFactory;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.enemy.*;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyPool<T extends Enemy> extends Pool<Enemy> {

    private final Class<? extends Enemy> type;
    private final EnemyFactory factory;
    private final DDGame game;

    public EnemyPool(DDGame game, int capacity, int max, Class<? extends Enemy> type) {
        super(capacity, max);
        this.game = game;
        this.type = type;
        String file;
        String enemyFilesDir = "data/world/enemies/";
        if (type.equals(EnemyWarrior.class)) {
            file = enemyFilesDir + "warrior.json";
        } else if (type.equals(EnemyRunner.class)) {
            file = enemyFilesDir + "runner.json";
        } else if (type.equals(EnemySoldier.class)) {
            file = enemyFilesDir + "soldier.json";
        } else if (type.equals(EnemySoldierArmored.class)) {
            file = enemyFilesDir + "soldierArmored.json";
        } else if (type.equals(EnemySummoner.class)) {
            file = enemyFilesDir + "summoner.json";
        } else {
            throw new NullPointerException("Couldn't get configuration for: " + type.getSimpleName());
        }
        factory = game.deserialize(file, EnemyFactory.class, true);
        Gdx.app.log("INFO", "Pool for " + type.getSimpleName() + " is created");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        return (T) factory.create(game, type);
    }
}

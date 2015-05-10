package ua.gram.controller.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.factory.EnemyFactory;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.enemy.EnemySoldierArmored;
import ua.gram.model.actor.enemy.EnemyRunner;
import ua.gram.model.actor.enemy.EnemySoldier;
import ua.gram.model.actor.enemy.EnemySummoner;
import ua.gram.model.actor.enemy.EnemyWarrior;

/**
 * Created by Gram on 20/1.
 */
public class EnemyPool<T extends Enemy> extends Pool<Enemy> {

    private final Class<? extends Enemy> type;
    private final String enemyFilesDir = "data/world/enemies/";
    private final EnemyFactory container;

    public EnemyPool(DDGame game, int capacity, int max, Class<? extends Enemy> type) {
        super(capacity, max);
        this.type = type;
        String file = "";
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
        container = game.getFactory(file, EnemyFactory.class);
        container.setGame(game);
        Gdx.app.log("INFO", "Pool for " + type.getSimpleName() + " is created.");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T newObject() {
        return (T) container.create(type);
    }
}

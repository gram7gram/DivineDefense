package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.enemy.*;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyFactory implements Factory<Enemy> {

    private ArrayList<EnemyPrototype> prototypes;

    @Override
    public Enemy create(DDGame game) {
        return null;
    }

    @Override
    public Enemy create(DDGame game, Class<? extends Enemy> type) {
        EnemyPrototype enemy = prototypes.get(0);
        float[] stats = new float[]{
                enemy.health,
                enemy.speed,
                enemy.armor,
                enemy.reward
        };
        Enemy enemyType;
        if (type.equals(EnemyWarrior.class)) {
            enemyType = new EnemyWarrior(game, stats);
        } else if (type.equals(EnemySoldier.class)) {
            enemyType = new EnemySoldier(game, stats);
        } else if (type.equals(EnemySoldierArmored.class)) {
            enemyType = new EnemySoldierArmored(game, stats);
        } else if (type.equals(EnemySummoner.class)) {
            enemyType = new EnemySummoner(game, stats);
        } else if (type.equals(EnemyRunner.class)) {
            enemyType = new EnemyRunner(game, stats);
        } else {
            throw new NullPointerException("EnemyFactory couldn't create: " + type.getSimpleName());
        }
        Gdx.app.log("INFO", "EnemyFactory creates: " + type.getSimpleName());
        return enemyType;
    }

    static class EnemyPrototype {

        public short health;
        public float speed;
        public short armor;
        public short reward;
    }
}

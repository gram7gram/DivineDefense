package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyFactory implements AbstractFactory<ua.gram.model.actor.Enemy> {

    private ArrayList<EnemyPrototype> prototypes;
    private DDGame game;

    public void setGame(DDGame game) {
        this.game = game;
    }

    @Override
    public ua.gram.model.actor.Enemy create(Class<? extends ua.gram.model.actor.Enemy> type) {
        EnemyPrototype enemy = prototypes.get(0);
        float[] stats = new float[]{
                enemy.health,
                enemy.speed,
                enemy.armor,
                enemy.reward
        };
        ua.gram.model.actor.Enemy enemyType = null;
        if (type.equals(ua.gram.model.actor.enemy.EnemyWarrior.class)) {
            enemyType = new ua.gram.model.actor.enemy.EnemyWarrior(game, stats);
        } else if (type.equals(ua.gram.model.actor.enemy.EnemySoldier.class)) {
            enemyType = new ua.gram.model.actor.enemy.EnemySoldier(this.game, stats);
        } else if (type.equals(ua.gram.model.actor.enemy.EnemySoldierArmored.class)) {
            enemyType = new ua.gram.model.actor.enemy.EnemySoldierArmored(this.game, stats);
        } else if (type.equals(ua.gram.model.actor.enemy.EnemySummoner.class)) {
            enemyType = new ua.gram.model.actor.enemy.EnemySummoner(this.game, stats);
        } else if (type.equals(ua.gram.model.actor.enemy.EnemyRunner.class)) {
            enemyType = new ua.gram.model.actor.enemy.EnemyRunner(this.game, stats);
        } else {
            throw new NullPointerException("Enemy factory couldn't create: " + type.getSimpleName());
        }
        Gdx.app.log("INFO", "Factory creates " + type.getSimpleName());
        return enemyType;
    }

    static class EnemyPrototype {

        public short health;
        public float speed;
        public short armor;
        public short reward;
    }
}

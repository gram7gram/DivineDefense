package ua.gram.controller.factory;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.*;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyFactory implements FactoryInterface<Enemy, EnemyPrototype> {

    @Override
    public Enemy create(DDGame game, EnemyPrototype prototype) {
        Enemy enemyType;
        String type = prototype.type;
        if (type.equals("EnemyWarrior")) {
            enemyType = new EnemyWarrior(game, prototype);
        } else if (type.equals("EnemySoldier")) {
            enemyType = new EnemySoldier(game, prototype);
        } else if (type.equals("EnemySoldierArmored")) {
            enemyType = new EnemySoldierArmored(game, prototype);
        } else if (type.equals("EnemySummoner")) {
            enemyType = new EnemySummoner(game, prototype);
        } else if (type.equals("EnemyRunner")) {
            enemyType = new EnemyRunner(game, prototype);
        } else {
            throw new NullPointerException("EnemyFactory couldn't create: " + type);
        }
        Gdx.app.log("INFO", "EnemyFactory creates: " + type);
        return enemyType;
    }
}

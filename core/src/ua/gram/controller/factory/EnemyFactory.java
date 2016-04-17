package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.enemy.EnemyRunner;
import ua.gram.model.actor.enemy.EnemySoldier;
import ua.gram.model.actor.enemy.EnemySoldierArmored;
import ua.gram.model.actor.enemy.EnemySummoner;
import ua.gram.model.actor.enemy.EnemyWarrior;
import ua.gram.model.prototype.enemy.AbilityUserPrototype;
import ua.gram.model.prototype.enemy.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyFactory {

    public static Enemy create(DDGame game, EnemyPrototype prototype) {
        Enemy enemy;
        switch (prototype.name) {
            case "EnemyWarrior":
                enemy = new EnemyWarrior(game, prototype);
                break;
            case "EnemySoldier":
                enemy = new EnemySoldier(game, prototype);
                break;
            case "EnemySoldierArmored":
                enemy = new EnemySoldierArmored(game, prototype);
                break;
            case "EnemySummoner":
                enemy = new EnemySummoner(game, (AbilityUserPrototype) prototype);
                break;
            case "EnemyRunner":
                enemy = new EnemyRunner(game, prototype);
                break;
            default:
                throw new NullPointerException("EnemyFactory couldn't create: " + prototype.name);
        }
        enemy.init();
        return enemy;
    }
}

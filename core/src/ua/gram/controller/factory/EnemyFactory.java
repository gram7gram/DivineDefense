package ua.gram.controller.factory;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.enemy.EnemyRunner;
import ua.gram.model.actor.enemy.EnemySoldier;
import ua.gram.model.actor.enemy.EnemySoldierArmored;
import ua.gram.model.actor.enemy.EnemySummoner;
import ua.gram.model.actor.enemy.EnemyWarrior;
import ua.gram.model.prototype.AbilityUserPrototype;
import ua.gram.model.prototype.EnemyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyFactory {

    public static Enemy create(DDGame game, EnemyPrototype prototype) {
        switch (prototype.name) {
            case "EnemyWarrior":
                return new EnemyWarrior(game, prototype);
            case "EnemySoldier":
                return new EnemySoldier(game, prototype);
            case "EnemySoldierArmored":
                return new EnemySoldierArmored(game, prototype);
            case "EnemySummoner":
                return new EnemySummoner(game, (AbilityUserPrototype) prototype);
            case "EnemyRunner":
                return new EnemyRunner(game, prototype);
            default:
                throw new NullPointerException("EnemyFactory couldn't create: " + prototype.name);
        }
    }
}

package ua.gram.model;

import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.weapon.Weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ActiveTarget {

    private final Tower tower;
    private final Weapon weapon;
    private final Enemy target;

    public ActiveTarget(Tower tower, Weapon weapon, Enemy enemy) {
        this.tower = tower;
        this.weapon = weapon;
        this.target = enemy;
    }

    public Tower getTower() {
        return tower;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Enemy getTarget() {
        return target;
    }
}

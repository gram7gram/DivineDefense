package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.model.actor.Enemy;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.Weapon;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerPrimary extends Tower implements Cloneable {


    public TowerPrimary(DDGame game, float[] stats) {
        super(game, stats);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 70);
    }

    @Override
    public void pre_attack(Enemy victim) {

    }

    @Override
    public void attack(Enemy victim) {

    }

    @Override
    public void post_attack(Enemy victim) {

    }

    @Override
    public TowerPrimary clone() throws CloneNotSupportedException {
        return (TowerPrimary) super.clone();
    }

    @Override
    public void setWeapon(Weapon weapon) {
        super.setWeapon(weapon);
        //...
    }
}

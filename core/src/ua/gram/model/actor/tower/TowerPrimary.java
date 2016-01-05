package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.model.actor.weapon.LaserWeapon;
import ua.gram.model.prototype.LaserWeaponPrototype;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerPrimary extends Tower implements Cloneable {

    public TowerPrimary(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 70);
    }

    @Override
    public LaserWeaponPrototype getWeaponPrototype() {
        return (LaserWeaponPrototype) prototype.weapon;
    }

    @Override
    public LaserWeapon getWeapon() {
        if (weapon == null) {
            weapon = new LaserWeapon(game.getResources(), this.getWeaponPrototype());
        }
        return (LaserWeapon) weapon;
    }

    @Override
    public TowerPrimary clone() throws CloneNotSupportedException {
        return (TowerPrimary) super.clone();
    }

}

package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.model.actor.weapon.BombWeapon;
import ua.gram.model.prototype.BombWeaponPrototype;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerSecondary extends Tower implements Cloneable {

    public TowerSecondary(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 78);
    }

    @Override
    public TowerSecondary clone() throws CloneNotSupportedException {
        return (TowerSecondary) super.clone();
    }

    @Override
    public BombWeaponPrototype getWeaponPrototype() {
        return (BombWeaponPrototype) prototype.weapon;
    }

    @Override
    public BombWeapon getWeapon() {
        if (weapon == null) {
            weapon = new BombWeapon(game.getResources(), this.getParent(), null);
        }
        return (BombWeapon) weapon;
    }
}

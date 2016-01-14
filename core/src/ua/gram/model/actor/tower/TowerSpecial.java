package ua.gram.model.actor.tower;

import ua.gram.DDGame;
import ua.gram.model.actor.weapon.LightningWeapon;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.prototype.weapon.LightningWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class TowerSpecial extends Tower implements Cloneable {

    public TowerSpecial(DDGame game, TowerPrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void update(float delta) {
        this.setOrigin(getX() + animationWidth / 2f, getY() + 78);
    }

    @Override
    public TowerSpecial clone() throws CloneNotSupportedException {
        return (TowerSpecial) super.clone();
    }

    @Override
    public LightningWeaponPrototype getWeaponPrototype() {
        return (LightningWeaponPrototype) prototype.weapon;
    }

    @Override
    public LightningWeapon getWeapon() {
        if (weapon == null) {
            weapon = new LightningWeapon(game.getResources(), this.getWeaponPrototype());
        }
        return (LightningWeapon) weapon;
    }
}

package ua.gram.model.prototype.tower;

import ua.gram.model.prototype.weapon.BombWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerSecondaryPrototype extends TowerPrototype {
    public BombWeaponPrototype weapon;

    @Override
    public BombWeaponPrototype getWeapon() {
        return weapon;
    }
}

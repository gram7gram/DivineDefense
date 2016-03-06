package ua.gram.model.prototype.tower;

import ua.gram.model.prototype.weapon.FreezeWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerStunPrototype extends TowerPrototype {
    public FreezeWeaponPrototype weapon;

    @Override
    public FreezeWeaponPrototype getWeapon() {
        return weapon;
    }
}

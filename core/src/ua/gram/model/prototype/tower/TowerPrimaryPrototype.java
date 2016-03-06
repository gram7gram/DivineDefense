package ua.gram.model.prototype.tower;

import ua.gram.model.prototype.weapon.LaserWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPrimaryPrototype extends TowerPrototype {
    public LaserWeaponPrototype weapon;

    @Override
    public LaserWeaponPrototype getWeapon() {
        return weapon;
    }
}

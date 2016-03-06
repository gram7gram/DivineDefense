package ua.gram.model.prototype.tower;

import ua.gram.model.prototype.weapon.LightningWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerSpecialPrototype extends TowerPrototype {
    public LightningWeaponPrototype weapon;

    @Override
    public LightningWeaponPrototype getWeapon() {
        return weapon;
    }
}

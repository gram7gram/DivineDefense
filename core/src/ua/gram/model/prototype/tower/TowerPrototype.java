package ua.gram.model.prototype.tower;

import ua.gram.model.prototype.GameActorPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPrototype extends GameActorPrototype {

    public float buildDelay;
    public WeaponPrototype weapon;
    public TowerPropertyPrototype[] properties;

    public TowerPropertyPrototype getFirstLevelProperty() {
        return getProperty(1);
    }

    public TowerPropertyPrototype getProperty(int lvl) {
        TowerPropertyPrototype prototype = null;
        for (TowerPropertyPrototype p : properties) {
            if (p.towerLevel == lvl) {
                prototype = p;
                break;
            }
        }
        if (prototype == null)
            throw new NullPointerException("Missing first tower property");

        return prototype;
    }
}

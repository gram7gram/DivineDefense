package ua.gram.model;

import ua.gram.model.prototype.tower.TowerPropertyPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerProperty {

    private TowerPropertyPrototype prototype;

    public TowerProperty(TowerPropertyPrototype prototype) {
        this.prototype = prototype;
    }

    public TowerPropertyPrototype getPrototype() {
        return prototype;
    }

    public void setPrototype(TowerPropertyPrototype prototype) {
        this.prototype = prototype;
    }

    public int getCost() {
        return prototype.cost;
    }

    public float getDamage() {
        return prototype.damage;
    }

    public float getRange() {
        return prototype.range;
    }

    public float getRate() {
        return prototype.rate;
    }

    public int getTowerLevel() {
        return prototype.towerLevel;
    }
}

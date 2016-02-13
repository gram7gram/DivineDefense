package ua.gram.model.prototype;

import ua.gram.model.prototype.shop.TowerShopConfigPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelConfigPrototype extends Prototype {
    public String[] resources;
    public LevelPrototype[] levels;
    public TowerShopConfigPrototype towerShopConfig;
    public CounterButtonPrototype counterButtonConfig;
}

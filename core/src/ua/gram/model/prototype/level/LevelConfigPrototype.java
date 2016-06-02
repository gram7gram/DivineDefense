package ua.gram.model.prototype.level;

import ua.gram.model.prototype.Prototype;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.ui.CounterButtonPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelConfigPrototype extends Prototype {
    public String[] commonResources;
    public LevelPrototype[] levels;
    public TowerShopConfigPrototype towerShopConfig;
    public CounterButtonPrototype counterButtonConfig;
}

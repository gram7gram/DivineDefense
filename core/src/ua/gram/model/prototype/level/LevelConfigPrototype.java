package ua.gram.model.prototype.level;

import ua.gram.model.prototype.Prototype;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelConfigPrototype extends Prototype {
    public String[] resources;
    public LevelPrototype[] levels;
    public TowerShopConfigPrototype towerShopConfig;
    public ua.gram.model.prototype.ui.CounterButtonPrototype counterButtonConfig;
}

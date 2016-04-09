package ua.gram.controller.pool;

import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.factory.TowerFactory;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerPool extends Pool<Tower> {

    private final TowerPrototype prototype;
    private final DDGame game;
    private final TowerShop shop;

    public TowerPool(DDGame game, TowerShop shop, TowerPrototype prototype) {
        super(5, DDGame.MAX_ENTITIES);
        this.game = game;
        this.prototype = prototype;
        this.shop = shop;
        Log.info("Pool for " + prototype.name + " is created");
    }

    @Override
    protected Tower newObject() {
        return TowerFactory.create(game, shop, prototype);
    }
}

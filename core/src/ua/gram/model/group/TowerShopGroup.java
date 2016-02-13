package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Initializer;
import ua.gram.model.actor.market.TowerShopItem;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.shop.TowerShopItemPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopGroup extends Group implements Initializer {

    private final TowerShopConfigPrototype prototype;
    private final DDGame game;
    private TowerShop towerShop;

    public TowerShopGroup(DDGame game, TowerShopConfigPrototype prototype) {
        this.game = game;
        this.prototype = prototype;
        Log.info("TowerShopGroup is OK");
    }

    @Override
    public void init() {
        int total = prototype.items.length;
        for (TowerShopItemPrototype shopItem : prototype.items) {
            TowerPrototype proto = towerShop.findByName(shopItem.name);
            TowerShopItem item = new TowerShopItem(game, towerShop,
                    proto, "shopitem-" + shopItem.name.toLowerCase());
            item.setIndexInShop(total - shopItem.order + 1);

            item.addAction(
                    Actions.sequence(
                            Actions.parallel(
                                    Actions.alpha(0, .15f),
                                    Actions.moveBy(0, -item.getHeight(), .2f)
                            ),
                            Actions.delay(.2f * item.getIndexInShop()),
                            Actions.parallel(
                                    Actions.alpha(1, .15f),
                                    Actions.moveBy(0, item.getHeight(), .2f)
                            )
                    )
            );
            addActor(item);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            setDebug(DDGame.DEBUG);
        }
    }

    public void setTowerShop(TowerShop towerShop) {
        this.towerShop = towerShop;
    }

}

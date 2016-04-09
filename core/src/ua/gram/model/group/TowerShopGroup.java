package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Initializer;
import ua.gram.model.actor.market.TowerShopItem;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.shop.TowerShopItemPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopGroup extends Table implements Initializer {

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
            item.setVisible(true);
            item.addAction(
                    Actions.sequence(
                            Actions.alpha(0),
                            Actions.delay(.2f * item.getIndexInShop()),
                            Actions.alpha(1, .15f)
                    )
            );
            add(item).size(DDGame.DEFAULT_BUTTON_HEIGHT).pad(5);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
    }

    public void setTowerShop(TowerShop towerShop) {
        this.towerShop = towerShop;
    }

}

package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.market.TowerShopItem;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopGroup extends Group {

    public TowerShopGroup(DDGame game, TowerShop shop) {

        int i = 1;
        for (TowerPrototype prototype : shop.getRegisteredTowers()) {
            TowerShopItem item = new TowerShopItem(game, shop, prototype,
                    "shopitem-" + prototype.name.toLowerCase());
            item.addAction(
                    Actions.sequence(
                            Actions.parallel(
                                    Actions.alpha(0, .15f),
                                    Actions.moveBy(0, -item.getHeight(), .2f)
                            ),
                            Actions.delay(.2f * i),
                            Actions.parallel(
                                    Actions.alpha(1, .15f),
                                    Actions.moveBy(0, item.getHeight(), .2f)
                            )
                    )
            );
            this.addActor(item);
            item.setIndex(i);
            ++i;
        }
        Log.info("TowerShopGroup is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            this.setDebug(DDGame.DEBUG);
        }
    }
}

package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import ua.gram.DDGame;
import ua.gram.controller.market.shop.TowerShop;
import ua.gram.model.actor.market.TowerShopItem;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopGroup extends Group {

    public TowerShopGroup(DDGame game, TowerShop shop) {

        ArrayList<TowerShopItem> items = new ArrayList<TowerShopItem>();
        items.add(new TowerShopItem(game, shop, TowerPrimary.class, "shopitem-tower-primary"));
        items.add(new TowerShopItem(game, shop, TowerSecondary.class, "shopitem-tower-secondary"));
        items.add(new TowerShopItem(game, shop, TowerStun.class, "shopitem-tower-stun"));
        items.add(new TowerShopItem(game, shop, TowerSpecial.class, "shopitem-tower-special"));

        for (int i = 0; i < items.size(); i++) {
            TowerShopItem item = items.get(i);
            item.setIndex(items.size() - i);
            item.addAction(
                    Actions.sequence(
                            Actions.parallel(
                                    Actions.alpha(0),
                                    Actions.moveBy(0, -item.getHeight())
                            ),
                            Actions.delay(.2f * (1 + i)),
                            Actions.parallel(
                                    Actions.alpha(1, .15f),
                                    Actions.moveBy(0, item.getHeight(), .2f)
                            )
                    )
            );
            this.addActor(item);
        }
        this.setDebug(DDGame.DEBUG);
        Gdx.app.log("INFO", "TowerShopGroup is OK");
    }
}

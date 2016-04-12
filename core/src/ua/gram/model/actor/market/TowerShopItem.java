package ua.gram.model.actor.market;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.listener.TowerShopInputListener;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.tower.TowerPropertyPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopItem extends Group {

    private final DDGame game;
    private final TowerPropertyPrototype prototype;
    private final Button item;
    private final Label price;
    private int indexInShop;

    public TowerShopItem(DDGame game, TowerShop shop, TowerPrototype proto, String style) {
        this.game = game;
        this.prototype = proto.getFirstLevelProperty();
        Skin skin = game.getResources().getSkin();
        item = new Button(skin, style);
        item.setVisible(true);
        price = new CustomLabel("" + prototype.cost, skin, "16_tinted");
        price.setVisible(true);

        item.addListener(new TowerShopInputListener(shop, proto.name));

        addActor(item);
        addActor(price);
    }

    public int getIndexInShop() {
        return indexInShop;
    }

    public void setIndexInShop(int index) {
        indexInShop = index;
        price.setPosition(
                item.getX() + item.getWidth() - price.getWidth(),
                item.getY()
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
        if (!DDGame.PAUSE && item.isVisible()) {
            int money = game.getPlayer().getCoins();
            int cost = prototype.cost;
            Touchable touchBefore = item.getTouchable();
            item.setTouchable(money < cost ? Touchable.disabled : Touchable.enabled);
            item.setDisabled(money < cost);
            boolean isTouchableChanged = touchBefore != item.getTouchable();
            if (isTouchableChanged)
                item.addAction(
                        Actions.parallel(
                                Actions.moveBy(0, money < cost ? -20 : 20, .5f),
                                Actions.alpha(money < cost ? 0.8f : 1, .5f)
                        )
                );
        }
    }

    @Override
    public float getHeight() {
        return item.getHeight();
    }

    @Override
    public float getWidth() {
        return item.getWidth();
    }
}

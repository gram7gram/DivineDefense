package ua.gram.model.actor.market;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.listener.TowerShopInputListener;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.prototype.TowerPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopItem extends Group {

    private final DDGame game;
    private final Button item;
    private final Label price;

    public TowerShopItem(DDGame game, TowerShop shop, TowerPrototype prototype, String style) {
        this.game = game;
        Skin skin = game.getResources().getSkin();
        item = new Button(skin, style);
        item.setSize(DDGame.DEFAULT_BUTTON_HEIGHT, DDGame.DEFAULT_BUTTON_HEIGHT);
        price = new Label("" + prototype.cost, skin, "16_tinted");
        price.setVisible(true);
        item.setVisible(true);

        item.addListener(new TowerShopInputListener(game, shop, prototype.name));

        this.addActor(item);
        this.addActor(price);
    }

    public void setIndex(int index) {
        item.setPosition(DDGame.WORLD_WIDTH - DDGame.DEFAULT_BUTTON_HEIGHT * index - 5 * index, 5);
        price.setPosition(
                item.getX() + item.getWidth() - price.getWidth(),
                item.getY()
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE && item.isVisible()) {
            int money = game.getPlayer().getCoins();
            int cost = Integer.parseInt(price.getText().toString());
            item.setTouchable(money < cost ? Touchable.disabled : Touchable.enabled);
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

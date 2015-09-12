package ua.gram.model.actor.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.DDGame;
import ua.gram.model.prototype.MarketCategoryPrototype;
import ua.gram.model.prototype.MarketItemPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketCategoryContainer extends Group {

    private String category;
    private Button tab;

    public MarketCategoryContainer(DDGame game, MarketCategoryPrototype prototype) {
        this.category = prototype.category;
        Skin skin = game.getResources().getSkin();
        tab = new Button(skin, prototype.icon);
        float row = 1;
        float count = 0;
        for (MarketItemPrototype item : prototype.items) {
            this.addActor(new MarketItem(game, item,
                    (count % 2 == 0 && count != 0 ? ++row : row),
                    (count % 2 != 0 ? 2 : 1)));
            ++count;
        }
        Gdx.app.log("INFO", "MarketCategory " + this.category + " is OK");
    }

    public String getCategory() {
        return category;
    }

    public Button getTab() {
        return tab;
    }
}
package ua.gram.model.actor.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ua.gram.DDGame;
import ua.gram.model.prototype.MarketCategoryPrototype;
import ua.gram.model.prototype.MarketItemPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketCategoryContainer extends Table {

    private String category;
    private Button tab;

    public MarketCategoryContainer(DDGame game, MarketCategoryPrototype prototype) {
        this.category = prototype.category;
        Skin skin = game.getResources().getSkin();
        tab = new Button(skin, prototype.icon);
        for (int i = 0; i < prototype.items.length; i += 2) {
            this.add(new MarketItem(game, prototype.items[i]))
                    .height(150).pad(10).expandX().left();
            try {
                this.add(new MarketItem(game, prototype.items[i + 1]))
                        .height(150).pad(10).expandX().left().row();
            } catch (IndexOutOfBoundsException e) {
                //If there is an 'odd' index, do nothing
            }
        }
        Gdx.app.log("INFO", "Market's category [" + this.category + "] is OK");
    }

    public String getCategory() {
        return category;
    }

    public Button getTab() {
        return tab;
    }
}
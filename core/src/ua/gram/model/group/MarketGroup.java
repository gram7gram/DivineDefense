package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.actor.market.MarketCategoryContainer;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.MarketCategoryPrototype;
import ua.gram.view.screen.LevelSelectScreen;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketGroup extends Table {

    private final CustomLabel header;

    public MarketGroup(final DDGame game, MarketCategoryPrototype[] prototypes) {
        Skin skin = game.getResources().getSkin();
        header = new CustomLabel("", skin, "header1white");
        final ArrayList<MarketCategoryContainer> containers = new ArrayList<MarketCategoryContainer>();
        boolean active = false;
        for (MarketCategoryPrototype proto : prototypes) {
            final MarketCategoryContainer container = new MarketCategoryContainer(game, proto);
            final Button tab = container.getTab();
            if (!active) {
                header.setText(container.getCategory());
                tab.setChecked(true);
                active = true;
            }
            tab.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    for (MarketCategoryContainer other : containers) {
                        other.getTab().setChecked(false);
                    }
                    header.setText(container.getCategory());
                    tab.setChecked(true);
                }
            });
            containers.add(container);
        }
        Button back = new Button(skin, "back-button");
        back.setSize(80, 80);
        back.setVisible(true);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        this.add(back).width(80).height(60);
        this.add(header).height(60).expandX().row();
        for (MarketCategoryContainer container : containers) {
            this.add(container.getTab()).width(80).height(60);
            this.add().expandX().row();
        }
    }
}

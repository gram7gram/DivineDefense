package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import ua.gram.DDGame;
import ua.gram.model.actor.market.MarketCategoryContainer;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.prototype.MarketCategoryPrototype;
import ua.gram.view.screen.LevelSelectScreen;

import java.util.ArrayList;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketTable extends Table {

    public MarketTable(final DDGame game, MarketCategoryPrototype[] prototypes) {
        final Skin skin = game.getResources().getSkin();
        final ArrayList<MarketCategoryContainer> containers = new ArrayList<MarketCategoryContainer>(4);
        final CustomLabel header = new CustomLabel("", skin, "header1white");
        header.setVisible(true);
        boolean active = false;
        for (MarketCategoryPrototype proto : prototypes) {
            final MarketCategoryContainer container = new MarketCategoryContainer(game, proto);
            final Button tab = container.getTab();
            if (!active) {
                header.updateText(container.getCategory());
                tab.setChecked(true);
                container.setVisible(true);
                active = true;
            }
            tab.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    Actor current = null;
                    for (MarketCategoryContainer cont : containers) {
                        if (cont.getTab().isChecked() && current == null) {
                            current = cont;
                        }
                        cont.getTab().setChecked(false);
                        cont.setVisible(false);
                        cont.getTab().setTouchable(Touchable.enabled);
                    }
                    header.updateText(container.getCategory());
                    tab.setChecked(true);
                    tab.setTouchable(Touchable.disabled);
                    Array cells = getCells();
                    Cell cell = (Cell) cells.get(cells.size - 1);
                    if (cell != null) {
                        ScrollPane scroll = new ScrollPane(container, skin);
                        scroll.setScrollingDisabled(true, false);
                        cell.setActor(scroll);
                        container.setVisible(true);
                        cell.getActor().setVisible(true);
                        cell.expand();
                        Gdx.app.log("INFO", "Opened MarketCategory: " + container.getCategory());
                    } else {
                        Gdx.app.error("ERROR", "Missing MarketCategory: " + container.getCategory());
                    }
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
                game.setScreen(new LevelSelectScreen(game, game.getPrototype()));
            }
        });

        this.setDebug(DDGame.DEBUG);
        this.setFillParent(true);
        this.add(back).width(80).height(80);
        this.add(header).align(Align.center).height(80).expandX().row();
        Table tabs = new Table(skin);
        for (MarketCategoryContainer container : containers) {
            tabs.add(container.getTab()).width(80).height(80).row();
        }
        this.add(tabs).width(80).expandY().align(Align.top);
        ScrollPane scroll = new ScrollPane(containers.get(0), skin);
        scroll.setScrollingDisabled(true, false);
        this.add(scroll).top().left().expand();
    }
}

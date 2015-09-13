package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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

    public MarketGroup(final DDGame game, MarketCategoryPrototype[] prototypes) {
        Skin skin = game.getResources().getSkin();
        final ArrayList<MarketCategoryContainer> containers = new ArrayList<MarketCategoryContainer>(4);
        final CustomLabel header = new CustomLabel("Test", skin, "header1white");
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
                            System.out.println("Current found");
                        }
                        cont.getTab().setChecked(false);
                        cont.setVisible(false);
                    }
                    Cell cell = getCell(current);
                    if (cell != null) {
                        cell.clearActor();
                        cell.setActor(container);
                        header.updateText(container.getCategory());
                        tab.setChecked(true);
                        container.setVisible(true);
                        System.out.println("YES");
                    } else {
                        System.out.println("NOT");
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
                game.setScreen(new LevelSelectScreen(game));
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
        this.add(containers.get(0)).top().left().expand();
//        for (MarketCategoryContainer container : containers) {
//            container.setVisible(false);
//            if (containers.indexOf(container) == containers.size() - 1) {
//                this.add(container).top().left().expand();
//            } else {
//                this.add(container);
//            }
//        }
    }
}

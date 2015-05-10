package ua.gram.view.stage.group;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.view.AbstractGroup;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.Tower;

/**
 * <pre>
 * TODO Change strategies on click
 * TODO Hide all other ControlGroups on click.
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerControlsGroup extends AbstractGroup {

    private final Button sellBut;
    private final Button upgradeBut;
    private final TowerShop shop;

    public TowerControlsGroup(Skin skin, TowerShop shop) {
        super();
        this.shop = shop;
        sellBut = new Button(skin, "sell-button");
        upgradeBut = new Button(skin, "upgrade-button");
        butHeight = 45;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.toFront();
    }

    public void setGroup(final TowerGroup group) {
        final Tower tower = group.getTower();
        sellBut.setSize(butHeight, butHeight);
        sellBut.setPosition(
                tower.getX() - sellBut.getWidth() - gap,
                tower.getY() + (tower.getHeight() - sellBut.getHeight()) / 2f
        );
        sellBut.setVisible(true);
        sellBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop.sell(group);
                group.toggleControls();
            }
        });
        upgradeBut.setSize(butHeight, butHeight);
        upgradeBut.setPosition(
                tower.getX() + tower.getWidth() + gap,
                tower.getY() + (tower.getHeight() - upgradeBut.getHeight()) / 2f
        );
        upgradeBut.setVisible(true);
        upgradeBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tower.upgrade();
                group.toggleControls();
            }
        });
        this.setVisible(false);
        this.addActor(sellBut);
        this.addActor(upgradeBut);
    }

    public Button getUpgradeBut() {
        return upgradeBut;
    }

    public Button getSellBut() {
        return sellBut;
    }
}

package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
public class TowerControlsGroup extends Group {

    private final Button sellBut;
    private final Button upgradeBut;
    private final TowerShop shop;
    private Tower tower;

    public TowerControlsGroup(Skin skin, TowerShop shop) {
        super();
        this.shop = shop;
        sellBut = new Button(skin, "sell-button");
        upgradeBut = new Button(skin, "upgrade-button");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.toFront();
        if (tower != null && tower.getTowerLevel() == Tower.MAX_TOWER_LEVEL) {
            upgradeBut.setVisible(false);
        }
    }

    public void setGroup(final TowerGroup group) {
        tower = group.getTower();
        final TowerControlsGroup controls = this;
        float butHeight = DDGame.DEFAULT_BUTTON_HEIGHT * .75f;
        byte gap = 5;

        sellBut.setSize(butHeight, butHeight);
        sellBut.setPosition(
                tower.getX() - sellBut.getWidth() - gap,
                tower.getY() + (tower.getHeight() - sellBut.getHeight()) / 2f
        );
        sellBut.setVisible(true);
        sellBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controls.setVisible(false);
                shop.getStageBattle().getRange().setVisible(false);
                shop.sell(group);
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
                controls.setVisible(false);
                shop.getStageBattle().getRange().setVisible(false);
                tower.upgrade();
            }
        });
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

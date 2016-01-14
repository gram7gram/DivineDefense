package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.tower.Tower;

/**
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
        float butHeight = 45;
        sellBut.setSize(butHeight, butHeight);
        upgradeBut.setSize(butHeight, butHeight);
        this.addActor(sellBut);
        this.addActor(upgradeBut);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && tower != null) {
            this.toFront();
            if (tower.getProperty().getTowerLevel() == Tower.MAX_TOWER_LEVEL) {
                upgradeBut.setVisible(false);
            }
        }
    }

    public void setGroup(final TowerGroup group) {
        tower = group.getRootActor();
        byte gap = 5;
        sellBut.setPosition(
                tower.getX() - sellBut.getWidth() - gap,
                tower.getY() + (tower.getHeight() - sellBut.getHeight()) / 2f
        );
        sellBut.clearListeners();
        sellBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (tower != null) {
                    setVisible(false);
                    shop.sell(group);
                    tower = null;
                }
            }
        });
        upgradeBut.setPosition(
                tower.getX() + tower.getWidth() + gap,
                tower.getY() + (tower.getHeight() - upgradeBut.getHeight()) / 2f
        );
        upgradeBut.clearListeners();
        upgradeBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (tower != null) {
                    setVisible(false);
                    try {
                        tower.upgrade();
                    } catch (IllegalArgumentException e) {
                        Log.exc("Could not upgrade " + tower, e);
                    } finally {
                        tower = null;
                    }
                }
            }
        });

        upgradeBut.setVisible(true);
        sellBut.setVisible(true);
    }

    public Button getUpgradeBut() {
        return upgradeBut;
    }

    public Button getSellBut() {
        return sellBut;
    }
}

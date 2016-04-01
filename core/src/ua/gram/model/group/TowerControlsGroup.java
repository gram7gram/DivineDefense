package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.ResetableInterface;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.tower.TowerStateManager;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerControlsGroup extends Table implements ResetableInterface {

    private final Button sellBut;
    private final Button upgradeBut;
    private final TowerShop shop;
    private Tower tower;

    public TowerControlsGroup(Skin skin, TowerShop shop) {
        super();
        this.shop = shop;
        sellBut = new Button(skin, "sell-button");
        upgradeBut = new Button(skin, "upgrade-button");
        add(sellBut).size(45)
                .padRight(5);
        add().width(DDGame.DEFAULT_BUTTON_HEIGHT)
                .height(45);
        add(upgradeBut).size(45)
                .padLeft(5);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && tower != null) {
            toFront();
            if (tower.getProperty().getTowerLevel() == Tower.MAX_TOWER_LEVEL) {
                upgradeBut.setVisible(false);
            }
        }
    }

    public void setGroup(final TowerGroup group) {
        tower = group.getRootActor();
        sellBut.clearListeners();
        sellBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (tower == null) throw new NullPointerException("Sell failed: no tower");
                shop.sell(group);
                resetObject();
            }
        });
        upgradeBut.clearListeners();
        upgradeBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (tower == null) throw new NullPointerException("Upgrade failed: no tower");

                try {
                    TowerStateManager manager = tower.getStateManager();
                    manager.swap(tower,
                            tower.getStateHolder().getCurrentLevel2State(),
                            null, 2);
                    manager.swap(tower,
                            tower.getStateHolder().getCurrentLevel1State(),
                            manager.getUpgradeState(), 1);
                } catch (IllegalArgumentException e) {
                    Log.exc("Could not upgrade " + tower, e);
                } finally {
                    resetObject();
                }
            }
        });

        setPosition(
                tower.getX() + (tower.getWidth() - getWidth()) / 2f,
                tower.getY() + (tower.getHeight() - getHeight()) / 2f
        );

        setVisible(true);
    }

    public Button getUpgradeBut() {
        return upgradeBut;
    }

    public Button getSellBut() {
        return sellBut;
    }

    public Tower getTower() {
        return tower;
    }

    @Override
    public void resetObject() {
        tower = null;
        setVisible(false);
    }
}

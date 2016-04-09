package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Resetable;
import ua.gram.model.actor.misc.UpgradeButton;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.state.tower.TowerStateManager;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerControls extends Table implements Resetable {

    private final Button sellBut;
    private final UpgradeButton upgradeBut;
    private final DDGame game;
    private TowerShop shop;
    private TowerGroup towerGroup;

    public TowerControls(DDGame game, Skin skin) {
        super();
        this.game = game;
        sellBut = new Button(skin, "sell-button");
        upgradeBut = new UpgradeButton(skin, "upgrade-button");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && isVisible()) {
            toFront();
        }
    }

    private synchronized void build() {
        final TowerGroup group = getTowerGroup();
        final Tower tower = getTower();
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
                    manager.swap(tower, manager.getUpgradeState());
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

        invalidateHierarchy();

        clearChildren();

        clearActions();

        add(sellBut).size(45)
                .padRight(5);
        add().width(tower.getWidth())
                .height(45);

        if (tower.getProperty().getTowerLevel() == Tower.MAX_TOWER_LEVEL) {
            add().size(45)
                    .padLeft(5);
        } else {
            add(upgradeBut).size(45)
                    .padLeft(5);
        }

        int price = tower.getProperty().getCost();
        upgradeBut.setVisible(game.getPlayer().hasCoins(price));
        upgradeBut.updatePrice(price + "");

    }

    public void showControls() {
        build();
        setVisible(true);
        toFront();
        addAction(
                Actions.sequence(
                        Actions.moveBy(0, -5),
                        Actions.moveBy(0, 5, .1f)
                )
        );
    }

    public void hideControls() {
        setVisible(true);
        addAction(
                Actions.sequence(
                        Actions.alpha(1),
                        Actions.moveBy(0, -5, .1f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                resetObject();
                            }
                        })
                )
        );

    }

    public Actor getUpgradeBut() {
        return upgradeBut;
    }

    public Button getSellBut() {
        return sellBut;
    }

    public Tower getTower() {
        return towerGroup != null ? towerGroup.getRootActor() : null;
    }

    public void setTower(TowerGroup tower) {
        this.towerGroup = tower;
    }

    public TowerGroup getTowerGroup() {
        return towerGroup;
    }

    public void setShop(TowerShop shop) {
        this.shop = shop;
    }

    @Override
    public void resetObject() {
        towerGroup = null;
        setVisible(false);
    }
}

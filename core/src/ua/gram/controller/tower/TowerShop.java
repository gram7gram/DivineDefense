package ua.gram.controller.tower;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

import ua.gram.DDGame;
import ua.gram.controller.animation.tower.TowerAnimationChanger;
import ua.gram.controller.animation.tower.TowerAnimationProvider;
import ua.gram.controller.pool.TowerPool;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.stage.UIStage;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.controller.weapon.WeaponProvider;
import ua.gram.model.Initializer;
import ua.gram.model.actor.PositionMarker;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.TowerControls;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.group.TowerShopGroup;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.shop.ShopInterface;
import ua.gram.model.strategy.TowerStrategyManager;
import ua.gram.utils.Log;

/**
 * Container for singleton objects, which are shared among the Towers.
 * Handles managing TowerStates, purchasing and refunding.
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShop implements ShopInterface<TowerGroup>, Initializer {

    private final DDGame game;
    private final StageHolder stageHolder;
    private final TowerShopGroup towerShopGroup;
    private final TowerStrategyManager strategyManager;
    private final TowerStateManager stateManager;
    private final TowerAnimationProvider animationProvider;
    private final HashMap<TowerPrototype, Pool<Tower>> identityMap;
    private final PositionMarker marker;
    private final TowerShopConfigPrototype prototype;
    private final TowerControls controls;
    private final WeaponProvider weaponProvider;
    private final TowerPrototype[] towerPrototypes;
    private final TowerAnimationChanger animationChanger;

    public TowerShop(DDGame game, StageHolder stageHolder, TowerShopConfigPrototype prototype) {
        this.game = game;
        this.prototype = prototype;
        this.stageHolder = stageHolder;

        Skin skin = game.getResources().getSkin();

        towerPrototypes = game.getPrototype().towers;
        identityMap = new HashMap<TowerPrototype, Pool<Tower>>(towerPrototypes.length);
        animationProvider = new TowerAnimationProvider(skin, towerPrototypes);

        strategyManager = new TowerStrategyManager();
        stateManager = new TowerStateManager(game);
        towerShopGroup = new TowerShopGroup(game, prototype);
        animationChanger = new TowerAnimationChanger();

        marker = new PositionMarker(skin);
        marker.setVisible(false);

        controls = new TowerControls(game, skin);

        weaponProvider = new WeaponProvider(game, game.getPrototype().weapons);

        Log.info("TowerShop is OK");
    }

    @Override
    public void init() {

        animationProvider.init();

        stateManager.init();

        registerAll(towerPrototypes);

        weaponProvider.init();
        towerShopGroup.setTowerShop(this);
        towerShopGroup.init();
        controls.setShop(this);
        stageHolder.getUiStage().setTowerControls(controls);
        stageHolder.getBattleStage().addActor(marker);
    }

    private void registerAll(TowerPrototype[] prototypes) {
        for (TowerPrototype prototype : prototypes)
            identityMap.put(prototype, new TowerPool(game, this, prototype));
    }

    /**
     * Gets the towerGroup from the pool, according to type.
     * Does not charge towerGroup cost from player.
     *
     * @param type descendant of the TowerState
     * @param x    initial appear point
     * @param y    initial appear point
     * @return towerGroup, obtained from pool
     */
    public TowerGroup preorder(String type, float x, float y) {
        Log.info(type + " is going to be preordered from TowerShop...");

        TowerPrototype prototype = findByName(type);
        Tower tower = identityMap.get(prototype).obtain();
        tower.setPosition(x, y);

        stateManager.swap(tower, stateManager.getPreorderState());

        TowerGroup group = new TowerGroup(game, tower);
        stageHolder.getBattleStage().addActor(group);

        Log.info(tower + " has been preordered from TowerShop");

        return group;
    }

    public TowerPrototype findByName(String name) {
        TowerPrototype prototype = null;
        for (TowerPrototype p : identityMap.keySet()) {
            if (p.name.equals(name)) {
                prototype = p;
                break;
            }
        }
        if (prototype == null)
            throw new NullPointerException("Couldn't preorder tower: " + name);
        return prototype;
    }

    /**
     * Puts the towerGroup on the stage and charges towerGroup cost from player.
     *
     * @param group will be put on the stage
     * @param x     x-axis of the towerGroup
     * @param y     y-axis of the towerGroup
     */
    public void buy(TowerGroup group, float x, float y) {
        Tower tower = group.getRootActor();
        Log.info(tower + " is going to be bought from TowerShop...");
        tower.setPosition(x, y);

        stateManager.swap(tower, stateManager.getBuildingState());

        Log.info(tower + " has been bought from TowerShop");
    }

    public void sell(TowerGroup group) {
        Tower tower = group.getRootActor();
        Log.info(tower + " is going to be sold to TowerShop...");

        stateManager.swap(tower, stateManager.getSellingState());

        Log.info(tower + " has been sold to TowerShop");
    }

    public Pool<Tower> getPool(TowerPrototype prototype) {
        return identityMap.get(prototype);
    }

    @Override
    public void refund(TowerGroup group) {
        Tower tower = group.getRootActor();
        Pool<Tower> pool = getPool(tower.getPrototype());
        if (pool == null) {
            Log.crit("Missing prototype in identity map");
        } else {
            pool.free(tower);
            Log.info(tower + " is set free");
        }
        group.remove();
    }

    public TowerShopGroup getTowerShopGroup() {
        return towerShopGroup;
    }

    public BattleStage getBattleStage() {
        return stageHolder.getBattleStage();
    }

    public UIStage getUiStage() {
        return stageHolder.getUiStage();
    }

    public TowerStrategyManager getStrategyManager() {
        return strategyManager;
    }

    public TowerStateManager getStateManager() {
        return stateManager;
    }

    public TowerAnimationProvider getAnimationProvider() {
        return animationProvider;
    }

    public PositionMarker getMarker() {
        return marker;
    }

    public TowerShopConfigPrototype getPrototype() {
        return prototype;
    }

    public WeaponProvider getWeaponProvider() {
        return weaponProvider;
    }

    public TowerAnimationChanger getAnimationChanger() {
        return animationChanger;
    }
}

package ua.gram.controller.tower;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.pool.TowerPool;
import ua.gram.model.Initializer;
import ua.gram.model.ShopInterface;
import ua.gram.model.actor.PositionMarker;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.group.TowerShopGroup;
import ua.gram.model.prototype.shop.TowerShopConfigPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.stage.BattleStage;
import ua.gram.model.stage.StageHolder;
import ua.gram.model.stage.UIStage;
import ua.gram.model.state.tower.TowerStateManager;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * Container for singleton objects, which are shared among the Towers built.
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
    private final Skin skin;
    private final TowerShopConfigPrototype prototype;

    public TowerShop(DDGame game, StageHolder stageHolder, TowerShopConfigPrototype prototype) {
        this.game = game;
        this.prototype = prototype;
        this.stageHolder = stageHolder;
        skin = game.getResources().getSkin();
        TowerPrototype[] prototypes = game.getPrototype().towers;
        identityMap = new HashMap<>(prototypes.length);

        registerAll(prototypes);

        animationProvider = new TowerAnimationProvider(skin, prototypes);
        strategyManager = new TowerStrategyManager();
        stateManager = new TowerStateManager(game);
        towerShopGroup = new TowerShopGroup(game, prototype);

        marker = new PositionMarker(skin, "position-marker");
        marker.setVisible(false);

        Log.info("TowerShop is OK");
    }

    @Override
    public void init() {
        towerShopGroup.setTowerShop(this);
        towerShopGroup.init();
        stageHolder.getUiStage().setTowerControls(new TowerControlsGroup(skin, this));
        stageHolder.getBattleStage().addActor(marker);
    }

    private void registerAll(TowerPrototype[] prototypes) {
        for (TowerPrototype prototype : prototypes)
            identityMap.put(prototype, new TowerPool(game, prototype));
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
        TowerPrototype prototype = findByName(type);
        Tower tower = identityMap.get(prototype).obtain();
        Log.info(type + " is going to be preordered from TowerShop...");
        tower.setPosition(x, y);
        tower.setTowerShop(this);
        stateManager.init(tower);
        stateManager.swap(tower,
                tower.getStateHolder().getCurrentLevel1State(),
                stateManager.getPreorderState(), 1);
        TowerGroup group = new TowerGroup(game, tower);
        stageHolder.getBattleStage().addActor(group);
        Log.info(tower + " is preordered from TowerShop");
        return group;
    }

    public TowerPrototype findByName(String name) {
        Optional<TowerPrototype> prototype = identityMap.keySet().stream()
                .filter(proto -> Objects.equals(proto.name, name))
                .findFirst();
        if (!prototype.isPresent())
            throw new NullPointerException("Couldn't preorder tower: " + name);
        return prototype.get();
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
        stateManager.swap(tower,
                tower.getStateHolder().getCurrentLevel1State(),
                stateManager.getBuildingState(), 1);
        Log.info(tower + " is bought from TowerShop");
    }

    public void sell(TowerGroup group) {
        Tower tower = group.getRootActor();
        Log.info(tower + " is going to be sold to TowerShop...");
        stateManager.swap(tower,
                tower.getStateHolder().getCurrentLevel1State(),
                stateManager.getSellingState(), 1);
        Log.info(tower + " is sold to TowerShop");
    }

    public Pool<Tower> getPool(TowerPrototype prototype) {
        return identityMap.get(prototype);
    }

    @Override
    public void refund(TowerGroup group) {
        Tower tower = group.getRootActor();
        Pool<Tower> pool = this.getPool(tower.getPrototype());
        if (pool == null) {
            Log.crit("Missing prototype in identity map");
        } else {
            pool.free(tower);
            Log.info(tower + " is set free");
        }
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

    public Set<TowerPrototype> getRegisteredTowers() {
        return identityMap.keySet();
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
}

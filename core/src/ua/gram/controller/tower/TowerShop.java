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
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.ShopInterface;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerShopGroup;
import ua.gram.model.prototype.TowerPrototype;
import ua.gram.model.state.tower.TowerStateManager;
import ua.gram.model.strategy.TowerStrategyManager;

/**
 * Container for singleton objects, which are shared among the Towers built.
 * Handles managing TowerStates, purchasing and refunding.
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShop implements ShopInterface<Tower> {

    private final DDGame game;
    private final GameUIStage uiStage;
    private final GameBattleStage battleStage;
    private final TowerShopGroup towerShopGroup;
    private final TowerStrategyManager strategyManager;
    private final TowerStateManager stateManager;
    private final TowerAnimationProvider animationProvider;
    private final HashMap<TowerPrototype, Pool<Tower>> identityMap;

    public TowerShop(DDGame game, GameBattleStage battleStage, GameUIStage uiStage) {
        this.game = game;
        this.uiStage = uiStage;
        this.battleStage = battleStage;
        Skin skin = game.getResources().getSkin();
        TowerPrototype[] prototypes = game.getPrototype().towers;
        identityMap = new HashMap<>(prototypes.length);

        registerAll(prototypes);

        animationProvider = new TowerAnimationProvider(skin, prototypes);
        towerShopGroup = new TowerShopGroup(game, this);
        strategyManager = new TowerStrategyManager();
        stateManager = new TowerStateManager(game);
        this.uiStage.setTowerControls(new TowerControlsGroup(skin, this));
        Log.info("TowerShop is OK");
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
    public Tower preorder(String type, float x, float y) {
        Optional<TowerPrototype> prototype = identityMap.keySet().stream()
                .filter(proto -> Objects.equals(proto.name, type))
                .findFirst();
        if (!prototype.isPresent()) throw new NullPointerException("Couldn't buy tower: " + type);

        Tower tower = identityMap.get(prototype.get()).obtain();
        tower.setPosition(x, y);
        tower.setTowerShop(this);
        stateManager.init(tower);
        stateManager.swap(tower, null, stateManager.getPreorderState(), 1);
        battleStage.addActor(tower);
        return tower;
    }

    /**
     * Puts the towerGroup on the stage and charges towerGroup cost from player.
     *
     * @param tower will be buy on the stage
     * @param x     x-axis of the towerGroup
     * @param y     y-axis of the towerGroup
     */
    public void buy(Tower tower, float x, float y) {
        tower.setPosition(x, y);
        stateManager.swap(tower,
                tower.getStateHolder().getCurrentLevel1State(),
                stateManager.getBuildingState(), 1);
    }

    public void sell(Tower tower) {
        stateManager.swap(tower,
                tower.getStateHolder().getCurrentLevel1State(),
                stateManager.getSellingState(), 1);
    }

    /**
     * Get pool for corresponding towerGroup Class.
     *
     * @param type descendant of the TowerState
     * @return corresponding Pool
     */
    public Pool<Tower> getPool(String type) {
        return identityMap.get(type);
    }

    @Override
    public void refund(Tower tower) {
        this.getPool(tower.getName()).free(tower);
        Log.info(tower + " is set free");
    }

    public TowerShopGroup getTowerShopGroup() {
        return towerShopGroup;
    }

    public GameBattleStage getStageBattle() {
        return battleStage;
    }

    public GameUIStage getUiStage() {
        return uiStage;
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
}

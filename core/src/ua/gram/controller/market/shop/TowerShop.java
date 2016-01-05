package ua.gram.controller.market.shop;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.pool.TowerPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.Animator;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSecondary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.group.TowerShopGroup;
import ua.gram.model.strategy.StrategyManager;

import static ua.gram.model.actor.tower.Tower.SELL_RATIO;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShop {

    private final DDGame game;
    private final GameBattleStage battleStage;
    private final GameUIStage uiStage;
    private final TowerShopGroup towerShopGroup;
    private final StrategyManager strategyManager;
    private final Pool<Tower> poolPrimary;
    private final Pool<Tower> poolSecondary;
    private final Pool<Tower> poolStun;
    private final Pool<Tower> poolSpecial;

    public TowerShop(DDGame game, GameBattleStage battleStage, GameUIStage uiStage) {
        this.game = game;
        this.uiStage = uiStage;
        this.battleStage = battleStage;
        poolPrimary = new TowerPool<TowerPrimary>(game, "TowerPrimary");
        poolSecondary = new TowerPool<TowerSecondary>(game, "TowerSecondary");
        poolStun = new TowerPool<TowerStun>(game, "TowerStun");
        poolSpecial = new TowerPool<TowerSpecial>(game, "TowerSpecial");
        towerShopGroup = new TowerShopGroup(game, this);
        this.uiStage.setTowerControls(new TowerControlsGroup(game.getResources().getSkin(), this));
        strategyManager = new StrategyManager();
        Log.info("TowerShop is OK");
    }

    /**
     * Gets the towerGroup from the pool, according to type.
     * Does not charge towerGroup cost from player.
     *
     * @param type descendant of the Tower
     * @param x    initial appear point
     * @param y    initial appear point
     * @return towerGroup, obtained from pool
     * @throws CloneNotSupportedException
     */
    public Tower preorder(Class<? extends Tower> type, float x, float y) throws CloneNotSupportedException {
        Tower tower;
        if (type.equals(TowerPrimary.class)) {
            tower = ((TowerPrimary) poolPrimary.obtain()).clone();
        } else if (type.equals(TowerSecondary.class)) {
            tower = ((TowerSecondary) poolSecondary.obtain()).clone();
        } else if (type.equals(TowerStun.class)) {
            tower = ((TowerStun) poolStun.obtain()).clone();
        } else if (type.equals(TowerSpecial.class)) {
            tower = ((TowerSpecial) poolSpecial.obtain()).clone();
        } else {
            throw new NullPointerException("Couldn't build tower: " + type);
        }
        tower.setLevelAnimationContainer(tower.getTowerLevel());
        tower.changeAnimation(Animator.Types.IDLE);//displayed while moving around screen
        tower.setPosition(x, y);
        tower.setVisible(true);
        tower.setTouchable(Touchable.disabled);
        battleStage.addActor(tower);
        return tower;
    }

    /**
     * Puts the towerGroup on the stage and charges towerGroup cost from player.
     *
     * @param tower will be build on the stage
     * @param x     x-axis of the towerGroup
     * @param y     y-axis of the towerGroup
     */
    public void build(Tower tower, float x, float y) {
        tower.remove();
        tower.changeAnimation(Animator.Types.BUILD);
        tower.setPosition(x, y);
        game.getPlayer().chargeCoins(tower.getCost());
        TowerGroup towerGroup = new TowerGroup(game, tower);
        tower.setTouchable(Touchable.disabled);
        towerGroup.setVisible(true);
        tower.setOrigin(tower.getX() + 20, tower.getY() + 42);
        tower.setTowerShop(this);
        tower.setDefaultStrategy();
        battleStage.updateZIndexes(towerGroup);
        battleStage.addTowerPosition(tower);
        tower.isBuilding = true;
        Log.info(tower + " is building...");
    }

    /**
     * Removes towerGroup from stage and frees it to pool.
     * Only if dragging is aborted.
     *
     * @param tower descendant of the Tower
     */
    public void release(Tower tower) {
        tower.remove();
        this.free(tower);
    }

    public void sell(TowerGroup group) {
        battleStage.removeTowerPosition(group.getRootActor());
        int revenue = (int) (group.getRootActor().getCost() * SELL_RATIO);
        Log.info(this.getClass().getSimpleName() + " is sold for: " + revenue + " coins");
        game.getPlayer().addCoins(revenue);
        this.release(group.getRootActor());
        group.remove();
    }

    /**
     * Get pool for corresponding towerGroup Class.
     *
     * @param type descendant of the Tower
     * @return corresponding Pool
     */
    public Pool<Tower> getPool(Class<? extends Tower> type) {
        if (type.equals(TowerPrimary.class)) {
            return poolPrimary;
        } else if (type.equals(TowerSecondary.class)) {
            return poolSecondary;
        } else if (type.equals(TowerStun.class)) {
            return poolStun;
        } else if (type.equals(TowerSpecial.class)) {
            return poolSpecial;
        } else {
            throw new NullPointerException("Unknown pool for: " + type.getSimpleName());
        }
    }

    /**
     * Puts the towerGroup in corresponding Pool
     */
    public void free(Tower tower) {
        this.getPool(tower.getClass()).free(tower);
        Log.info(tower.getClass().getSimpleName() + " is set free");
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

    public StrategyManager getStrategyManager() {
        return strategyManager;
    }
}

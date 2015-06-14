package ua.gram.controller.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.pool.TowerPool;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.actor.ProgressBar;
import ua.gram.model.actor.Tower;
import ua.gram.model.actor.tower.TowerCannon;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.model.actor.weapon.Laser;
import ua.gram.model.group.TowerControlsGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.group.TowerShopGroup;

import static ua.gram.model.actor.Tower.SELL_RATIO;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShop {

    private final DDGame game;
    private final GameBattleStage stage_battle;
    private final GameUIStage stage_ui;
    private final TowerShopGroup towerShopGroup;
    private final Pool<Tower> poolPrimary;
    private final Pool<Tower> poolCannon;
    private final Pool<Tower> poolStun;
    private final Pool<Tower> poolSpecial;

    @SuppressWarnings("unchecked")
    public TowerShop(DDGame game, GameBattleStage stage_battle, GameUIStage stage_ui) {
        this.game = game;
        this.stage_ui = stage_ui;
        this.stage_battle = stage_battle;
        poolPrimary = new TowerPool(game, 5, DDGame.MAX, TowerPrimary.class);
        poolCannon = new TowerPool(game, 5, DDGame.MAX, TowerCannon.class);
        poolStun = new TowerPool(game, 5, DDGame.MAX, TowerStun.class);
        poolSpecial = new TowerPool(game, 5, DDGame.MAX, TowerSpecial.class);
        towerShopGroup = new TowerShopGroup(game, this);
        stage_ui.setTowerControls(new TowerControlsGroup(game.getResources().getSkin(), this));
        Gdx.app.log("INFO", "TowerShop is OK");
    }

    /**
     * Gets the tower from the pool, according to type.
     * Does not charge tower cost from player.
     *
     * @param type descendant of the Tower
     * @param x    initial appear point
     * @param y    initial appear point
     * @return tower, obtained from pool
     * @throws CloneNotSupportedException
     */
    public Tower preorder(Class<? extends Tower> type, float x, float y) throws CloneNotSupportedException {
        Tower tower;
        if (type.equals(TowerPrimary.class)) {
            tower = ((TowerPrimary) poolPrimary.obtain()).clone();
        } else if (type.equals(TowerCannon.class)) {
            tower = ((TowerCannon) poolCannon.obtain()).clone();
        } else if (type.equals(TowerStun.class)) {
            tower = ((TowerStun) poolStun.obtain()).clone();
        } else if (type.equals(TowerSpecial.class)) {
            tower = ((TowerSpecial) poolSpecial.obtain()).clone();
        } else {
            throw new NullPointerException("Couldn't build tower: " + type);
        }
        tower.setLevelAnimationContainer(tower.getTowerLevel());
        tower.changeAnimation(AnimationController.Types.IDLE);//displayed while moving around screen
        tower.setPosition(x, y);
        tower.setVisible(true);
        tower.setTouchable(Touchable.disabled);
        stage_battle.addActor(tower);
        return tower;
    }

    /**
     * Puts the tower on the stage.
     * Adds listener to tower and charges tower cost from player.
     *
     * @param tower will be build on the stage
     * @param x     x-axis of the tower
     * @param y     y-axis of the tower
     */
    public void build(Tower tower, float x, float y) {
        tower.remove();
        tower.changeAnimation(AnimationController.Types.BUILD);
        tower.setPosition(x, y);
        game.getPlayer().chargeCoins(tower.getCost());
        tower.setStageBattle(stage_battle);
        TowerGroup towerGroup = new TowerGroup(
                tower,
                new Laser(game.getResources(), Color.RED, tower, null),
                new ProgressBar(game.getResources().getSkin(), tower)
        );
        tower.setTouchable(Touchable.disabled);
        towerGroup.setVisible(true);
        tower.setOrigin(tower.getX() + 20, tower.getY() + 42);
        stage_battle.updateZIndexes(towerGroup);
        tower.isBuilding = true;
        Gdx.app.log("INFO", tower + " is building...");
    }

    /**
     * Removes tower from stage and frees it to pool.
     * Only if dragging is aborted.
     *
     * @param tower descendant of the Tower
     */
    public void release(Tower tower) {
        tower.remove();
        this.free(tower);
    }

    public void sell(TowerGroup group) {
        int revenue = (int) (group.getTower().getCost() * SELL_RATIO);
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " is sold for: " + revenue + " coins");
        game.getPlayer().addCoins(revenue);
        this.release(group.getTower());
        group.remove();
    }

    /**
     * Get pool for corresponding tower Class.
     *
     * @param type descendant of the Tower
     * @return corresponding Pool
     */
    public Pool<Tower> getPool(Class<? extends Tower> type) {
        if (type.equals(TowerPrimary.class)) {
            return poolPrimary;
        } else if (type.equals(TowerCannon.class)) {
            return poolCannon;
        } else if (type.equals(TowerStun.class)) {
            return poolStun;
        } else if (type.equals(TowerSpecial.class)) {
            return poolSpecial;
        } else {
            throw new NullPointerException("Unknown pool for: " + type.getSimpleName());
        }
    }

    public void free(Tower tower) {
        this.getPool(tower.getClass()).free(tower);
        Gdx.app.log("INFO", tower.getClass().getSimpleName() + " is set free");
    }

    public Pool<Tower> getPoolPrimary() {
        return poolPrimary;
    }

    public Pool<Tower> getPoolCannon() {
        return poolCannon;
    }

    public Pool<Tower> getPoolStun() {
        return poolStun;
    }

    public Pool<Tower> getPoolSpecial() {
        return poolSpecial;
    }

    public TowerShopGroup getTowerShopGroup() {
        return towerShopGroup;
    }

    public GameBattleStage getStageBattle() {
        return stage_battle;
    }

    public GameUIStage getStageUi() {
        return stage_ui;
    }
}

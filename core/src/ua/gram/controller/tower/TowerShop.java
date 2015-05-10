package ua.gram.controller.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.pool.TowerPool;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.actor.ProgressBar;
import ua.gram.model.actor.Range;
import ua.gram.model.actor.Tower;

import static ua.gram.model.actor.Tower.SELL_RATIO;

import ua.gram.model.actor.tower.TowerCannon;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.view.stage.GameBattleStage;
import ua.gram.view.stage.GameUIStage;
import ua.gram.view.stage.group.TowerControlsGroup;
import ua.gram.view.stage.group.TowerGroup;
import ua.gram.view.stage.group.TowerShopGroup;

/**
 * @author gram
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
    private final byte capacity = 5;

    @SuppressWarnings("unchecked")
    public TowerShop(DDGame game, GameUIStage stage_ui) {
        this.game = game;
        this.stage_ui = stage_ui;
        this.stage_battle = stage_ui.getBattleStage();
        poolPrimary = new TowerPool(game, capacity, DDGame.MAX, TowerPrimary.class);
        poolCannon = new TowerPool(game, capacity, DDGame.MAX, TowerCannon.class);
        poolStun = new TowerPool(game, capacity, DDGame.MAX, TowerStun.class);
        poolSpecial = new TowerPool(game, capacity, DDGame.MAX, TowerSpecial.class);
        towerShopGroup = new TowerShopGroup(game, stage_ui, this);
        Gdx.app.log("INFO", "TowerShop is OK");
    }

    /**
     * <pre>
     * Gets the tower from the pool, according to type.
     * Does not charge tower cost from player.
     * </pre>
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
     * <pre>
     * Puts the tower on the stage.
     * Adds listener to tower and charges tower cost from player.
     * </pre>
     *
     * @param tower
     * @param x
     * @param y
     */
    public void build(Tower tower, float x, float y) {
        tower.remove();
        tower.changeAnimation(AnimationController.Types.BUILD);
        tower.setPosition(x, y);
        game.getPlayer().chargeCoins(tower.getCost());
        tower.setStageBattle(stage_battle);
        tower.setTouchable(Touchable.enabled);
        final TowerGroup group = new TowerGroup(
                tower,
                tower.getWeapon(),
                new Range(game.getResources()),
                new TowerControlsGroup(game.getSkin(), this),
                new ProgressBar(game.getSkin(), tower)
        );
        group.getControls().setGroup(group);
        group.getRange().setGroup(group);
        group.setVisible(true);
        stage_battle.getTowers().addActor(group);
        stage_battle.updateZIndexes(group.getTower());
        tower.isBuilding = true;
        Gdx.app.log("INFO", tower + " is building...");
    }

    /**
     * Removes tower from stage and frees it to pool.
     * Only if dragging is canceled.
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

}

package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.TowerPrototype;
import ua.gram.model.prototype.WeaponPrototype;
import ua.gram.model.state.tower.TowerStateHolder;
import ua.gram.model.state.tower.TowerStateManager;
import ua.gram.model.strategy.tower.TowerStrategy;

/**
 * TODO Different animations: IDLE, BUILDING, SELLING, SHOOTING
 * NOTE The amount of TowerState tagets depends on it's tower_level.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Tower extends GameActor<Types.TowerState, Types.TowerLevels, TowerStateManager>
        implements Pool.Poolable {

    //    public static final byte MAX_POWER_LEVEL = 4;
    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    public final float build_delay = 2;
    protected final TowerPrototype prototype;
    protected final TowerStateHolder stateHolder;
    public boolean isActive;
    public boolean isBuilding;
    public float countBuilding = 0;
    protected DDGame game;
    protected TextureRegion currentFrame;
    protected TowerShop towerShop;
    protected Weapon weapon;
    private float damage;
    private float range;
    private float rate;
    private int cost;
    private int tower_lvl;
    private TowerStrategy currentTowerStrategy;
    private float stateTime;
    private float count = 0;
//    private int power_lvl;

    public Tower(DDGame game, TowerPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        this.tower_lvl = prototype.towerLevel;
        this.damage = prototype.damage;
        this.range = prototype.range;
        this.rate = prototype.rate;
        this.cost = prototype.cost;
        isActive = false;
        isBuilding = false;
//        this.power_lvl = prototype.powerLevel;
        stateHolder = new TowerStateHolder();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if ((!DDGame.PAUSE || currentFrame == null) && animator.getAnimation() != null) {
            currentFrame = animator.getAnimation().getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null) batch.draw(currentFrame, this.getX(), this.getY());
    }

    @Override
    public TowerGroup getParent() {
        return (TowerGroup) super.getParent();
    }

    /**
     * TowerState logic is here.
     * TODO Make TowerStateManager
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            update(delta);
            if (isBuilding) {
                if (countBuilding >= build_delay) {
                    countBuilding = 0;
                    isBuilding = false;
                    isActive = true;
                    this.setTouchable(Touchable.enabled);
                    weapon.setSource(this.getParent());
                    Log.info(this + " is builded");
                } else {
                    countBuilding += delta;
                }
            } else if (isActive) {
                if (count >= this.rate) {
                    count = 0;
                    ArrayList<EnemyGroup> targets = scan();
                    if (!targets.isEmpty()) {
                        List<EnemyGroup> victims = currentTowerStrategy.chooseVictims(this, targets);
                        if (victims != null && !victims.isEmpty()) {
                            for (EnemyGroup victimGroup : victims) {
                                Enemy victim = victimGroup.getRootActor();
                                if (isInRange(victim)) {
                                    preAttack(victim);
                                    weapon.setTarget(victimGroup);
                                    weapon.setVisible(true);
                                    attack(victim);
                                } else {
                                    looseTarget(victim);
                                    weapon.reset();
                                }
                            }
                        } else {
                            weapon.reset();
                        }
                    } else {
                        weapon.reset();
                    }
                } else {
                    count += delta;
                }
            }
        }
    }

    private void looseTarget(Enemy victim) {
        postAttack(victim);
        victim.isAttacked = false;
        weapon.reset();
    }


    public abstract void update(float delta);

    /**
     * Perform TowerState specific preparations before attack.
     *
     * @param victim the enemy to attack
     */
    public void preAttack(Enemy victim) {
    }

    /**
     * Perform towerGroup-specific attack.
     *
     * @param victim the enemy to attack
     */
    public void attack(Enemy victim) {
        victim.isAttacked = true;
        victim.damage(this.damage);
    }

    /**
     * Perform TowerState specific actions after attack.
     *
     * @param victim the enemy attacked
     */
    public void postAttack(Enemy victim) {
    }

    public abstract WeaponPrototype getWeaponPrototype();

    public abstract Weapon getWeapon();

    /**
     * Grab Enemies in range.
     *
     * @return list of all Enemies in range, whose health is not zero.
     */
    private ArrayList<EnemyGroup> scan() {
        return (ArrayList<EnemyGroup>) getStage().getEnemyGroupsOnMap().stream()
                .filter(enemy -> isInRange(enemy.getRootActor()) && enemy.getRootActor().health > 0)
                .collect(Collectors.toList());
    }

    private boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getOriginX(), enemy.getOriginY());
        Vector2 towerPos = new Vector2(this.getOriginX(), this.getOriginY());
        float distance = enemyPos.dst(towerPos);
        return distance <= this.range * DDGame.TILE_HEIGHT * 1.5;
    }

    /**
     * Increases stats of the towerGroup, according to the level and the towerGroup type.
     * TODO Increase stats
     * TODO Charge according to the next towerGroup level
     * TODO Change Range radius
     */
    public void upgrade() {
//        ++tower_lvl;
//        this.setLevelAnimationContainer(tower_lvl);
//        changeAnimation(Types.TowerState.BUILD);
//        game.getPlayer().chargeCoins(30);
//        Log.info(this + " is upgraded to " + tower_lvl + " level");
    }

//    /**
//     * TODO Set corresponding type.
//     *
//     * @param type desired animation type
//     */
//    public void changeAnimation(Types.TowerState type) {
//        Log.info(this + " animation changed to: " + tower_lvl + "_" + type.name());
//        type = Types.TowerState.IDLE;
//        this.setLevelAnimationContainer(tower_lvl);
//        this.setAnimation(container.getAnimation(type));
//    }

    @Override
    public void reset() {
        currentTowerStrategy = towerShop != null ? towerShop.getStrategyManager().getDefault() : null;
        tower_lvl = 1;
        isActive = false;
        isBuilding = false;
        this.setPosition(0, 0);
        Log.info(this + " was reset");
    }

    @Override
    public GameBattleStage getStage() {
        return (GameBattleStage) super.getStage();
    }

    public int getCost() {
        return cost;
    }

    public int getTowerLevel() {
        return tower_lvl;
    }

    public Animation getAnimation() {
        return animator.getAnimation();
    }

    public void setAnimation(PoolableAnimation animation) {
        animator.setPollable(animation);
    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
    }

    public TowerPrototype getPrototype() {
        return prototype;
    }

    public void setCurrentTowerStrategy(TowerStrategy currentTowerStrategy) {
        this.currentTowerStrategy = currentTowerStrategy;
    }

    public void setDefaultStrategy() {
        this.currentTowerStrategy = getDefaultStrategy();
    }

    public TowerStrategy getDefaultStrategy() {
        return towerShop != null ? towerShop.getStrategyManager().getDefault() : null;
    }

    public TowerShop getTowerShop() {
        return towerShop;
    }

    public void setTowerShop(TowerShop towerShop) {
        this.towerShop = towerShop;
    }

    @Override
    public TowerStateManager getStateManager() {
        if (towerShop == null) throw new NullPointerException("Missing TowerShop");
        return towerShop.getStateManager();
    }

    public TowerStateHolder getStateHolder() {
        return stateHolder;
    }
}
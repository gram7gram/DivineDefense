package ua.gram.model.actor.tower;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.animation.tower.TowerAnimationProvider;
import ua.gram.controller.pool.WeaponPool;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.tower.TowerStateHolder;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.controller.state.tower.level1.ActiveState;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.ActiveTarget;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.strategy.tower.TowerStrategy;
import ua.gram.utils.Log;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Tower
        extends GameActor<Types.TowerState, Types.TowerLevels, TowerStateManager>
        implements Pool.Poolable {

    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    protected final DDGame game;
    protected final TowerShop towerShop;
    protected final TowerPrototype prototype;
    protected final TowerProperties properties;
    protected final TowerStateHolder stateHolder;
    protected final WeaponPool weaponPool;
    private final Vector2 center;
    public float buildCount = 0;
    public float attackCount = 0;
    private List<ActiveTarget> targets;
    private TowerStrategy currentTowerStrategy;

    public Tower(DDGame game, TowerShop towerShop, TowerPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        this.towerShop = towerShop;
        properties = new TowerProperties(prototype.getFirstLevelProperty());
        stateHolder = new TowerStateHolder();
        targets = new ArrayList<ActiveTarget>(5);
        weaponPool = towerShop.getWeaponProvider().getPool(prototype.weapon);
        center = Vector2.Zero;
    }

    @Override
    public TowerGroup getParent() {
        return (TowerGroup) super.getParent();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            update(delta);

            try {
                getStateManager().update(this, delta);
            } catch (GdxRuntimeException e) {
                Log.exc("Could not update " + this + "'s state", e);
                this.remove();
            }
        }
    }

    public abstract void update(float delta);

    public Weapon getWeapon() {
        if (targets.isEmpty())
            throw new NullPointerException("Mising active targets, so no weapon can be obtained");
        return targets.get(0).getWeapon();
    }

    public boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getOriginX(), enemy.getOriginY());
        Vector2 towerPos = new Vector2(this.getOriginX(), this.getOriginY());
        float distance = enemyPos.dst(towerPos);
        return distance <= (properties.getRange() * DDGame.TILE_HEIGHT * 1.5);
    }

    @Override
    public void reset() {
        currentTowerStrategy = getDefaultStrategy();
        properties.setPropertyPrototype(prototype.getFirstLevelProperty());
        setPosition(0, 0);
        resetObject();
        Log.info(this + " was reset");
    }

    @Override
    public BattleStage getStage() {
        return (BattleStage) super.getStage();
    }

    public Animation getAnimation() {
        return animator.getAnimation();
    }

    public void setAnimation(PoolableAnimation animation) {
        animator.setPollable(animation);
    }

    public Vector2 getCenterPoint() {
        center.set(getX() + (getWidth() / 2f), getY() + (getHeight() / 2f));
        return center;
    }

    public TowerPrototype getPrototype() {
        return prototype;
    }

    public void setDefaultStrategy() {
        this.currentTowerStrategy = getDefaultStrategy();
    }

    public TowerStrategy getDefaultStrategy() {
        if (towerShop == null)
            throw new NullPointerException(this + " has no assocciated tower shop");
        return towerShop.getStrategyManager().getDefault();
    }

    public TowerShop getTowerShop() {
        return towerShop;
    }

    @Override
    public TowerStateManager getStateManager() {
        if (towerShop == null)
            throw new NullPointerException("Missing TowerShop");
        return towerShop.getStateManager();
    }

    public TowerStateHolder getStateHolder() {
        return stateHolder;
    }

    public List<ActiveTarget> getTargets() {
        return targets;
    }

    public TowerStrategy getCurrentTowerStrategy() {
        return currentTowerStrategy;
    }

    public void resetVictims() {
        if (targets.isEmpty()) return;

        for (ActiveTarget target : targets) {
            weaponPool.free(target.getWeapon());
        }
        targets.clear();
        Log.info(this + " resets targets");
    }

    public boolean isControlsVisible() {
        return towerShop.getUiStage().getTowerControls().getTower() == this;
    }

    public TowerProperties getProperties() {
        return properties;
    }

    public boolean isActiveState() {
        return getStateHolder().getCurrentLevel1State() instanceof ActiveState;
    }

    public void addTarget(ActiveTarget target) {
        targets.add(target);
    }

    public WeaponPool getWeaponPool() {
        return weaponPool;
    }

    public TowerAnimationProvider getAnimationProvider() {
        return towerShop.getAnimationProvider();
    }
}
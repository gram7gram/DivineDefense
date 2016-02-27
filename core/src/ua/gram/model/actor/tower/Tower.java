package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.TowerProperty;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.tower.TowerPropertyPrototype;
import ua.gram.model.prototype.tower.TowerPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;
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

    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    protected final TowerPrototype prototype;
    protected final TowerProperty property;
    protected final TowerStateHolder stateHolder;
    public float buildCount = 0;
    public float attackCount = 0;
    protected DDGame game;
    protected TextureRegion currentFrame;
    protected TowerShop towerShop;
    protected Weapon weapon;
    private List<EnemyGroup> victims;
    private TowerStrategy currentTowerStrategy;
    private float stateTime;

    public Tower(DDGame game, TowerPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        this.property = new TowerProperty(prototype.getFirstLevelProperty());
        stateHolder = new TowerStateHolder();
        victims = new ArrayList<>(10);
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

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            update(delta);
            getStateManager().update(this, delta);
        }
    }

    public abstract void update(float delta);

    public abstract WeaponPrototype getWeaponPrototype();

    public abstract Weapon getWeapon();

    public boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getOriginX(), enemy.getOriginY());
        Vector2 towerPos = new Vector2(this.getOriginX(), this.getOriginY());
        float distance = enemyPos.dst(towerPos);
        return distance <= property.getRange() * DDGame.TILE_HEIGHT * 1.5;
    }

    public void upgrade() {
        int price = property.getCost();
        int nextLevel = property.getTowerLevel() + 1;

        if (nextLevel > MAX_TOWER_LEVEL)
            throw new IllegalArgumentException("Cannot update to higher value, then expected");

        try {
            TowerPropertyPrototype nextProperty = prototype.getProperty(nextLevel);
            property.setPrototype(nextProperty);
            game.getPlayer().chargeCoins(price);
            Log.info(this + " is upgraded to " + nextLevel + " level");
        } catch (Exception e) {
            Log.exc("Could not upgrade " + this + " to level " + nextLevel, e);
        }
    }

    @Override
    public void reset() {
        currentTowerStrategy = getDefaultStrategy();
        property.setPrototype(prototype.getFirstLevelProperty());
        this.setPosition(0, 0);
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
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
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

    public void setTowerShop(TowerShop towerShop) {
        this.towerShop = towerShop;
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

    public List<EnemyGroup> getVictims() {
        return victims;
    }

    public void setVictims(List<EnemyGroup> victims) {
        this.victims = victims;
    }

    public TowerStrategy getCurrentTowerStrategy() {
        return currentTowerStrategy;
    }

    public void resetVictims() {
        if (!victims.isEmpty()) victims.clear();
        if (weapon.isVisible()) weapon.reset();
    }

    public TowerProperty getProperty() {
        return property;
    }
}
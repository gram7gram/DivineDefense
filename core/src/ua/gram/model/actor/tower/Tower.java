package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.List;

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

    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    protected final TowerPrototype prototype;
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
    private int tower_lvl;

    public Tower(DDGame game, TowerPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        this.tower_lvl = prototype.towerLevel;
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
        return distance <= prototype.range * DDGame.TILE_HEIGHT * 1.5;
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

    @Override
    public void reset() {
        currentTowerStrategy = towerShop != null ? towerShop.getStrategyManager().getDefault() : null;
        tower_lvl = 1;
        this.setPosition(0, 0);
        Log.info(this + " was reset");
    }

    @Override
    public GameBattleStage getStage() {
        return (GameBattleStage) super.getStage();
    }

    public int getCost() {
        return prototype.cost;
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

    public List<EnemyGroup> getVictims() {
        return victims;
    }

    public void setVictims(List<EnemyGroup> victims) {
        this.victims = victims;
    }

    public TowerStrategy getCurrentTowerStrategy() {
        return currentTowerStrategy;
    }

    public void setCurrentTowerStrategy(TowerStrategy currentTowerStrategy) {
        this.currentTowerStrategy = currentTowerStrategy;
    }

    public void resetVictims() {
        victims.clear();
        weapon.reset();
    }
}
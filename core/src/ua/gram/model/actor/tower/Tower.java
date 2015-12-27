package ua.gram.model.actor.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.comparator.EnemyDistanceComparator;
import ua.gram.controller.comparator.EnemyHealthComparator;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.tower.TowerAnimationController;
import ua.gram.controller.tower.TowerLevelAnimationContainer;
import ua.gram.model.Animator;
import ua.gram.model.actor.GameActor;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.weapon.Weapon;
import ua.gram.model.prototype.TowerPrototype;
import ua.gram.model.prototype.WeaponPrototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO Different animations: IDLE, BUILDING, SELLING, SHOOTING
 * NOTE The amount of Tower tagets depends on it's tower_level.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Tower extends GameActor implements Pool.Poolable {

    public static final float SELL_RATIO = .6f;
    public static final byte MAX_TOWER_LEVEL = 4;
    public static final byte MAX_POWER_LEVEL = 4;
    public final float build_delay = 2;
    protected final TowerPrototype prototype;
    private final EnemyDistanceComparator distanceComparator;
    private final EnemyHealthComparator healthComparator;
    public boolean isActive;
    public boolean isBuilding;
    public float countBuilding = 0;
    protected DDGame game;
    protected GameBattleStage stage_battle;
    protected TextureRegion currentFrame;
    protected TowerAnimationController controller;
    protected TowerLevelAnimationContainer container;
    protected Animation animation;
    protected Weapon weapon;
    private float damage;
    private float range;
    private float rate;
    private int cost;
    private int power_lvl;
    private int tower_lvl;
    private Strategy strategy;
    private float stateTime;
    private float count = 0;
    private int targetIndex = -1;
    private Enemy victim;

    public Tower(DDGame game, TowerPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        this.power_lvl = prototype.powerLevel;
        this.tower_lvl = prototype.towerLevel;
        this.damage = prototype.damage;
        this.range = prototype.range;
        this.rate = prototype.rate;
        this.cost = prototype.cost;
        this.strategy = Strategy.STRONGEST;
        isActive = false;
        isBuilding = false;
        controller = new TowerAnimationController(game.getResources().getSkin(), this);
        distanceComparator = new EnemyDistanceComparator(this);
        healthComparator = new EnemyHealthComparator();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE) {
            try {
                stateTime += Gdx.graphics.getDeltaTime();
                currentFrame = animation.getKeyFrame(stateTime, true);
            } catch (NullPointerException e) {
                //Prevent too soon rendering.
            }
        }
        if (currentFrame != null) batch.draw(currentFrame, this.getX(), this.getY());
    }

    /**
     * Tower logic is here.
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
                    weapon.setSource(this);
                    Log.info(this + " is builded");
                } else {
                    countBuilding += delta;
                }
            } else if (isActive) {
                if (count >= this.rate) {
                    count = 0;
                    if (victim == null) {
                        ArrayList<Enemy> targets = scan();
                        if (!targets.isEmpty()) {
                            List<Enemy> victims = chooseVictims(targets);
                            if (!victims.isEmpty()) {
                                int index = ++targetIndex <= victims.size() - 1 ? targetIndex : 0;
                                //Get enemies from different sides of the array
                                victim = victims.get(index % 2 == 0 && index != 0 ? index : victims.size() - index - 1);
                                if (isInRange(victim) && !victim.isDead) {
                                    preAttack(victim);
                                    weapon.setTarget(victim);
                                    weapon.setVisible(true);
                                    victim.isAttacked = true;
                                    weapon.toFront();
                                    victim.damage(this.damage);
                                    attack(victim);
                                }
                            } else if (victim != null) {
                                postAttack(victim);
                                victim.isAttacked = false;
                            } else {
                                Log.crit("Could not choose targets!");
                            }
                        }
                    } else {
                        victim.isAttacked = false;
                        weapon.resetTarget();
                        victim = null;
                        weapon.setVisible(false);
                    }
                } else {
                    count += delta;
                }
            }
        }
    }

    public abstract void update(float delta);

    /**
     * Perform Tower specific preparations before attack.
     *
     * @param victim the enemy to attack
     */
    public abstract void preAttack(Enemy victim);

    /**
     * Perform tower-specific attack.
     *
     * @param victim the enemy to attack
     */
    public abstract void attack(Enemy victim);

    /**
     * Perform Tower specific actions after attack.
     *
     * @param victim the enemy attacked
     */
    public abstract void postAttack(Enemy victim);

    public abstract WeaponPrototype getWeaponPrototype();

    public abstract Weapon getWeapon();
    
    /**
     * Grab Enemies in range.
     *
     * @return list of all Enemies in range, whose health is not zero.
     */
    private ArrayList<Enemy> scan() {
        ArrayList<Enemy> targets = new ArrayList<Enemy>();
        for (Enemy enemy : stage_battle.getEnemiesOnMap()) {
            if (isInRange(enemy) && enemy.health > 0) {
                targets.add(enemy);
            }
        }
        return targets;
    }

    /**
     * Choose Enemies according to Tower level and strategy.
     *
     * @param victims potential victims in range
     * @return actual victims
     */
    private List<Enemy> chooseVictims(ArrayList<Enemy> victims) {
        List<Enemy> enemyTargets = new ArrayList<Enemy>();
        switch (this.strategy) {
            case STRONGEST:
                healthComparator.setType(EnemyHealthComparator.MAX);
                Collections.sort(victims, healthComparator);
                break;
            case WEAKEST:
                healthComparator.setType(EnemyHealthComparator.MIN);
                Collections.sort(victims, healthComparator);
                break;
            case NEAREST:
                Collections.sort(victims, distanceComparator);
                break;
            case RANDOM:
                int index = victims.size() > 1 ? (int) (Math.random() * (victims.size())) : 0;
                enemyTargets.add(victims.get(index));
                return enemyTargets;
        }
        if (this.getTowerLevel() > 1 && victims.size() > 1) {
            enemyTargets = victims.subList(0, victims.size() > this.getTowerLevel() ?
                    this.getTowerLevel() : victims.size());
        } else {
            enemyTargets.add(victims.get(0));
        }
        return enemyTargets;
    }

    private boolean isInRange(Enemy enemy) {
        Vector2 enemyPos = new Vector2(enemy.getX() + enemy.getWidth() / 2f, enemy.getY() + enemy.getHeight() / 2f);
        Vector2 towerPos = new Vector2(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
        float distance = enemyPos.dst(towerPos);
        return distance <= this.range * DDGame.TILE_HEIGHT;
    }

    /**
     * Increases stats of the tower, according to the level and the tower type.
     * TODO Increase stats
     * TODO Charge according to the next tower level
     * TODO Change Range radius
     */
    public void upgrade() {
        ++tower_lvl;
        this.setLevelAnimationContainer(tower_lvl);
        changeAnimation(Animator.Types.BUILD);
        game.getPlayer().chargeCoins(30);
        Log.info(this + " is upgraded to " + tower_lvl + " level");
    }

    /**
     * TODO Set corresponding type.
     *
     * @param type desired animation type
     */
    public void changeAnimation(Animator.Types type) {
        Log.info(this + " animation changed to: " + tower_lvl + "_" + type.name());
        type = Animator.Types.IDLE;//remove
        this.setLevelAnimationContainer(tower_lvl);
        this.setAnimation(container.getAnimation(type));
    }

    @Override
    public void reset() {
        this.strategy = Strategy.STRONGEST;
        this.tower_lvl = 1;
        Log.info(this.getClass().getSimpleName() + " was reset");
    }

    public float getDamage() {
        return damage;
    }

    public void setStageBattle(GameBattleStage stage_battle) {
        this.stage_battle = stage_battle;
    }

    public float getRange() {
        return range;
    }

    public float getRate() {
        return rate;
    }

    public int getCost() {
        return cost;
    }

    public int getPowerLevel() {
        return power_lvl;
    }

    public int getTowerLevel() {
        return tower_lvl;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public TowerAnimationController getController() {
        return controller;
    }

    public void setController(TowerAnimationController controller) {
        this.controller = controller;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
    }

    public void setLevelAnimationContainer(int level) {
        container = controller.getLevelAnimationContainer(level);
    }

    public enum Strategy {

        NEAREST, RANDOM, WEAKEST, STRONGEST
    }
}
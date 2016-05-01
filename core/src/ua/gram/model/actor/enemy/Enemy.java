package ua.gram.model.actor.enemy;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.enemy.DirectionHolder;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.event.DamageEvent;
import ua.gram.controller.listener.DamageListener;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.enemy.EnemyStateHolder;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.Animator;
import ua.gram.model.Initializer;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Path;
import ua.gram.model.map.WalkablePath;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy
        extends GameActor<Types.EnemyState, Path.Direction, EnemyStateManager>
        implements Pool.Poolable, Initializer {

    public final float defaultHealth;
    public final float defaultSpeed;
    public final float defaultArmor;
    public final int reward;
    protected final DDGame game;
    protected final EnemyPrototype prototype;
    protected final EnemyStateHolder stateHolder;
    public float health;
    public float speed;
    public float armor;
    public float spawnDuration;
    public float spawnDurationCount;
    public boolean isAttacked;
    public boolean isAffected;
    public boolean isDead;
    public boolean isRemoved;
    public boolean hasReachedCheckpoint;
    protected WalkablePath path;
    protected EnemySpawner spawner;
    private DirectionHolder directionHolder;
    private EnemyGroup group;
    private Enemy parent;

    public Enemy(DDGame game, EnemyPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        health = prototype.health;
        speed = meddle(prototype.speed);
        armor = prototype.armor;
        reward = prototype.reward;
        spawnDuration = prototype.spawnDuration;
        defaultHealth = health;
        defaultSpeed = speed;
        defaultArmor = armor;
        isDead = false;
        isRemoved = false;
        hasReachedCheckpoint = true;
        stateHolder = new EnemyStateHolder();
    }

    @Override
    public void init() {
        directionHolder = new DirectionHolder(this);
        addListener(new DamageListener(this, game.getResources().getSkin()));
    }

    @Override
    public EnemyGroup getParent() {
        if (!(super.getParent() instanceof EnemyGroup))
            throw new GdxRuntimeException("Enemy parent is not EnemyGroup");
        return (EnemyGroup) super.getParent();
    }

    @Override
    public BattleStage getStage() {
        if (!(super.getStage() instanceof BattleStage))
            throw new GdxRuntimeException("Enemy stage is not BattleStage");
        return (BattleStage) super.getStage();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && !isRemoved) {

            if (game.getSpeed().isIncreased()) {
                updateAnimationSpeed();
            }

            update(delta);

            getStage().updateActorIndex(getParent());

            getStateManager().update(this, delta);
        }
    }

    private void updateAnimationSpeed() {
        float delay = game.getSpeed().getValue() * prototype.frameDuration;
        spawner.getAnimationProvider().get(prototype, animator).setDelay(delay);
    }

    /**
     * Perform Enemy-specific action.
     * Executes every iteration, if not paused.
     */
    public abstract void update(float delta);

    @Override
    public void reset() {
        spawner.getStateManager().reset(this);
        health = prototype.health;
        speed = meddle(prototype.speed);
        armor = prototype.armor;
        path = null;
        isDead = false;
        isAttacked = false;
        isRemoved = false;
        hasReachedCheckpoint = true;
        directionHolder.resetObject();
        resetObject();
        Log.info(this + " was reset");
    }

    @Override
    public EnemyStateManager getStateManager() {
        if (spawner == null)
            throw new NullPointerException("Missing EnemySpawner");
        return spawner.getStateManager();
    }

    /**
     * TODO Meddle with param to make slight difference between enemy stats
     */
    private float meddle(float param) {
        return param;
    }

    public void setAnimation(PoolableAnimation animation) {
        animator.setPollable(animation);
    }

    public void damage(float damage) {
        health -= damage;
        fire(new DamageEvent(damage));
    }

    public EnemySpawner getSpawner() {
        return spawner;
    }

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }

    public float getSpawnDuration() {
        return spawnDuration;
    }

    public float getSpawnDurationCount() {
        return spawnDurationCount;
    }

    public void setSpawnDurationCount(float spawnDurationCount) {
        this.spawnDurationCount = spawnDurationCount;
    }

    public void addSpawnDurationCount(float spawnDurationCount) {
        this.spawnDurationCount += spawnDurationCount;
    }

    public DirectionHolder getDirectionHolder() {
        return directionHolder;
    }

    public EnemyGroup getEnemyGroup() {
        return group;
    }

    public WalkablePath getPath() {
        return path;
    }

    public void setPath(WalkablePath path) {
        this.path = path;
    }

    public void setGroup(EnemyGroup group) {
        this.group = group;
    }

    public void increaseSpeed() {
        this.speed = defaultSpeed / 2;
    }

    public void decreaseSpeed() {
        this.speed = defaultSpeed * 2;
    }

    public void resetSpeed() {
        this.speed = defaultSpeed;
    }

    public Animator<Types.EnemyState, Path.Direction> getAnimator() {
        return animator;
    }

    public EnemyPrototype getPrototype() {
        return prototype;
    }

    public EnemyStateHolder getStateHolder() {
        return stateHolder;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Enemy getParentEnemy() {
        return parent;
    }

    public void setParentEnemy(Enemy parent) {
        this.parent = parent;
    }

    public boolean hasParentEnemy() {
        return parent != null;
    }
}

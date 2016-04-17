package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.event.DamageEvent;
import ua.gram.controller.listener.DamageListener;
import ua.gram.controller.stage.BattleStage;
import ua.gram.model.Animator;
import ua.gram.model.Initializer;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.enums.Types;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Path;
import ua.gram.model.map.WalkablePath;
import ua.gram.model.prototype.enemy.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateHolder;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy extends GameActor<Types.EnemyState, Path.Types, EnemyStateManager>
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
    private float stateTime = 0;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Vector2 originPosition;
    private Vector2 previousPosition;
    private Vector2 currentPositionIndex;
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
        previousPosition = Vector2.Zero;
        originPosition = Vector2.Zero;
        currentPositionIndex = Vector2.Zero;
        stateHolder = new EnemyStateHolder();
    }

    @Override
    public void init() {
        addListener(new DamageListener(this));
    }

    @Override
    public EnemyGroup getParent() {
        return (EnemyGroup) super.getParent();
    }

    @Override
    public BattleStage getStage() {
        return (BattleStage) super.getStage();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if ((!DDGame.PAUSE || currentFrame == null) && animator.getAnimation() != null) {
            currentFrame = animator.getAnimation().getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null) batch.draw(currentFrame, getX(), getY());
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
        getAnimationProvider().get(prototype, animator).setDelay(delay);
    }

    /**
     * Perform Enemy-specific action.
     * Executes every iteration, if not paused.
     */
    public abstract void update(float delta);

    @Override
    public void reset() {
        EnemyStateManager stateManager = spawner.getStateManager();
        stateManager.reset(this);
        health = prototype.health;
        speed = meddle(prototype.speed);
        armor = prototype.armor;
        stateTime = 0;
        path = null;
        currentFrame = null;
        isDead = false;
        isAttacked = false;
        isRemoved = false;
        hasReachedCheckpoint = true;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        originPosition = Vector2.Zero;
        previousPosition = Vector2.Zero;
        currentPositionIndex = Vector2.Zero;
        Log.info(this + " was reset");
    }

    /**
     * TODO Meddle with param to make slight difference between enemy stats
     */
    private float meddle(float param) {
        return param;
    }

    public EnemyAnimationProvider getAnimationProvider() {
        return spawner.getAnimationProvider();
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

    public Vector2 getOrigin() {
        originPosition.set(
                getX() + getOriginX(),
                getY() + getOriginY());
        return originPosition;
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

    public Vector2 getPreviousDirection() {
        return previousDirection;
    }

    public Vector2 getCurrentPositionIndex() {
        currentPositionIndex.set(
                Math.round(getX()) / DDGame.TILE_HEIGHT,
                Math.round(getY()) / DDGame.TILE_HEIGHT);
        return currentPositionIndex;
    }

    public Animator<Types.EnemyState, Path.Types> getAnimator() {
        return animator;
    }

    public PoolableAnimation getPoolableAnimation() {
        return animator.getPoolable();
    }

    public void setPreviousPosition(float x, float y) {
        previousPosition.set(x, y);
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    @Override
    public EnemyStateManager getStateManager() {
        if (spawner == null) throw new NullPointerException("Missing EnemySpawner");
        return spawner.getStateManager();
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

    public Skin getSkin() {
        return game.getResources().getSkin();
    }
}

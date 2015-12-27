package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.Animator;
import ua.gram.model.EnemyPath;
import ua.gram.model.PollableAnimation;
import ua.gram.model.actor.GameActor;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.Level1State;
import ua.gram.model.state.enemy.level2.Level2State;
import ua.gram.model.state.enemy.level3.Level3State;
import ua.gram.model.state.enemy.level4.Level4State;

import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy extends GameActor implements Pool.Poolable {

    public final float defaultHealth;
    public final float defaultSpeed;
    public final float defaultArmor;
    public final int reward;
    public final EnemyPrototype prototype;
    public float health;
    public float speed;
    public float armor;
    public float spawnDuration;
    public boolean isStunned;
    public boolean isAttacked;
    public boolean isAffected;
    public boolean isDead;
    public boolean isRemoved;
    public boolean hasReachedCheckpoint;
    protected DDGame game;
    protected EnemyPath path;
    protected EnemySpawner spawner;
    private float stateTime = 0;
    private EnemyAnimationProvider animationProvider;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Animator animator;
    private Vector2 originPosition;
    private Vector2 currentDirection;
    private Vector2 previousDirection;
    private Vector2 previousPosition;
    //    private Path.Types previousPositionType;
//    private Path.Types previousDirectionType;
    private Path.Types currentDirectionType;
    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Level3State currentLevel3State;
    private Level4State currentLevel4State;

    public Enemy(DDGame game, EnemyPrototype prototype) {
        super(prototype);
        this.game = game;
        this.prototype = prototype;
        health = prototype.health;
        speed = prototype.speed * (new Random().nextInt((11 - 9) + 10) / 10 + 0.9f);
        armor = prototype.armor;
        reward = prototype.reward;
        spawnDuration = prototype.spawnDuration;
        defaultHealth = health;
        defaultSpeed = speed;
        defaultArmor = armor;
        isStunned = false;
        isAffected = false;
        isDead = false;
        isRemoved = false;
        hasReachedCheckpoint = true;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        previousPosition = Vector2.Zero;
        originPosition = Vector2.Zero;
        animator = new Animator();
    }

    @Override
    public EnemyGroup getParent() {
        return (EnemyGroup) super.getParent();
    }

    @Override
    public GameBattleStage getStage() {
        return (GameBattleStage) super.getStage();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (animator.getAnimation() == null) throw new NullPointerException("Missing animation");
        if (!DDGame.PAUSE || currentFrame == null) {
            currentFrame = animator.getAnimation().getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null) batch.draw(currentFrame, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && !isRemoved) {
            if (game.getGameSpeed() != 1)
                animationProvider.get(this, animator.getType()).setDelay(game.getGameSpeed() * prototype.frameDuration);
            update(delta);
            EnemyStateManager stateManager = spawner.getStateManager();
            if (this.health <= 0 && currentLevel1State != stateManager.getDeadState()) {
                stateManager.swapLevel1State(this, stateManager.getDeadState());
            } else {
                getStage().updateActorIndex(getParent());
                if (isStunned && !isAffected) {
                    stateManager.swapLevel4State(this, stateManager.getStunState());
                } else if (!isStunned && isAffected) {
                    stateManager.swapLevel4State(this, null);
                }
            }
            stateManager.update(this, delta);
        }
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
        speed = prototype.speed * (new Random().nextInt((11 - 9) + 10) / 10 + 0.9f);
        armor = prototype.armor;
        stateTime = 0;
        path = null;
        currentFrame = null;
        isStunned = false;
        isAffected = false;
        isDead = false;
        isAttacked = false;
        isRemoved = false;
        hasReachedCheckpoint = true;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        originPosition = Vector2.Zero;
        previousPosition = Vector2.Zero;
        Gdx.app.log("INFO", this + " was reset");
    }

    public EnemyAnimationProvider getAnimationProvider() {
        return animationProvider;
    }

    public void setAnimationProvider(EnemyAnimationProvider enemyAnimation) {
        this.animationProvider = enemyAnimation;
    }

    public Animation getAnimation() {
        return animator.getPoolable().getAnimation();
    }

    public void setAnimation(Animator.Types type) {
        AnimationPool pool = animationProvider.get(this, type);
        this.setAnimation(pool.obtain());
    }

    public void setAnimation(PollableAnimation animation) {
        this.animator.setPollable(animation);
    }

    public void damage(float damage) {
        this.health -= damage;
        Gdx.app.log("INFO", this + " receives "
                + (int) damage + " dmg, hp: " + this.health);
    }

    public EnemySpawner getSpawner() {
        return spawner;
    }

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }

    public Vector2 getOrigin() {
        originPosition.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY());
        return originPosition;
    }

    public float getSpawnDuration() {
        return spawnDuration;
    }

    public EnemyGroup getEnemyGroup() {
        return group;
    }

    public EnemyPath getPath() {
        return path;
    }

    public void setPath(EnemyPath path) {
        this.path = path;
    }

    public void setGroup(EnemyGroup group) {
        this.group = group;
    }

    public Level1State getCurrentLevel1State() {
        return currentLevel1State;
    }

    public void setCurrentLevel1State(Level1State currentLevel1State) {
        this.currentLevel1State = currentLevel1State;
    }

    public Level2State getCurrentLevel2State() {
        return currentLevel2State;
    }

    public void setCurrentLevel2State(Level2State currentLevel2State) {
        this.currentLevel2State = currentLevel2State;
    }

    public Level3State getCurrentLevel3State() {
        return currentLevel3State;
    }

    public void setCurrentLevel3State(Level3State currentLevel3State) {
        this.currentLevel3State = currentLevel3State;
    }

    public Level4State getCurrentLevel4State() {
        return currentLevel4State;
    }

    public void setCurrentLevel4State(Level4State currentLevel4State) {
        this.currentLevel4State = currentLevel4State;
    }

    public Vector2 getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Vector2 currentDirection) {
        this.currentDirection = currentDirection;
        this.previousDirection = Path.opposite(currentDirection);
        this.currentDirectionType = Path.getType(currentDirection);
//        this.previousDirectionType = Path.getType(this.previousDirection);
    }

    public void alterSpeed(float deceleration) {
        this.speed = defaultSpeed * deceleration;
    }

    public Path.Types getCurrentDirectionType() {
        return currentDirectionType;
    }

    public Vector2 getPreviousDirection() {
        return previousDirection;
    }

//    public void setPreviousDirection(Vector2 previousDirection) {
//        this.previousDirection = previousDirection;
//        this.currentDirection = Path.opposite(previousDirection);
//        this.previousDirectionType = Path.getType(previousDirection);
//        this.currentDirectionType = Path.getType(this.currentDirection);
//    }

    public Vector2 getCurrentPositionIndex() {
        return new Vector2(Math.round(this.getX()) / DDGame.TILE_HEIGHT, Math.round(this.getY()) / DDGame.TILE_HEIGHT);
    }

    public Animator getAnimator() {
        return animator;
    }

    public PollableAnimation getPoolableAnimation() {
        return animator.getPoolable();
    }

    public void setPreviousPosition(float x, float y) {
        previousPosition.set(x, y);
    }

    public Vector2 getPreviousPosition() {
        return previousPosition;
    }
}

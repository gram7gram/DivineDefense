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
import ua.gram.model.actor.GameActor;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.Level1State;
import ua.gram.model.state.enemy.level2.Level2State;
import ua.gram.model.state.enemy.level3.Level3State;
import ua.gram.model.state.enemy.level4.Level4State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy extends GameActor implements Pool.Poolable {

    public final float defaultHealth;
    public final float defaultSpeed;
    public final float defaultArmor;
    public final int reward;
    private final Vector2 originPosition;
    public float health;
    public float speed;
    public float armor;
    public float spawnDuration;
    public boolean isStunned;
    public boolean isAttacked;
    public boolean isAffected;
    public boolean isDead;
    protected DDGame game;
    protected EnemyPath path;
    private float stateTime = 0;
    private GameBattleStage stage_battle;
    private EnemyAnimationProvider animationProvider;
    private EnemySpawner spawner;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Animator animator;
    private Vector2 currentDirection;
    private Vector2 previousDirection;
    private Path.Types currentDirectionType;
    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Level3State currentLevel3State;
    private Level4State currentLevel4State;
    private Animator.Types currentLevel1StateType;
    private Animator.Types currentLevel2StateType;
    private Animator.Types currentLevel3StateType;
    private Animator.Types currentLevel4StateType;

    public Enemy(DDGame game, EnemyPrototype prototype) {
        super(prototype);
        this.game = game;
        health = prototype.health;
        speed = prototype.speed;
        armor = prototype.armor;
        reward = prototype.reward;
        spawnDuration = prototype.spawnDuration;
        defaultHealth = health;
        defaultSpeed = speed;
        defaultArmor = armor;
        isStunned = false;
        isAffected = false;
        isDead = false;
        isAttacked = false;
        currentDirection = Vector2.Zero;
        previousDirection = Vector2.Zero;
        originPosition = Vector2.Zero;
        animator = new Animator();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (animator.getAnimation() == null) return;
        if (!DDGame.PAUSE || currentFrame == null) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = animator.getAnimation().getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            update(delta);
            EnemyStateManager stateManager = spawner.getStateManager();
            if (this.health <= 0 && currentLevel1State != stateManager.getDeadState()) {
                stateManager.swapLevel1State(this, stateManager.getDeadState());
            } else {
                stage_battle.updateActorIndex(this);
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
        this.health = defaultHealth;
        this.speed = defaultSpeed;
        this.armor = defaultArmor;
        stateTime = 0;
        currentFrame = null;
        EnemyStateManager stateManager = spawner.getStateManager();
        stateManager.reset(this);
//        stateManager.swapLevel1State(this, stateManager.getInactiveState());
//        stateManager.swapLevel2State(this, stateManager.getIdleState());
        Gdx.app.log("INFO", this + " was reset");
    }

    public EnemyAnimationProvider getAnimationProvider() {
        return animationProvider;
    }

    public void setAnimationProvider(EnemyAnimationProvider enemyAnimation) {
        this.animationProvider = enemyAnimation;
    }

    public Animation getAnimation() {
        return animator.getAnimation();
    }

    public void setAnimation(Animator.Types type) {
        AnimationPool pool = animationProvider.get(
                this.getOriginType(),
                type,
                this.getCurrentDirectionType());
        this.setAnimation(pool.obtain());
    }

    public void setAnimation(Animation animation) {
        this.animator.setAnimation(animation);
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
//        return new Vector2(this.getX() + this.getOriginX(), this.getY() + this.getOriginY());
    }

    public float getSpawnDuration() {
        return spawnDuration;
    }

    public void setSpawnDuration(float spawnDuration) {
        this.spawnDuration = spawnDuration;
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

    public void setBattleStage(GameBattleStage stage_battle) {
        this.stage_battle = stage_battle;
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
        this.setCurrentDirectionType(Path.getType(currentDirection));
    }

    public void alterSpeed(float deceleration) {
        this.speed = defaultSpeed * deceleration;
    }

    public Path.Types getCurrentDirectionType() {
        return currentDirectionType;
    }

    public void setCurrentDirectionType(Path.Types currentDirectionType) {
        this.currentDirectionType = currentDirectionType;
    }

    public Vector2 getPreviousDirection() {
        return previousDirection;
    }

    public void setPreviousDirection(Vector2 previousDirection) {
        this.previousDirection = previousDirection;
    }

    public Animator.Types getCurrentLevel1StateType() {
        return currentLevel1StateType;
    }

    public void setCurrentLevel1StateType(Animator.Types currentLevel1StateType) {
        this.currentLevel1StateType = currentLevel1StateType;
    }

    public Animator.Types getCurrentLevel2StateType() {
        return currentLevel2StateType;
    }

    public void setCurrentLevel2StateType(Animator.Types currentLevel2StateType) {
        this.currentLevel2StateType = currentLevel2StateType;
    }

    public Animator.Types getCurrentLevel3StateType() {
        return currentLevel3StateType;
    }

    public void setCurrentLevel3StateType(Animator.Types currentLevel3StateType) {
        this.currentLevel3StateType = currentLevel3StateType;
    }

    public Animator.Types getCurrentLevel4StateType() {
        return currentLevel4StateType;
    }

    public void setCurrentLevel4StateType(Animator.Types currentLevel4StateType) {
        this.currentLevel4StateType = currentLevel4StateType;
    }

    public Vector2 getCurrentPositionIndex() {
        return new Vector2(Math.round(this.getX()) / DDGame.TILE_HEIGHT, Math.round(this.getY()) / DDGame.TILE_HEIGHT);
    }

    public Animator getAnimator() {
        return animator;
    }
}

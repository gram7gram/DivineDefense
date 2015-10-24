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
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.Animator;
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
    public float health;
    public float speed;
    public float armor;
    public boolean isStunned;
    public boolean isAttacked;
    public boolean isAffected;
    public boolean isDead;
    protected DDGame game;
    protected Path path;
    private float stateTime = 0;
    private GameBattleStage stage_battle;
    private EnemyAnimationProvider animationProvider;
    private EnemySpawner spawner;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Animator animator;
    private Vector2 currentDirection;
    private Path.Types currentDirectionType;
    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Level3State currentLevel3State;
    private Level4State currentLevel4State;

    public Enemy(DDGame game, EnemyPrototype prototype) {
        super(prototype);
        this.game = game;
        health = prototype.health;
        speed = prototype.speed;
        armor = prototype.armor;
        reward = prototype.reward;
        defaultHealth = health;
        defaultSpeed = speed;
        defaultArmor = armor;
        isStunned = false;
        isAffected = false;
        isDead = false;
        isAttacked = false;
        currentDirection = Vector2.Zero;
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
        EnemyStateManager stateManager = spawner.getStateManager();
        stateManager.swapLevel1State(this, stateManager.getInactiveState());
        stateManager.swapLevel2State(this, stateManager.getIdleState());
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
        this.setAnimation(this.getAnimationProvider().get(
                this.getOriginType(),
                type,
                this.getCurrentDirectionType()).obtain());
    }

    public void setAnimation(Animation animation) {
        this.animator.setAnimation(animation);
    }

    public void damage(float damage) {
        this.health -= damage;
        Gdx.app.log("INFO", this + "@" + this.hashCode() + " receives "
                + (int) damage + " dmg, hp: " + this.health);
    }

    public EnemySpawner getSpawner() {
        return spawner;
    }

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }

    public Vector2 getOrigin() {
        return new Vector2(this.getX() + this.getOriginX(), this.getY() + this.getOriginY());
//        return new Vector2(
//                this.getX() + (this.getWidth() / 2f),
//                this.getY() + (this.getHeight() / 2f)
//        );
    }

    public EnemyGroup getEnemyGroup() {
        return group;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Vector2 getPosition() {
        return new Vector2(this.getX(), this.getY());
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
}

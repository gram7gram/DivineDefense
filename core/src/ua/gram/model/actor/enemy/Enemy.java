package ua.gram.model.actor.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationController;
import ua.gram.controller.enemy.EnemyRemover;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.actor.GameActor;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.EnemyPrototype;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.*;
import ua.gram.model.state.enemy.level2.*;
import ua.gram.model.state.enemy.level3.AbilityState;
import ua.gram.model.state.enemy.level3.Level3State;
import ua.gram.model.state.enemy.level4.Level4State;
import ua.gram.model.state.enemy.level4.StunState;

import java.util.Random;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy extends GameActor implements Pool.Poolable {

    public final int reward;
    public final float defaultHealth;
    public final float defaultSpeed;
    public final float defaultArmor;
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
    private EnemyAnimationController enemyAnimation;
    private EnemySpawner spawner;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Animation animation;
    private Vector2 direction;

    private final EnemyStateManager stateManager;
    private Level1State currentLevel1State;
    private Level2State currentLevel2State;
    private Level3State currentLevel3State;
    private Level4State currentLevel4State;

    public Enemy(DDGame game, EnemyPrototype prototype) {
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

        this.setSize(prototype.width, prototype.height);
        this.setBounds(this.getX(), this.getY(), prototype.width, prototype.height);

        stateManager = new EnemyStateManager(game, this);
        stateManager.swapLevel1State(stateManager.getInactiveState());
        stateManager.swapLevel2State(stateManager.getIdleState());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || currentFrame == null) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = animation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            update(delta);
            if (this.health <= 0 && currentLevel1State != stateManager.getDeathState()) {
                stateManager.swap(currentLevel1State, stateManager.getDeathState());
                stateManager.swap(currentLevel2State, null);
                stateManager.swap(currentLevel3State, null);
                stateManager.swap(currentLevel4State, null);
            } else {
                stage_battle.updateActorIndex(this);
                if (isStunned && !isAffected) {
                    stateManager.swap(currentLevel4State, stateManager.getStunState());
                } else if (!isStunned && isAffected) {
                    stateManager.swap(currentLevel4State, null);
                }
            }
            stateManager.update(delta);
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
        Gdx.app.log("INFO", this.getClass().getSimpleName() + " was reset");
    }

    public EnemyAnimationController getEnemyAnimation() {
        return enemyAnimation;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public EnemyAnimationController getAnimationController() {
        return enemyAnimation;
    }

    public void setAnimationController(EnemyAnimationController enemyAnimation) {
        this.enemyAnimation = enemyAnimation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void receiveDamage(float damage) {
        this.health -= damage;
        Gdx.app.log("INFO", this.getClass().getSimpleName() + "@" + this.hashCode() + " recives "
                + (int) damage + " dmg, hp: " + this.health);
    }

    public EnemySpawner getSpawner() {
        return spawner;
    }

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
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

    public EnemyStateManager getStateManager() {
        return stateManager;
    }

    public Level1State getCurrentLevel1State() {
        return currentLevel1State;
    }

    public Level2State getCurrentLevel2State() {
        return currentLevel2State;
    }

    public Level3State getCurrentLevel3State() {
        return currentLevel3State;
    }

    public Level4State getCurrentLevel4State() {
        return currentLevel4State;
    }

    public void setCurrentLevel1State(Level1State currentLevel1State) {
        this.currentLevel1State = currentLevel1State;
    }

    public void setCurrentLevel2State(Level2State currentLevel2State) {
        this.currentLevel2State = currentLevel2State;
    }

    public void setCurrentLevel3State(Level3State currentLevel3State) {
        this.currentLevel3State = currentLevel3State;
    }

    public void setCurrentLevel4State(Level4State currentLevel4State) {
        this.currentLevel4State = currentLevel4State;
    }
}

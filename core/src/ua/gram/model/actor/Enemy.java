package ua.gram.model.actor;

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
import ua.gram.model.group.EnemyGroup;

/**
 * TODO Make 8 directional texture region for enemy to smooth moving
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Enemy extends Actor implements Pool.Poolable {

    public final int reward;
    public final float defaultHealth;
    public final float defaultSpeed;
    public final float defaultArmor;
    public final byte animationWidth = 60;
    public final byte animationHeight = 90;
    public float health;
    public float speed;
    public float armor;
    public boolean isStunned;
    public boolean isAttacked;
    public boolean isAffected;
    public boolean isDead;//Prevent Towers from shooting if true
    protected DDGame game;
    private float stateTime = 0;
    private GameBattleStage stage_battle;
    private EnemyAnimationController enemyAnimation;
    private EnemySpawner spawner;
    private EnemyGroup group;
    private TextureRegion currentFrame;
    private Animation animation;
    private Vector2 direction;

    public Enemy(DDGame game, float[] stats) {
        this.game = game;
        this.health = stats[0];
        this.speed = stats[1];
        this.armor = stats[2];
        this.reward = (int) stats[3];
        defaultHealth = health;
        defaultSpeed = speed;
        defaultArmor = armor;
        isStunned = false;
        isAffected = false;
        isDead = false;
        isAttacked = false;
        this.setSize(animationWidth, animationHeight);
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
            this.setOrigin(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() * .9f / 2f);
            this.setBounds(this.getX(), this.getY(), animationWidth, animationHeight);
            if (this.health <= 0) {
                die();
            } else {
                stage_battle.updateActorIndex(this);
                if (isStunned && !isAffected) {
                    isAffected = true;
                    this.speed = defaultSpeed / 2f;
                    Gdx.app.log("INFO", this + " is stunned");
                } else if (!isStunned && isAffected) {
                    isAffected = false;
                    this.speed = defaultSpeed;
                    Gdx.app.log("INFO", this + " is unstunned");
                }
            }
        }
    }

    public void die() {
        this.addAction(Actions.run(new EnemyRemover(spawner, group) {
            @Override
            public void customAction() {
                Enemy enemy = group.getEnemy();
                game.getPlayer().addCoins(enemy.reward);
                //TODO Randomly add gems as reward
                Gdx.app.log("INFO", enemy + "@" + enemy.hashCode() + " was killed");
            }
        }));
    }

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

    public void setSpawner(EnemySpawner spawner) {
        this.spawner = spawner;
    }

    public Vector2 getCenterPoint() {
        return new Vector2(
                this.getX() + (this.getWidth() / 2f),
                this.getY() + (this.getHeight() / 2f)
        );
    }

    public void setGroup(EnemyGroup group) {
        this.group = group;
    }

    public void setBattleStage(GameBattleStage stage_battle) {
        this.stage_battle = stage_battle;
    }
}

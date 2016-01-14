package ua.gram.model.actor.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.Resources;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * NOTE Set duration in WeaponPrototype less then zero to display weapon until enemy is within the reach
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor {

    protected final WeaponPrototype prototype;
    protected TowerGroup towerGroup;
    protected EnemyGroup targetGroup;
    protected TextureRegion currentFrame;
    protected Animation animation;
    protected float duration;
    protected float stateTime;
    protected float scaleX;
    protected float scaleY;

    public Weapon(Resources resources, WeaponPrototype prototype) {
        this.prototype = prototype;
        this.animation = createAnimation(resources.getSkin());
        duration = 0;
        scaleX = 1;
        scaleY = 1;
        Log.info(this.getClass().getSimpleName() + " is OK");
    }

    /**
     * Weapon will be updated if game is not paused and there is a targetGroup.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE && targetGroup != null) {
            if (!(isWeaponDurationExceeded(duration) && isFinished())) {
                duration += delta;

                int index1 = targetGroup.getRootActor().getStage() != null
                        && targetGroup.getLayer() != null
                        ? targetGroup.getLayer().getZIndex() : -1;
                int index2 = towerGroup.getRootActor().getStage() != null
                        && towerGroup.getLayer() != null
                        ? towerGroup.getLayer().getZIndex() : -1;

                handleIndexes(index1, index2);

                update(delta);
                this.setVisible(true);
            } else if (targetGroup != null) {
                reset();
            }
        }
    }

    /**
     * Each weapon may require different z-indexes to be displayed properly.
     * Move Weapon from front to back to achieve pseudo-3d effect.
     * Current implementaion toggles TowerState and Weapon z-indexes, so that they won't overlap.
     */
    protected void handleIndexes(int targetIndex, int parentIndex) {
        //NOTE toFront/toBack methods cause overlap in towerGroup and weapon animations, so used this...
        if (targetIndex < parentIndex) {
            this.setZIndex(0);
            towerGroup.getRootActor().setZIndex(1);
        } else {
            towerGroup.getRootActor().setZIndex(0);
            this.setZIndex(1);
        }
    }

    private boolean isWeaponDurationExceeded(float time) {
        float duration = prototype.getDuration();
        return !(duration > time || duration < 0);
    }

    /**
     * Weapon should be drawn in it's render(Batch) method.
     * Weapon will be drawn if game is not paused and there is a targetGroup.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || (currentFrame == null && animation != null)) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null && !isOutOfBounds())
            batch.draw(
                    currentFrame,
                    getX(),
                    getY(),
                    getWidth() * scaleX,
                    getHeight() * scaleY);
    }

    /**
     * Update your weapon here.
     */
    public abstract void update(float delta);

    protected abstract Animation createAnimation(Skin skin);

    public boolean isFinished() {
        return stateTime == 0 || animation.isAnimationFinished(stateTime);
    }

    /**
     * Reset your weapon here. Resets if targetGroup is lost, or weapon duration is exceeded
     */
    public void reset() {
        duration = 0;
        stateTime = 0;
        scaleX = 1;
        scaleY = 1;
        targetGroup = null;
        currentFrame = null;
        this.setVisible(false);
        Log.info(this.getClass().getSimpleName() + " was reset");
    }

    public abstract WeaponPrototype getPrototype();

    public void setTarget(EnemyGroup targetGroup) {
        this.targetGroup = targetGroup;
    }

    public void setSource(TowerGroup source) {
        this.towerGroup = source;
    }

    protected boolean isOutOfBounds() {
        return getX() == 0 && getY() == 0;
    }

    public void scale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }

    /**
     * Perform TowerState specific preparations before attack.
     *
     * @param victim the enemy to attack
     */
    public void preAttack(Tower tower, Enemy victim) {
        Weapon weapon = tower.getWeapon();
        weapon.setTarget(victim.getParent());
        weapon.setVisible(true);
    }

    /**
     * Perform towerGroup-specific attack.
     *
     * @param victim the enemy to attack
     */
    public void attack(Tower tower, Enemy victim) {
        victim.isAttacked = true;
        victim.damage(tower.getProperty().getDamage());
    }

    /**
     * Perform TowerState specific actions after attack.
     *
     * @param victim the enemy attacked
     */
    public void postAttack(Tower tower, Enemy victim) {
        victim.isAttacked = false;
    }
}
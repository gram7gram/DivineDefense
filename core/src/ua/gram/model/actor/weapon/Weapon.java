package ua.gram.model.actor.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Pool;

import ua.gram.DDGame;
import ua.gram.controller.builder.WeaponBuilder;
import ua.gram.model.Resetable;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.weapon.WeaponPrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;

/**
 * NOTE Set duration in WeaponPrototype less then zero to display weapon until enemy is within the reach
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor implements Resetable, Pool.Poolable {

    protected final WeaponPrototype prototype;
    protected final WeaponBuilder builder;
    protected TowerGroup towerGroup;
    protected EnemyGroup targetGroup;
    protected TextureRegion currentFrame;
    protected Animation animation;
    protected float duration;
    protected float stateTime;
    protected float scaleX;
    protected float scaleY;

    public Weapon(WeaponBuilder builder, Resources resources, WeaponPrototype prototype) {
        this.builder = builder;
        this.prototype = prototype;
        this.animation = createAnimation(resources.getSkin());
        duration = 0;
        scaleX = 1;
        scaleY = 1;
        Log.info(this.getClass().getSimpleName() + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG);
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
                setVisible(true);
            } else if (targetGroup != null) {
                resetObject();
            }
        }
    }

    /**
     * Each weapon may require different z-indexes to be displayed properly.
     * Move Weapon from front to back to achieve pseudo-3d effect.
     * Current implementation toggles TowerState and Weapon z-indexes, so that they won't overlap.
     */
    protected void handleIndexes(int targetIndex, int parentIndex) {
        //NOTE toFront/toBack methods cause overlap in towerGroup and weapon animations, so used this...
        if (targetIndex < parentIndex) {
            setZIndex(0);
            towerGroup.getRootActor().setZIndex(1);
        } else {
            towerGroup.getRootActor().setZIndex(0);
            setZIndex(1);
        }
    }

    private boolean isWeaponDurationExceeded(float time) {
        float duration = prototype.getDuration();
        return !(duration > time || duration < 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (needAnimationUpdate()) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (canBeDrawn())
            batch.draw(
                    currentFrame,
                    getX(),
                    getY(),
                    getWidth() * scaleX,
                    getHeight() * scaleY);
    }

    protected boolean needAnimationUpdate() {
        return !DDGame.PAUSE || (currentFrame == null && animation != null);
    }

    protected boolean canBeDrawn() {
        return currentFrame != null && !isOutOfBounds()
                && targetGroup != null && isTargetInRange();
    }

    protected boolean isTargetInRange() {
        return towerGroup != null
                && towerGroup.getRootActor().isInRange(targetGroup.getRootActor());
    }

    /**
     * Update your weapon here
     */
    public abstract void update(float delta);

    protected abstract Animation createAnimation(Skin skin);

    public boolean isFinished() {
        return stateTime == 0 || animation.isAnimationFinished(stateTime);
    }

    @Override
    public void resetObject() {
        remove();
        duration = 0;
        stateTime = 0;
        scaleX = 1;
        scaleY = 1;
        targetGroup = null;
        currentFrame = null;
        setVisible(false);
        Log.info(getClass().getSimpleName() + " was reset");
    }

    @Override
    public void reset() {
        resetObject();
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
     * Perform Tower specific preparations before attack.
     *
     * @param victim the enemy to attack
     */
    public void preAttack(Tower tower, Enemy victim) {
        setTarget(victim.getParent());
        setVisible(true);
    }

    /**
     * Perform Tower specific attack.
     *
     * @param victim the enemy to attack
     */
    public void attack(Tower tower, Enemy victim) {
        victim.isAttacked = true;
        victim.damage(tower.getProperty().getDamage());
    }

    /**
     * Perform Tower specific actions after attack.
     *
     * @param victim the enemy attacked
     */
    public void postAttack(Tower tower, Enemy victim) {
        victim.isAttacked = false;
    }

    public boolean hasTarget() {
        return targetGroup != null;
    }
}
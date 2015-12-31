package ua.gram.model.actor.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.WeaponPrototype;

/**
 * NOTE Set duration in WeaponPrototype less then zero to display weapon until enemy is within the reach
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor {

    protected final WeaponPrototype prototype;
    protected TowerGroup tower;
    protected EnemyGroup target;
    private float duration;
    private Animation animation;
    private float stateTime;
    private TextureRegion currentFrame;

    public Weapon(Resources resources, TowerGroup tower, EnemyGroup target) {
        if (tower == null)
            throw new NullPointerException("Empty weapon owner passed to " + this.getClass().getSimpleName());
        this.prototype = tower.getRootActor().getWeaponPrototype();
        this.tower = tower;
        this.target = target;
        this.animation = createAnimation(resources.getSkin());
        duration = 0;
    }

    /**
     * Weapon will be updated if game is not paused and there is a target.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE && this.isVisible()) {
            if (target != null && !(isWeaponDurationExceeded(duration) && isFinished())) {
                duration += delta;

                int index1 = target.getRootActor().getStage() != null ? target.getParent().getZIndex() : -1;
                int index2 = tower.getRootActor().getStage() != null ? tower.getParent().getZIndex() : -1;

                handleIndexes(index1, index2);

                update(delta);
                this.setVisible(true);
            } else {
                target = null;
                duration = 0;
                reset();
                this.setVisible(false);
            }
        }
    }

    /**
     * Each weapon may require different z-indexes to be displayed properly.
     * Move Weapon from front to back to achieve pseudo-3d effect.
     * Current implementaion toggles Tower and Weapon z-indexes, so that they won't overlap.
     */
    protected void handleIndexes(int targetIndex, int parentIndex) {
        //NOTE toFront/toBack methods cause overlap in tower and weapon animations, so used this...
        if (targetIndex < parentIndex) {
            this.setZIndex(0);
            tower.getRootActor().setZIndex(1);
        } else {
            this.setZIndex(0);
            tower.getRootActor().setZIndex(1);
        }
    }

    private boolean isWeaponDurationExceeded(float time) {
        float duration = prototype.getDuration();
        return !(duration > time || duration < 0);
    }

    /**
     * Weapon should be drawn in it's render(Batch) method.
     * Weapon will be drawn if game is not paused and there is a target.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || (currentFrame == null && animation != null)) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null && !isOutOfBounds()) batch.draw(currentFrame, getX(), getY());
    }

    /**
     * Update your weapon here.
     */
    public abstract void update(float delta);

    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    /**
     * Reset your weapon here. Resets if target is lost, or weapon duration is exceeded
     */
    public void reset() {
        stateTime = 0;
        currentFrame = null;
    }

    public abstract WeaponPrototype getPrototype();

    public void setTarget(EnemyGroup target) {
        this.target = target;
    }

    public void setSource(TowerGroup source) {
        this.tower = source;
    }

    public void resetTarget() {
        target = null;
    }

    protected boolean isOutOfBounds() {
        return getX() == 0 && getY() == 0;
    }

    protected Animation createAnimation(Skin skin) {
        return null;
    }
}

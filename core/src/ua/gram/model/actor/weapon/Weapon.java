package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.WeaponPrototype;

/**
 * NOTE Set duartion in WeaponPrototype less then zero to display weapon until enemy is within the reach
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Weapon extends Actor {

    protected final WeaponPrototype prototype;
    protected TowerGroup tower;
    protected EnemyGroup target;
    private float duration;

    public Weapon(TowerGroup tower, EnemyGroup target) {
        if (tower == null)
            throw new NullPointerException("Empty weapon owner passed to " + this.getClass().getSimpleName());
        this.prototype = tower.getTower().getWeaponPrototype();
        this.tower = tower;
        this.target = target;
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

                int index1 = target.getEnemy().getStage() != null ? target.getParent().getZIndex() : -1;
                int index2 = tower.getTower().getStage() != null ? tower.getParent().getZIndex() : -1;

                //NOTE Move Weapon from front to back to achieve pseudo-3d effect
                if (index1 < index2)
                    this.toBack();
                else
                    this.toFront();

                System.err.println(tower.getTower().getClass().getSimpleName()
                        + ":\t" + index1 + ":" + index2 + ":" + this.getZIndex());

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

    public abstract boolean isFinished();

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
        if (!DDGame.PAUSE && target != null) render(batch);
    }

    /**
     * Update your weapon here.
     */
    public abstract void update(float delta);

    /**
     * Draw your weapon here.
     *
     * @param batch draw on it
     */
    public abstract void render(Batch batch);

    /**
     * Reset your weapon here. Resets if target is lost, or weapon duration is exceeded
     */
    public abstract void reset();

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
}

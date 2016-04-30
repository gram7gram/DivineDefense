package ua.gram.controller.animation.tower;

import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerAnimationChanger implements Runnable {

    private final Object lock = new Object();
    private Tower tower;
    private Types.TowerState type;
    private Types.TowerLevels level;

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (tower == null || type == null)
            throw new NullPointerException("Missing required parameters");

        if (tower.getAnimator().hasAnimation()) {

            if (tower.getStateHolder().getCurrentLevelType() == level
                    && tower.getAnimator().getPrimaryType() == type) {
                return;
            }

            freeAnimation();
        }

        updateLevel();

        obtainAnimation();
    }

    private void updateLevel() {
        if (level != null && tower.getStateHolder().getCurrentLevelType() != level) {
            synchronized (lock) {
                tower.getStateHolder().setCurrentLevelType(level);
            }
        }
    }

    private void freeAnimation() {
        if (tower.getPoolableAnimation() == null) return;

        TowerAnimationProvider animationProvider = tower.getAnimationProvider();
        Animator<Types.TowerState, Types.TowerLevels> animator = tower.getAnimator();
        try {
            synchronized (lock) {
                AnimationPool pool = animationProvider.get(tower.getPrototype(), animator);
                pool.free(animator.getPoolable());
            }

        } catch (Exception e) {
            Log.exc("Cannot free " + tower + " previous animation", e);
        }
    }

    private void obtainAnimation() {
        TowerAnimationProvider animationProvider = tower.getAnimationProvider();
        Animator<Types.TowerState, Types.TowerLevels> animator = tower.getAnimator();
        try {
            synchronized (lock) {
                animator.setPrimaryType(type);
                animator.setSecondaryType(tower.getStateHolder().getCurrentLevelType());

                AnimationPool pool = animationProvider.get(tower.getPrototype(), animator);
                PoolableAnimation animation = pool.obtain();
                animator.setPollable(animation);
            }
        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + tower, e);
        }
    }

    public void update(Tower tower, Types.TowerState type, Types.TowerLevels level) {
        updateValues(tower, type, level);
        update();
    }

    public TowerAnimationChanger updateValues(Tower tower, Types.TowerState type, Types.TowerLevels level) {
        this.tower = tower;
        this.type = type;
        this.level = level;

        return this;
    }
}

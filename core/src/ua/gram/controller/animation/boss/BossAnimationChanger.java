package ua.gram.controller.animation.boss;

import com.badlogic.gdx.math.Vector2;

import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossAnimationChanger implements Runnable {

    private final Object lock = new Object();
    private Types.BossState type;
    private Boss boss;
    private Vector2 dir;

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (boss == null || type == null)
            throw new NullPointerException("Missing required parameters");

        if (boss.getAnimator().hasAnimation()) {

            if (Path.equal(boss.getDirectionHolder().getCurrentDirection(), dir)
                    && boss.getAnimator().getPrimaryType() == type) {
                return;
            }

            freeAnimation();
        }

        updateDirection();

        obtainAnimation();
    }

    private void updateDirection() {
        if (dir != null && boss.getDirectionHolder().getCurrentDirection() != dir) {
            synchronized (lock) {
                boss.getDirectionHolder().setCurrentDirection(dir);
            }
        }
    }

    private void freeAnimation() {
        if (boss.getPoolableAnimation() == null) return;

        BossAnimationProvider animationProvider = boss.getLevel().getAnimationProvider();
        Animator<Types.BossState, Path.Direction> animator = boss.getAnimator();
        try {
            synchronized (lock) {
                AnimationPool pool = animationProvider.get(boss.getPrototype(), animator);
                pool.free(animator.getPoolable());
            }

        } catch (Exception e) {
            Log.exc("Cannot free " + boss + " previous animation", e);
        }
    }

    private void obtainAnimation() {
        BossAnimationProvider animationProvider = boss.getLevel().getAnimationProvider();
        Animator<Types.BossState, Path.Direction> animator = boss.getAnimator();
        try {
            synchronized (lock) {
                animator.setPrimaryType(type);
                animator.setSecondaryType(boss.getDirectionHolder().getCurrentDirectionType());

                AnimationPool pool = animationProvider.get(boss.getPrototype(), animator);
                PoolableAnimation animation = pool.obtain();
                animator.setPollable(animation);
            }
        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + boss, e);
        }
    }

    public void update(Boss boss, Types.BossState type) {
        update(boss, boss.getDirectionHolder().getCurrentDirection(), type);
    }

    public void update(Boss boss, Vector2 dir, Types.BossState type) {
        updateValues(boss, dir, type);
        update();
    }

    public BossAnimationChanger updateValues(Boss boss, Vector2 dir, Types.BossState type) {
        this.boss = boss;
        this.dir = dir;
        this.type = type;

        return this;
    }
}

package ua.gram.controller.animation.enemy;

import com.badlogic.gdx.math.Vector2;

import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.PoolableAnimation;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationChanger implements Runnable {

    private final Object lock = new Object();
    private Types.EnemyState type;
    private Enemy enemy;
    private Vector2 dir;

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (enemy == null || type == null)
            throw new NullPointerException("Missing required parameters");

        if (enemy.getAnimator().hasAnimation()) {

            if (Path.equal(enemy.getDirectionHolder().getCurrentDirection(), dir)
                    && enemy.getAnimator().getPrimaryType() == type) {
                return;
            }

            freeAnimation();
        }

        updateDirection();

        obtainAnimation();
    }

    private void updateDirection() {
        if (dir != null && enemy.getDirectionHolder().getCurrentDirection() != dir) {
            synchronized (lock) {
                enemy.getDirectionHolder().setCurrentDirection(dir);
            }
        }
    }

    private void freeAnimation() {
        if (enemy.getPoolableAnimation() == null) return;

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();
        Animator<Types.EnemyState, Path.Types> animator = enemy.getAnimator();
        try {
            synchronized (lock) {
                AnimationPool pool = animationProvider.get(enemy.getPrototype(), animator);
                pool.free(animator.getPoolable());
            }

        } catch (Exception e) {
            Log.exc("Cannot free " + enemy + " previous animation", e);
        }
    }

    private void obtainAnimation() {
        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();
        Animator<Types.EnemyState, Path.Types> animator = enemy.getAnimator();
        try {
            synchronized (lock) {
                animator.setPrimaryType(type);
                animator.setSecondaryType(enemy.getDirectionHolder().getCurrentDirectionType());

                AnimationPool pool = animationProvider.get(enemy.getPrototype(), animator);
                PoolableAnimation animation = pool.obtain();
                animator.setPollable(animation);
            }
        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + enemy, e);
        }
    }

    public void update(Enemy enemy, Types.EnemyState type) {
        update(enemy, enemy.getDirectionHolder().getCurrentDirection(), type);
    }

    public void update(Enemy enemy, Vector2 dir, Types.EnemyState type) {
        updateValues(enemy, dir, type);
        update();
    }

    public EnemyAnimationChanger updateValues(Enemy enemy, Vector2 dir, Types.EnemyState type) {
        this.enemy = enemy;
        this.dir = dir;
        this.type = type;

        return this;
    }
}

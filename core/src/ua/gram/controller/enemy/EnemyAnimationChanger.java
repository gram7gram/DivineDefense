package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;
import ua.gram.controller.Log;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;

/**
 * Is requested, when Enemy owns the path.
 * It is executed if Enemy changes direction.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationChanger implements Runnable {

    private Animator.Types type;
    private Enemy enemy;
    private Vector2 dir;

    public EnemyAnimationChanger(Animator.Types type) {
        this.type = type;
    }

    @Override
    public void run() {
        if (enemy == null || type == null) throw new NullPointerException("Missing required parameters");

        if (enemy.getCurrentDirection() == dir && enemy.getAnimator().getType() == type) {
//            Log.warn("Ignored animation change from " + enemy.getAnimator().getType()
//                    + " to " + type
//                    + " on " + enemy);
            return;
        }

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();

        try {
            AnimationPool pool = animationProvider.getPool(enemy);
            pool.free(enemy.getPoolableAnimation());

            Log.info(enemy + " frees animation:"
                    + " " + enemy.getAnimator().getType()
                    + " " + enemy.getCurrentDirectionType());

        } catch (Exception e) {
            Log.exc("Cannot free " + enemy + " previous animation", e);
        }

        //NOTE Next animation may have other direction, so update is nessesary
        if (dir != null) enemy.setCurrentDirection(dir);
        enemy.getAnimator().setType(type);

        try {
            AnimationPool pool = animationProvider.getPool(enemy);
            enemy.setAnimation(pool.obtain());

            Log.info(enemy + " obtains animation to:"
                    + " " + enemy.getAnimator().getType()
                    + " " + enemy.getCurrentDirectionType());

        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + enemy, e);
        }
    }

    public EnemyAnimationChanger update(Enemy enemy, Vector2 dir, Animator.Types type) {
        this.enemy = enemy;
        this.dir = dir;
        this.type = type;

        return this;
    }

    public EnemyAnimationChanger update(Enemy enemy, Vector2 dir) {
        this.enemy = enemy;
        this.dir = dir;

        return this;
    }

    public EnemyAnimationChanger update(Enemy enemy) {
        this.enemy = enemy;
        this.dir = null;

        return this;
    }
}

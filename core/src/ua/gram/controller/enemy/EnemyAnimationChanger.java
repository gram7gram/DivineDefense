package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
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

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();

        try {
            AnimationPool pool = animationProvider.get(enemy, type);
            pool.free(enemy.getPoolableAnimation());
        } catch (Exception e) {
            Log.exc("Cannot free " + enemy + " previous animation", e);
        }

        //NOTE Next animation may have other direction, so update is nessesary
        if (dir != null) enemy.setCurrentDirection(dir);

        try {
            AnimationPool pool = animationProvider.get(enemy, type);
            enemy.setAnimation(pool.obtain());

            Gdx.app.log("INFO", enemy + " updates animation to: "
                    + type + " " + enemy.getCurrentDirectionType());

        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + enemy, e);
        }
    }

    public void update(Enemy enemy, Vector2 dir, Animator.Types type) {
        this.enemy = enemy;
        this.dir = dir;
        this.type = type;
    }

    public void update(Enemy enemy, Vector2 dir) {
        this.enemy = enemy;
        this.dir = dir;
    }

    public void update(Enemy enemy) {
        this.enemy = enemy;
        this.dir = null;
    }
}

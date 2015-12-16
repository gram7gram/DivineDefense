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

    private final Animator.Types type;
    private Enemy enemy;
    private Vector2 dir;

    public EnemyAnimationChanger(Animator.Types type) {
        this.type = type;
    }

    @Override
    public void run() {
        if (enemy == null || dir == null || type == null) throw new NullPointerException("Missing required params");

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();

        try {
            AnimationPool pool = animationProvider.get(enemy, type);
            pool.free(enemy.getPoolableAnimation());
        } catch (Exception e) {
            Log.exc("Cannot free " + enemy + " previous animation", e);
        }

        enemy.setCurrentDirection(dir);

        try {
            AnimationPool pool = animationProvider.get(enemy, type);
            enemy.setAnimation(pool.obtain());
        } catch (Exception e) {
            Log.exc("Cannot change " + enemy + " animation", e);
        }

        Gdx.app.log("INFO", enemy + " updates animation to:"
                + " " + type + " " + enemy.getCurrentDirectionType());
    }

    public void update(Enemy enemy, Vector2 dir) {
        this.enemy = enemy;
        this.dir = dir;
    }
}

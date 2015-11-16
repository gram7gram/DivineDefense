package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;

import java.util.Arrays;

/**
 * Is requested, when Enemy owns the path.
 * It is executed if Enemy changes direction.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationChanger implements Runnable {

    private Enemy enemy;
    private Vector2 dir;

    @Override
    public void run() {
        if (enemy == null || dir == null) throw new NullPointerException("Missing required params");

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();

        try {
            AnimationPool pool = animationProvider.get(
                    enemy.getOriginType(),
                    Animator.Types.WALKING,
                    enemy.getCurrentDirectionType());

            pool.free(enemy.getAnimation());
        } catch (Exception e) {
            Gdx.app.error("EXC", "Cannot free " + enemy + " previous animation"
                    + "\r\nMSG: " + e.getMessage()
                    + "\r\nSTACK: " + Arrays.toString(e.getStackTrace()));
        }

        enemy.setCurrentDirection(dir);

        try {
            AnimationPool pool = animationProvider.get(
                    enemy.getOriginType(),
                    Animator.Types.WALKING,
                    enemy.getCurrentDirectionType());

            enemy.setAnimation(pool.obtain());
            Gdx.app.log("INFO", enemy + " updates animation to:"
                    + " " + Animator.Types.WALKING
                    + " " + enemy.getCurrentDirectionType());
        } catch (Exception e) {
            Gdx.app.error("EXC", "Cannot change " + enemy + " animation"
                    + "\r\nMSG: " + e.getMessage()
                    + "\r\nSTACK: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }
}

package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;

import java.util.Arrays;

/**
 * <pre>
 * Is requested, when Enemy owns the path.
 * It is executed if Enemy changes direction.
 * </pre>
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationChanger implements Runnable {

    private final Vector2 dir;
    private final Enemy enemy;

    public EnemyAnimationChanger(Vector2 dir, Enemy enemy) {
        this.dir = dir;
        this.enemy = enemy;
    }

    @Override
    public void run() {
        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();
        try {
            AnimationPool pool = animationProvider.get(
                    enemy.getOriginType(),
                    Animator.Types.WALKING,
                    enemy.getCurrentDirectionType());

            pool.free(enemy.getAnimation());

            enemy.setCurrentDirection(dir);
            enemy.setCurrentDirectionType(Path.getType(dir));

            pool = animationProvider.get(
                    enemy.getOriginType(),
                    Animator.Types.WALKING,
                    enemy.getCurrentDirectionType());

            enemy.setAnimation(pool.obtain());
        } catch (Exception e) {
            Gdx.app.error("EXC", "Cannot change " + enemy + " animation\r\n"
                    + Arrays.toString(e.getStackTrace()));
        }
    }
}

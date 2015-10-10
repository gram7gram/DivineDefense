package ua.gram.controller.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
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
    private final EnemyAnimationController enemyAnimation;

    public EnemyAnimationChanger(Vector2 dir, Enemy enemy) {
        this.dir = dir;
        this.enemy = enemy;
        this.enemyAnimation = enemy.getAnimationController();
    }

    @Override
    public void run() {
        try {
            enemyAnimation.free(enemy.getAnimation());
            if (dir.equals(Path.EAST)) {
                enemy.setAnimation(enemyAnimation.getRightAnimation());
            } else if (dir.equals(Path.WEST)) {
                enemy.setAnimation(enemyAnimation.getLeftAnimation());
            } else if (dir.equals(Path.NORTH)) {
                enemy.setAnimation(enemyAnimation.getUpAnimation());
//                enemy.setZIndex(enemy.getZIndex() - 1);
            } else if (dir.equals(Path.SOUTH)) {
                enemy.setAnimation(enemyAnimation.getDownAnimation());
//                enemy.setZIndex(enemy.getZIndex() + 1);
            } else {
                throw new NullPointerException("Direction is not of the known values: [" + dir.x + ":" + dir.y + "]");
            }
        } catch (Exception e) {
            Gdx.app.error("EXC", "Cannot update Enemy Animation"
                    + (DDGame.DEBUG ? ": " + e + "\r\n" + Arrays.toString(e.getStackTrace()) : ""));
        }
    }
}

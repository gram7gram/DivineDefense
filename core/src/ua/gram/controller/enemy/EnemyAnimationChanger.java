package ua.gram.controller.enemy;

import com.badlogic.gdx.math.Vector2;

import ua.gram.controller.Log;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * It is executed if Enemy changes direction.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class EnemyAnimationChanger implements Runnable {

    private Types.EnemyState type;
    private Enemy enemy;
    private Vector2 dir;

    public EnemyAnimationChanger(Types.EnemyState type) {
        this.type = type;
    }

    @Override
    public void run() {
        if (enemy == null || type == null)
            throw new NullPointerException("Missing required parameters");
        if (enemy.getCurrentDirection() == dir && enemy.getAnimator().getPrimaryType() == type)
            return;

        EnemyAnimationProvider animationProvider = enemy.getAnimationProvider();

        returnOldAnimation(animationProvider);

        //NOTE Next animation may have other direction, so update is nessesary
        if (dir != null) enemy.setCurrentDirection(dir);

        setUncheckedType();

        getNewAnimation(animationProvider);
    }

    @SuppressWarnings("unchecked")
    private void setUncheckedType() {
        enemy.getAnimator().setPrimaryType(type);
    }

    private void returnOldAnimation(EnemyAnimationProvider animationProvider) {
        try {
            AnimationPool pool = animationProvider.get(enemy.getPrototype(),
                    type, enemy.getCurrentDirectionType());
            pool.free(enemy.getPoolableAnimation());

            Log.info(enemy + " frees animation:"
                    + " " + enemy.getAnimator().getPrimaryType()
                    + " " + enemy.getAnimator().getSecondaryType());

        } catch (Exception e) {
            Log.exc("Cannot free " + enemy + " previous animation", e);
        }
    }

    private void getNewAnimation(EnemyAnimationProvider animationProvider) {
        try {
            AnimationPool pool = animationProvider.get(enemy.getPrototype(),
                    type, enemy.getCurrentDirectionType());
            enemy.setAnimation(pool.obtain());

            Log.info(enemy + " obtains animation to:"
                    + " " + enemy.getAnimator().getPrimaryType()
                    + " " + enemy.getAnimator().getSecondaryType());

        } catch (Exception e) {
            Log.exc("Cannot set new animation for " + enemy, e);
        }
    }

    public EnemyAnimationChanger update(Enemy enemy, Vector2 dir, Types.EnemyState type) {
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

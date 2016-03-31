package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.EmptyStackException;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.enemy.EnemyAnimationChanger;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.Level1State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    protected EnemyAnimationChanger animationChanger;
    protected Vector2 basePosition;

    public WalkingState(DDGame game) {
        super(game);
        animationChanger = new EnemyAnimationChanger(getType());
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.WALKING;
    }

    @Override
    public void preManage(final Enemy enemy) {
        initAnimation(enemy);
        if (basePosition == null) {
            basePosition = enemy.getSpawner().getLevel().getMap()
                    .getBase().getPosition();
        }

        reset(enemy);

        Log.info(enemy + " state: " + enemy.getAnimator().getPrimaryType());
    }

    /**
     * Override the method to be able to handle moveing logics for the Actor
     *
     * @param enemy actor to move
     * @param delta graphics delta time
     * @param x     current x
     * @param y     current y
     */
    protected synchronized void move(final Enemy enemy, float delta, int x, int y) {

        Vector2 current = enemy.getCurrentDirection();
        Vector2 dir = enemy.getPath().nextDirection();

        if (!Path.compare(dir, current)) {
            enemy.addAction(Actions.parallel(
                    Actions.run(animationChanger.update(enemy, dir, getType())),
                    moveBy(enemy, dir)
            ));
        } else {
            enemy.setCurrentDirection(dir);
            enemy.addAction(moveBy(enemy, dir));
        }
    }

    @Override
    public void manage(final Enemy enemy, float delta) {
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());

        if (enemy.isRemoved) return;

        if (Path.compare(enemy.getCurrentPositionIndex(), basePosition)) {
            Log.info(enemy + " position equals to Base. Removing enemy");
            remove(enemy);
            return;
        }

        if (enemy.getPath() == null) {
            Log.crit("Missing path for " + enemy);
            remove(enemy);
            return;
        }

        if (Float.compare(x % DDGame.TILE_HEIGHT, 0) == 0 && Float.compare(x % DDGame.TILE_HEIGHT, 0) == 0) {
            if (isIterationAllowed(enemy.getUpdateIterationCount())) {

                Map map = enemy.getSpawner().getLevel().getMap();
                if (!map.checkPosition(enemy.getCurrentPositionIndex(), map.getPrototype().walkableProperty)) {
                    Log.crit(enemy + " stepped out of walking bounds");
                    remove(enemy, enemy.getSpawner().getStateManager().getDeadState());
                    return;
                }

                int prevX = Math.round(enemy.getPreviousPosition().x);
                int prevY = Math.round(enemy.getPreviousPosition().y);

                if (prevX != x || prevY != y) {
                    enemy.setUpdateIterationCount(0);

                    try {
                        move(enemy, delta, x, y);
                    } catch (EmptyStackException e) {
                        Log.warn("Direction stack is empty. Removing " + enemy);
                        remove(enemy);
                    } catch (NullPointerException e) {
                        Log.exc("Required variable is NULL. Removing " + enemy, e);
                        remove(enemy);
                    }

                    enemy.setPreviousPosition(x, y);
                }
            } else {
                enemy.addUpdateIterationCount(1);
            }
        }
    }

    protected final Action moveBy(Enemy enemy, Vector2 dir) {
        return Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * getGame().getSpeed().getValue());
    }

    protected final void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, enemy.getStateHolder().getCurrentLevel1State(), manager.getFinishState(), 1);
    }

    protected final void remove(Enemy enemy, Level1State state) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, enemy.getStateHolder().getCurrentLevel1State(), state, 1);
    }

    protected final boolean isIterationAllowed(int iteration) {
        return getGame().getSpeed().isIncreased() ? iteration > 2 : iteration > 4;
    }

    protected final void reset(Enemy enemy) {
        enemy.setPreviousPosition(-1, -1);
        enemy.speed = enemy.defaultSpeed;
        enemy.setUpdateIterationCount(0);
    }

    @Override
    public void postManage(Enemy enemy) {
        reset(enemy);
    }
}

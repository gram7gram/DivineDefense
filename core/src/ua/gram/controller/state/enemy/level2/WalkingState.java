package ua.gram.controller.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.EmptyStackException;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.controller.state.enemy.level1.Level1State;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    protected Vector2 basePosition;

    public WalkingState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.WALKING;
    }

    @Override
    public void preManage(final Enemy enemy) {
        manager.getAnimationChanger()
                .update(enemy, getType());

        super.preManage(enemy);

        if (basePosition == null) {
            basePosition = enemy.getSpawner().getLevel().getMap()
                    .getBase().getPosition();
        }

        resetState(enemy);
    }

    /**
     * Override the method to be able to handle moveing logics for the Actor
     *
     * @param enemy actor to move
     * @param delta graphics delta time
     * @param x     current x
     * @param y     current y
     */
    protected void move(final Enemy enemy, float delta, int x, int y) {

        Vector2 dir = enemy.getPath().nextDirection();

        getManager().getAnimationChanger().update(enemy, dir, getType());

        enemy.addAction(moveBy(enemy, dir));
    }

    private boolean checkFloatPosition(Enemy enemy) {
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());
        return Float.compare(x % DDGame.TILE_HEIGHT, 0) == 0
                && Float.compare(y % DDGame.TILE_HEIGHT, 0) == 0;
    }

    private boolean checkIntegerPosition(Enemy enemy) {
        int x = (int) enemy.getX();
        int y = (int) enemy.getY();
        return x % DDGame.TILE_HEIGHT == 0
                && y % DDGame.TILE_HEIGHT == 0;
    }

    @Override
    public void manage(final Enemy enemy, float delta) {

        if (!enemy.hasParent()) return;

        if (enemy.isRemoved) {
            remove(enemy);
            return;
        }

        if (Path.compare(enemy.getDirectionHolder().getCurrentPositionIndex(), basePosition)) {
            Log.info(enemy + " position equals to Base. Removing enemy");
            remove(enemy);
            return;
        }

        if (enemy.getPath() == null) {
            Log.crit("Missing path for " + enemy);
            remove(enemy);
            return;
        }

        if (checkFloatPosition(enemy)) {
            if (isIterationAllowed(enemy)) {
                handleEnemy(enemy, delta);
            } else {
                enemy.addUpdateIterationCount(1);
            }
        }
    }

    private void handleEnemy(Enemy enemy, float delta) {
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());

        Map map = enemy.getSpawner().getLevel().getMap();
        Vector2 pos = enemy.getDirectionHolder().getCurrentPositionIndex();

        if (!map.getVoter().isWalkable((int) pos.x, (int) pos.y)) {
            Log.crit(enemy + " stepped out of walking bounds");
            remove(enemy, enemy.getSpawner().getStateManager().getDeadState());
            return;
        }

        int prevX = Math.round(enemy.getDirectionHolder().getPreviousPosition().x);
        int prevY = Math.round(enemy.getDirectionHolder().getPreviousPosition().y);

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

            enemy.getDirectionHolder().setPreviousPosition(x, y);
        }
    }

    protected Action moveBy(Enemy enemy, Vector2 dir) {
        return Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * game.getSpeed().getValue());
    }

    protected void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, manager.getFinishState());
    }

    protected void remove(Enemy enemy, Level1State state) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, state);
    }

    protected boolean isIterationAllowed(Enemy enemy) {
        int iteration = enemy.getUpdateIterationCount();
        return enemy.getPrototype().speed <= 1.5f || iteration > 2;
    }

    protected void resetState(Enemy enemy) {
        enemy.getDirectionHolder().setPreviousPosition(-1, -1);
        enemy.speed = enemy.defaultSpeed;
        enemy.setUpdateIterationCount(0);
    }

    @Override
    public void postManage(Enemy enemy) {
        resetState(enemy);
    }
}

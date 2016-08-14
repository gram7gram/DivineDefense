package ua.gram.controller.state.enemy.level2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.enemy.DirectionHolder;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.controller.state.enemy.level1.Level1State;
import ua.gram.controller.voter.TiledMapVoter;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    private final String WALKING_STATE_ITERATION = "walking_state_iteration";
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
        Vector2 currentPositionCopy = enemy.getDirectionHolder().getCurrentPosition();

        int x = Math.round(currentPositionCopy.x);
        int y = Math.round(currentPositionCopy.y);

        return Float.compare(x % DDGame.TILE_HEIGHT, 0) == 0
                && Float.compare(y % DDGame.TILE_HEIGHT, 0) == 0;
    }

    @Override
    public void manage(final Enemy enemy, float delta) {

        if (!enemy.hasParent()) return;

        if (enemy.isRemoved) {
            remove(enemy);
            return;
        }

        if (!enemy.hasPath()) {
            Log.crit("Missing path for " + enemy);
            remove(enemy);
            return;
        }

        if (Path.compare(enemy.getDirectionHolder().getCurrentPositionIndex(), basePosition)) {
            Log.info(enemy + " position equals to Base. Removing enemy");
            remove(enemy);
            return;
        }

        if (checkFloatPosition(enemy)) {
            Counters counters = enemy.getCounters();
            float currentIterationCount = counters.get(WALKING_STATE_ITERATION);

            if (isIterationAllowed(enemy, currentIterationCount)) {
                counters.set(WALKING_STATE_ITERATION, 0);
                handleEnemy(enemy, delta);
            } else {
                counters.set(WALKING_STATE_ITERATION, currentIterationCount + 1);
            }
        }
    }

    private void handleEnemy(Enemy enemy, float delta) {
        Map map = enemy.getSpawner().getLevel().getMap();
        TiledMapVoter voter = map.getVoter();
        DirectionHolder directionHolder = enemy.getDirectionHolder();

        Vector2 pos = directionHolder.getCurrentPosition();
        Vector2 positionIndex = directionHolder.getCurrentPositionIndex();
        Vector2 previousPos = directionHolder.getPreviousPosition();

        int indexX = (int) positionIndex.x;
        int indexY = (int) positionIndex.y;

        if (!voter.isWalkable(indexX, indexY)) {
            Log.crit(enemy + " stepped out of walking bounds at index "
                    + Path.toString(indexX, indexY)
                    + Path.toString(positionIndex)
            );
            remove(enemy, enemy.getSpawner().getStateManager().getDeadState());
            return;
        }

        int prevX = Math.round(previousPos.x);
        int prevY = Math.round(previousPos.y);

        int x = Math.round(pos.x);
        int y = Math.round(pos.y);

        if (prevX != x || prevY != y) {

            try {
                move(enemy, delta, x, y);
                directionHolder.setPreviousPosition(x, y);
            } catch (Exception e) {
                Log.exc("Cannot move  " + enemy + " to " + Path.toString(x, y), e);
                remove(enemy);
            }
        }
    }

    protected Action moveBy(Enemy enemy, Vector2 dir) {
        Vector2 dirCopy = dir.cpy();
        float acceleration = game.getSpeed().getValue();

        int horizontal = (int) (dirCopy.x * DDGame.TILE_HEIGHT);
        int vertical = (int) (dirCopy.y * DDGame.TILE_HEIGHT);

        return Actions.moveBy(
                enemy.speed > 0 ? horizontal : 0,
                enemy.speed > 0 ? vertical : 0,
                enemy.speed * acceleration);
    }

    protected void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, manager.getFinishState());
    }

    protected void remove(Enemy enemy, Level1State state) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, state);
    }

    protected boolean isIterationAllowed(Enemy enemy, float iteration) {
        return enemy.getPrototype().speed <= 1.5f || iteration > 2;
    }

    protected void resetState(Enemy enemy) {
        enemy.speed = enemy.defaultSpeed;
        Counters counters = enemy.getCounters();
        counters.set(WALKING_STATE_ITERATION, 0);
    }

    @Override
    public void postManage(Enemy enemy) {
        resetState(enemy);
    }
}

package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.enemy.EnemyAnimationChanger;
import ua.gram.model.Animator;
import ua.gram.model.EnemyPath;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Map;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;
import ua.gram.model.state.enemy.level1.Level1State;

import java.util.EmptyStackException;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    protected EnemyAnimationChanger animationChanger;
    protected Vector2 basePosition;
    protected Vector2 dest;
    private int prevX;
    private int prevY;
    private int iteration;

    public WalkingState(DDGame game) {
        super(game);
        animationChanger = new EnemyAnimationChanger(Animator.Types.WALKING);
    }

    @Override
    public void preManage(final Enemy enemy) {
        initAnimation(enemy, Animator.Types.WALKING);
        if (basePosition == null) {
            basePosition = enemy.getSpawner().getLevel().getMap()
                    .getBase().getPosition();
        }

        enemy.speed = enemy.defaultSpeed;

        reset();

        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    /**
     * Override the method to be able to handle moveing logics for the Actor
     *
     * @param enemy actor to move
     * @param delta graphics delta time
     * @param x     current x
     * @param y     current y
     * @param dir   current direction
     */
    protected synchronized void move(final Enemy enemy, float delta, int x, int y, Vector2 dir) {

        Vector2 current = enemy.getCurrentDirection();

        boolean isSameDirection = Path.compare(dir, current);

        if (!isSameDirection) {
            enemy.addAction(Actions.parallel(
                    Actions.run(animationChanger.update(enemy, dir)),
                    moveBy(enemy, dir, null)
            ));
        } else {
            enemy.setCurrentDirection(dir);
            enemy.addAction(
                    moveBy(enemy, dir, null)
            );
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

        EnemyPath path = enemy.getPath();
        if (path == null) {
            Log.warn("Missing path for " + enemy);
            remove(enemy);
            return;
        }

        if (x % DDGame.TILE_HEIGHT == 0 && y % DDGame.TILE_HEIGHT == 0
                && isIterationAllowed(iteration)) {

            Map map = enemy.getSpawner().getLevel().getMap();
            if (!map.checkPosition(enemy.getCurrentPositionIndex(), map.getPrototype().walkableProperty)) {
                Log.warn(enemy + " stepped out of walking bounds");
                remove(enemy, enemy.getSpawner().getStateManager().getDeadState());
                return;
            }

            if ((prevX != x || prevY != y)) {
                iteration = 0;

                try {

                    move(enemy, delta, x, y, path.nextDirection());

                    prevX = x;
                    prevY = y;

                } catch (EmptyStackException e) {
                    Log.warn("Direction stack is empty. Removing " + enemy);
                    remove(enemy);
                } catch (NullPointerException e) {
                    Log.exc("Required variable is NULL. Removing " + enemy, e);
                    remove(enemy);
                }
            }
        } else {
            ++iteration;
        }
    }

    protected final Action moveBy(Enemy enemy, Vector2 dir, Action callback) {
        Action move = Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * getGame().getGameSpeed());
        return callback != null ? Actions.sequence(move, callback) : move;
    }

    protected final Action moveBy(Enemy enemy, Vector2 dir) {
        return Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * getGame().getGameSpeed());
    }

    protected final void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, enemy.getCurrentLevel1State(), manager.getFinishState(), 1);
    }

    protected final void remove(Enemy enemy, Level1State state) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        manager.swap(enemy, enemy.getCurrentLevel1State(), state, 1);
    }

    protected final boolean isIterationAllowed(int iteration) {
        return getGame().getGameSpeed() < 1 ? iteration > 2 : iteration > 4;
    }

    protected final void reset() {
        prevX = -1;
        prevY = -1;
        iteration = 0;
        dest = Vector2.Zero;
    }

    @Override
    public void postManage(Enemy enemy) {
        reset();
    }
}

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
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

import java.util.EmptyStackException;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    private final EnemyAnimationChanger walkingAnimationChanger;
    private final EnemyAnimationChanger abilityAnimationChanger;
    private Vector2 basePosition;
    private int prevX;
    private int prevY;
    private int iteration;
    private boolean removed;

    public WalkingState(DDGame game) {
        super(game);
        walkingAnimationChanger = new EnemyAnimationChanger(Animator.Types.WALKING);
        abilityAnimationChanger = new EnemyAnimationChanger(Animator.Types.ABILITY);
    }

    private void reset() {
        prevX = -1;
        prevY = -1;
        iteration = 0;
        removed = false;
    }

    @Override
    public void preManage(final Enemy enemy) {
        initAnimation(enemy, Animator.Types.WALKING);
        basePosition = enemy.getSpawner().getLevel().getMap()
                .getBase().getPosition();

        enemy.speed = enemy.defaultSpeed;

        reset();

        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    private boolean isIterationAllowed(int iteration) {
        return getGame().getGameSpeed() < 1 ? iteration > 2 : iteration > 4;
    }

    @Override
    public void manage(final Enemy enemy, float delta) {
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());

        if (x % DDGame.TILE_HEIGHT == 0 && y % DDGame.TILE_HEIGHT == 0
                && !removed && isIterationAllowed(iteration)) {
            if ((prevX != x || prevY != y)) {
                iteration = 0;

                if (Path.compare(enemy.getCurrentPosition(), basePosition)) {
                    Gdx.app.log("INFO", enemy + " position equals to Base. Removing enemy");
                    remove(enemy);
                    return;
                }

                try {
                    EnemyPath path = enemy.getPath();
                    if (path == null) throw new NullPointerException("Missing path for " + enemy);

                    Vector2 current = enemy.getCurrentDirection();
                    Vector2 dir = path.nextDirection();

                    boolean isSameDirection = Path.compare(dir, current);

                    if (!isSameDirection) {
                        walkingAnimationChanger.update(enemy, dir);
                        enemy.addAction(
                                Actions.parallel(
                                        Actions.run(walkingAnimationChanger)
                                        , moveBy(enemy, dir)
                                )
                        );
                    } else if (enemy instanceof AbilityUser && ((AbilityUser) enemy).isAbilityPossible(1)) {
                        EnemyStateManager manager = enemy.getSpawner().getStateManager();
                        stateSwapper.update(enemy, enemy.getCurrentLevel3State(), manager.getAbilityState(), 3);
                        abilityAnimationChanger.update(enemy, dir);
                        enemy.addAction(
                                Actions.sequence(
                                        Actions.parallel(
                                                Actions.run(stateSwapper),
                                                Actions.run(abilityAnimationChanger)
                                        ),
                                        Actions.sequence(
                                                Actions.delay(((AbilityUser) enemy).getAbilityDuration()),
                                                moveBy(enemy, dir)
                                        )
                                )
                        );
                    } else {
                        enemy.setCurrentDirection(dir);
                        enemy.addAction(
                                moveBy(enemy, dir)
                        );
                    }

                    prevX = x;
                    prevY = y;

                } catch (EmptyStackException e) {
                    Gdx.app.log("WARN", "Direction stack is empty. Removing " + enemy);
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

    private Action moveBy(Enemy enemy, Vector2 dir) {
        return Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * getGame().getGameSpeed());
    }

    private void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        stateSwapper.update(enemy, enemy.getCurrentLevel1State(), manager.getFinishState(), 1);
        enemy.addAction(
                Actions.parallel(
                        Actions.run(stateSwapper),
                        Actions.hide())
        );
        removed = true;
    }

    @Override
    public void postManage(Enemy enemy) {
        reset();
    }
}

package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationChanger;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.EnemyPath;
import ua.gram.model.actor.enemy.AbilityUserInterface;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

import java.util.EmptyStackException;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    private final EnemyAnimationChanger animationChanger;
    private Vector2 basePosition;
    private int prevX;
    private int prevY;
    private int iteration = 0;

    public WalkingState(DDGame game) {
        super(game);
        animationChanger = new EnemyAnimationChanger();
    }

    @Override
    public void preManage(final Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        EnemyAnimationProvider provider = spawner.getAnimationProvider();
        AnimationPool pool = provider.get(
                enemy.getOriginType(),
                Animator.Types.WALKING,
                enemy.getCurrentDirectionType());
        enemy.setAnimation(pool.obtain());
        basePosition = spawner.getLevel().getMap().getBase().getPosition();
        enemy.speed = enemy.defaultSpeed;
        prevX = -1;
        prevY = -1;

        Gdx.app.log("INFO", enemy + " state: " + Animator.Types.WALKING);
    }

    @Override
    public void manage(final Enemy enemy, float delta) {
//        int x = (int) enemy.getX();
//        int y = (int) enemy.getY();
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());

        if (x % DDGame.TILE_HEIGHT == 0 && y % DDGame.TILE_HEIGHT == 0 && iteration > 3) {
            if ((prevX != x || prevY != y)) {
                iteration = 0;
                try {
                    EnemyPath path = enemy.getPath();
                    if (path == null) throw new NullPointerException("Missing path for " + enemy);

                    if (Path.compare(enemy.getCurrentPosition(), basePosition)) {
                        Gdx.app.log("INFO", enemy + " position equals to Base. Removing enemy");
                        remove(enemy);
                    } else {
                        Vector2 current = enemy.getCurrentDirection();
                        Vector2 dir = path.nextDirection();

                        animationChanger.setEnemy(enemy);
                        animationChanger.setDir(dir);

                        if (!Path.compare(dir, current)) {
                            enemy.addAction(
                                    Actions.sequence(
                                            Actions.run(animationChanger),
                                            moveBy(enemy, dir))
                            );
                        } else if (enemy instanceof AbilityUserInterface
                                && ((AbilityUserInterface) enemy).isAbilityPossible(1)) {
                            EnemyStateManager manager = enemy.getSpawner().getStateManager();
                            stateSwapper.update(enemy, enemy.getCurrentLevel3State(), manager.getAbilityState(), 3);
                            enemy.addAction(
                                    Actions.sequence(
                                            Actions.run(stateSwapper),
                                            Actions.delay(((AbilityUserInterface) enemy).getAbilityDuration()),
                                            moveBy(enemy, dir))
                            );
                        } else {
                            enemy.addAction(
                                    moveBy(enemy, dir)
                            );
                        }

                        enemy.setPreviousDirection(Path.opposite(dir));

                        prevX = x;
                        prevY = y;
                    }

                } catch (EmptyStackException e) {
                    Gdx.app.log("WARN", "Direction stack is empty. Removing " + enemy);
                    remove(enemy);
                }
            }
        } else {
            ++iteration;
        }
    }

    private MoveByAction moveBy(Enemy enemy, Vector2 dir) {
        return Actions.moveBy(
                enemy.speed > 0 ? (int) (dir.x * DDGame.TILE_HEIGHT) : 0,
                enemy.speed > 0 ? (int) (dir.y * DDGame.TILE_HEIGHT) : 0,
                enemy.speed * getGame().getGameSpeed());
    }

    private void remove(Enemy enemy) {
        EnemyStateManager manager = enemy.getSpawner().getStateManager();
        stateSwapper.update(enemy, enemy.getCurrentLevel1State(), manager.getFinishState(), 1);
        enemy.addAction(Actions.run(stateSwapper));
        enemy.addAction(Actions.hide());
        prevX = -1;
        prevY = -1;
    }

    @Override
    public void postManage(Enemy enemy) {
        prevX = -1;
        prevY = -1;
    }
}

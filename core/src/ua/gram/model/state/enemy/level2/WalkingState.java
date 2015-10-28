package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemyAnimationProvider;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.AbilityUser;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.map.Path;
import ua.gram.model.state.enemy.EnemyStateManager;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    private Vector2 basePosition;
    private Runnable level1Swapper;

    public WalkingState(DDGame game) {
        super(game);
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
        final EnemyStateManager stateManager = spawner.getStateManager();
        level1Swapper = new Runnable() {
            @Override
            public void run() {
                stateManager.swapLevel1State(enemy, stateManager.getFinishState());
            }
        };
        Gdx.app.log("INFO", enemy + " is walking");
    }

    @Override
    public void manage(final Enemy enemy, float delta) {
        enemy.isAffected = false;
        enemy.speed = enemy.defaultSpeed;
        int x = Math.round(enemy.getX());
        int y = Math.round(enemy.getY());
        Path path = enemy.getPath();
        if (x % DDGame.TILE_HEIGHT == 0 && y % DDGame.TILE_HEIGHT == 0
                && !path.getDirections().isEmpty()) {
            try {
                Vector2 dir = path.peekNextDirection();
                if (!dir.equals(enemy.getPreviousDirection())) {
                    dir = path.nextDirection();
                    enemy.setCurrentDirection(dir);
                    if (Float.compare(x / DDGame.TILE_HEIGHT, basePosition.x) == 0
                            && Float.compare(y / DDGame.TILE_HEIGHT, basePosition.y) == 0) {
                        enemy.addAction(
                                Actions.parallel(
                                        Actions.run(level1Swapper),
                                        Actions.hide()));
                    } else {
                        SequenceAction actions = new SequenceAction();
                        enemy.setPreviousDirection(Path.opposite(dir));

                        Action currentAction = Actions.moveBy(
                                dir.x * DDGame.TILE_HEIGHT,
                                dir.y * DDGame.TILE_HEIGHT,
                                enemy.speed);

                        actions.addAction(currentAction);

                        if (enemy instanceof AbilityUser) {
                            ((AbilityUser) enemy).ability(actions);
                        }
                        enemy.addAction(actions);
                    }
                }
            } catch (EmptyStackException e) {
                Gdx.app.log("WARN", "Direction stack is empty. Removing " + enemy);
                enemy.addAction(
                        Actions.parallel(
                                Actions.run(level1Swapper),
                                Actions.hide()));
            } catch (Exception e) {
                Gdx.app.error("EXC", "Cannot move " + enemy
                        + "\r\nMSG: " + e.getMessage()
                        + "\r\nTRACE: " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    @Override
    public void postManage(Enemy enemy) {

    }
}

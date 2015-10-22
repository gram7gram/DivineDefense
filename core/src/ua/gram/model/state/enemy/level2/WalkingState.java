package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.controller.pool.animation.AnimationController;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.enemy.level2.Level2State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WalkingState extends Level2State {

    public WalkingState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        EnemySpawner spawner = enemy.getSpawner();
        spawner.setActionPath(enemy);
        enemy.setAnimation(enemy.getSpawner().getAnimationProvider()
                .get(
                        enemy.getOriginType(),
                        Animator.Types.WALKING,
                        enemy.getCurrentDirectionType()).obtain());
        Gdx.app.log("INFO", enemy + " is walking");
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        enemy.isAffected = false;
        enemy.speed = enemy.defaultSpeed;
    }

    @Override
    public void postManage(Enemy enemy) {

    }
}

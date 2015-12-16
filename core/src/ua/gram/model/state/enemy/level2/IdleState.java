package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;
import ua.gram.DDGame;
import ua.gram.model.Animator;
import ua.gram.model.actor.enemy.Enemy;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game) {
        super(game);
    }

    @Override
    public void preManage(Enemy enemy) {
        initAnimation(enemy, Animator.Types.IDLE);
        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getType());
    }

    @Override
    public void manage(Enemy enemy, float delta) {
        enemy.speed = 0;
    }

    @Override
    public void postManage(Enemy enemy) {
        enemy.speed = enemy.defaultSpeed;
    }
}

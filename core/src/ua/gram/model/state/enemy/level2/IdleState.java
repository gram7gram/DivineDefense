package ua.gram.model.state.enemy.level2;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.EnemyState getType() {
        return Types.EnemyState.IDLE;
    }

    @Override
    public void preManage(Enemy enemy) {
        initAnimation(enemy);
        Gdx.app.log("INFO", enemy + " state: " + enemy.getAnimator().getPrimaryType());
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

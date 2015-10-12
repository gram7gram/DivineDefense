package ua.gram.model.state.enemy.level1;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.state.AbstractState;
import ua.gram.model.state.State;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level1State extends AbstractState<Enemy> implements State {

    public Level1State(DDGame game, Enemy enemy) {
        super(game, enemy);
    }

    @Override
    public void manage() {

    }
}

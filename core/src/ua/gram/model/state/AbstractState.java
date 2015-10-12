package ua.gram.model.state;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbstractState<A extends Actor> {

    private DDGame game;
    private A actor;

    public AbstractState(DDGame game, A actor) {
        this.game = game;
        this.actor = actor;
    }

    public DDGame getGame() {
        return game;
    }

    public void setGame(DDGame game) {
        this.game = game;
    }

    public A getActor() {
        return actor;
    }

    public void setActor(A actor) {
        this.actor = actor;
    }
}

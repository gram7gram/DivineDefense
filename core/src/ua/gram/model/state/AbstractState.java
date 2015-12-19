package ua.gram.model.state;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AbstractState<A extends Actor> {

    private DDGame game;

    public AbstractState(DDGame game) {
        this.game = game;
    }

    public DDGame getGame() {
        return game;
    }

    public void setGame(DDGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

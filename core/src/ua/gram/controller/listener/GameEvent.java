package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class GameEvent implements EventListener {

    protected final DDGame game;

    public GameEvent(DDGame game) {
        this.game = game;
    }
}

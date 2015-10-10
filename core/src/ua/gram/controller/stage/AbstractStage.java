package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.controller.listener.DebugListener;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractStage extends Stage {

    protected final DDGame game;

    public AbstractStage(DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.game = game;
        this.setDebugAll(DDGame.DEBUG);
        if (DDGame.DEBUG) this.addListener(new DebugListener(this));
    }
}

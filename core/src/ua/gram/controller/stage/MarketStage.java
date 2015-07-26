package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class MarketStage extends Stage {

    private final DDGame game;

    public MarketStage(final DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.game = game;
    }
}

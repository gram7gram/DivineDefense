package ua.gram.view.screen;

import ua.gram.DDGame;
import ua.gram.controller.stage.MarketStage;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketScreen extends AbstractScreen {

    private final MarketStage stage;

    public MarketScreen(DDGame game) {
        super(game);
        stage = new MarketStage(game);
    }

    @Override
    public void render_ui(float delta) {

    }

    @Override
    public void render_other(float delta) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

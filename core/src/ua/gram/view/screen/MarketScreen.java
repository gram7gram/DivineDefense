package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import ua.gram.DDGame;
import ua.gram.controller.stage.MarketStage;
import ua.gram.model.prototype.market.MarketPrototype;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MarketScreen extends AbstractScreen {

    private final MarketStage stage;

    public MarketScreen(DDGame game, MarketPrototype prototype) {
        super(game);
        stage = new MarketStage(game, prototype);
        Gdx.app.log("INFO", "MarketSreen is OK");
    }

    @Override
    public void show() {
        Gdx.app.log("INFO", "Screen set to MainMenuScreen");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(45 / 255f, 25 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderOtherElements(float delta) {

    }


}

package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        Gdx.app.log("INFO", "MarketSreen is OK");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(60 / 255f, 165 / 255f, 40 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void render_other(float delta) {

    }


}

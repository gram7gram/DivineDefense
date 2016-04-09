package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.gram.DDGame;
import ua.gram.controller.stage.FactionStage;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram gram7gram@gmail.com
 */
public class FactionScreen extends AbstractScreen {

    private final FactionStage stage_ui;
    private final Sprite background;
    private final SpriteBatch batch;

    public FactionScreen(DDGame game) {
        super(game);
        stage_ui = new FactionStage(game);
        batch = game.getBatch();
        background = new Sprite(game.getResources().getRegisteredTexture(Resources.BACKGROUND_TEXTURE));
        Log.info("FractionScreen is OK");
    }

    @Override
    public void show() {
        Log.info("Screen set to FractionScreen");
        Gdx.input.setInputProcessor(stage_ui);
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
        background.setPosition(0, 0);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage_ui.act(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage_ui.draw();
    }

    @Override
    public void renderOtherElements(float delta) {
    }

    @Override
    public void dispose() {
        Log.warn("FractionScreen disposed!");
    }
}

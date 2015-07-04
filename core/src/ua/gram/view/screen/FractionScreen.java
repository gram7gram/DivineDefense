package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.stage.FractionStage;
import ua.gram.view.AbstractScreen;

/**
 * TODO On following launches display Unlock button for opposite fraction
 *
 * @author Gram
 */
public class FractionScreen extends AbstractScreen {

    private final FractionStage stage_ui;
    private final Sprite background;
    private final SpriteBatch batch;

    public FractionScreen(DDGame game) {
        super(game);
        stage_ui = new FractionStage(game);
        batch = game.getBatch();
        background = new Sprite(game.getResources().getTexture(Resources.BACKGROUND_TEXTURE));
        Gdx.app.log("INFO", "Screen set to FractionScreen");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage_ui);
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
        background.setPosition(0, 0);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage_ui.act(delta);
        batch.begin();
        background.draw(batch);
        batch.end();
        stage_ui.draw();
    }

    @Override
    public void render_other(float delta) {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.app.log("WARN", "FractionScreen disposed!");
    }
}

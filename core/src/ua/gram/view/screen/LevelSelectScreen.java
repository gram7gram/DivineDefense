package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.stage.LevelSelectStage;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectScreen extends AbstractScreen {

    private final LevelSelectStage stage_ui;
    private final Sprite background;

    public LevelSelectScreen(DDGame game) {
        super(game);
        stage_ui = new LevelSelectStage(game);
        background = new Sprite(game.getResources().getTexture(Resources.BACKGROUND_TEXTURE));
        Gdx.app.log("INFO", "Screen set to LevelSelectScreen");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage_ui);
        background.setSize(DDGame.WORLD_WIDTH, DDGame.WORLD_HEIGHT);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage_ui.getBatch().begin();
        background.draw(stage_ui.getBatch());
        stage_ui.getBatch().end();
        stage_ui.act(delta);
        stage_ui.draw();
    }

    @Override
    public void render_other(float delta) {

    }

    @Override
    public void hide() {
        Gdx.app.log("WARN", "Hiding LevelSelectScreen");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.app.log("WARN", "LevelSelectScreen disposed");
    }
}

package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.view.AbstractScreen;
import ua.gram.view.stage.LevelSelectStage;

/**
 * Created by Gram on 16/1.
 */
public class LevelSelectScreen extends AbstractScreen {

    private final Stage stage_ui;

    public LevelSelectScreen(DDGame game) {
        super(game);
        stage_ui = new LevelSelectStage(game);
        Gdx.app.log("INFO", "Screen set to LevelSelectScreen");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage_ui);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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

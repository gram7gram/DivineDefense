package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.controller.stage.ErrorStage;
import ua.gram.view.AbstractScreen;

import java.util.Arrays;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class ErrorScreen extends AbstractScreen {

    private final Stage stage_ui;

    public ErrorScreen(DDGame game, String error, Exception e) {
        super(game);
        stage_ui = new ErrorStage(game, error, e);
        Gdx.app.log("INFO", "ErrorScreen is OK");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage_ui);
        Gdx.app.log("INFO", "Screen set to ErrorScreen");
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(231 / 255f, 72 / 255f, 72 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage_ui.act(delta);
        stage_ui.draw();
    }

    @Override
    public void render_other(float delta) {

    }

    @Override
    public void hide() {
        Gdx.app.log("WARN", "Hiding ErrorScreen");
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.app.log("WARN", "ErrorScreen disposed");
    }
}

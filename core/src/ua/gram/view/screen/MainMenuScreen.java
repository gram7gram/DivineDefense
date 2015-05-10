package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;
import ua.gram.view.AbstractScreen;
import ua.gram.view.stage.MainMenuStage;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuScreen extends AbstractScreen {

    private final Stage stage_ui;

    public MainMenuScreen(DDGame game) {
        super(game);
        stage_ui = new MainMenuStage(game);
        Gdx.app.log("INFO", "Screen set to MainMenuScreen");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage_ui);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(60 / 255f, 165 / 255f, 40 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage_ui.act(delta);
        stage_ui.draw();
    }

    @Override
    public void render_other(float delta) {
    }

    @Override
    public void hide() {
        Gdx.app.log("WARN", "Hiding MainMenuScreen");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        Gdx.app.log("WARN", "MainMenuScreen disposed");
    }
}

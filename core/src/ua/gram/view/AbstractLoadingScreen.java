package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import ua.gram.DDGame;
import ua.gram.controller.stage.LoadingStage;
import ua.gram.view.screen.MainMenuScreen;

/**
 * LevelScreen handles resource loading invocation.
 * <p/>
 * In 'show' you specify resources to be loaded In 'doAction' you specify program logic that will be executed
 */
public abstract class AbstractLoadingScreen extends AbstractScreen {

    public LoadingStage stage_ui;
    public int progress;

    public AbstractLoadingScreen(DDGame game) {
        super(game);
    }

    @Override
    public void show() {
        stage_ui = new LoadingStage(game);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(.9f, .9f, .7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        progress = (byte) (game.getManager().getProgress() * 100);
        stage_ui.update(progress);
        stage_ui.act(delta);
        stage_ui.draw();
        if (game.getManager().update()) {
            doAction();
        }
    }

    @Override
    public void render_other(float delta) {
        //Does not contain other stuff
    }

    /**
     * Executes only if AssetManager finishes loading the resources,
     * required for the following Screen ancestor.
     */
    public void doAction() {
        stage_ui.update(progress);
        Gdx.app.log("INFO", "Loading " + progress + "%");
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void hide() {
        Gdx.app.log("WARN", "Hiding LoadingScreen");
        progress = 0;
    }

    @Override
    public void dispose() {
        Gdx.app.log("WARN", "LoadingScreen disposed");
    }
}

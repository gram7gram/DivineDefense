package ua.gram.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.LoadingStage;
import ua.gram.model.prototype.GamePrototype;
import ua.gram.view.screen.ErrorScreen;
import ua.gram.view.screen.MainMenuScreen;

/**
 * LevelScreen handles resource loading invocation.
 * In 'show' you specify resources to be loaded In 'doAction'
 * you specify program logic that will be executed
 */
public abstract class AbstractLoadingScreen extends AbstractScreen {

    public LoadingStage uiStage;
    public int progress;

    public AbstractLoadingScreen(DDGame game) {
        super(game);
    }

    public AbstractLoadingScreen(DDGame game, GamePrototype prototype) {
        super(game, prototype);
    }

    @Override
    public void show() {
        super.show();
        uiStage = new LoadingStage(game);
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(.9f, .9f, .7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        progress = (int) game.getAssetManager().getProgress() * 100;
        uiStage.update(progress);
        uiStage.act(delta);
        uiStage.draw();
        if (game.getAssetManager().update()) {
            try {
                doAction();
            } catch (Exception e) {
                game.setScreen(new ErrorScreen(game, "Error at loading", e));
            }
        }
    }

    @Override
    public void renderOtherElements(float delta) {

    }

    /**
     * Executes only if AssetManager finishes loading the resources,
     * required for the following Screen ancestor.
     */
    public void doAction() {
        uiStage.update(progress);
        Log.info("Loading " + progress + "%");
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void hide() {
        super.hide();
        progress = 0;
    }
}

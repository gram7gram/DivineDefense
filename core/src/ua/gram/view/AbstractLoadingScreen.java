package ua.gram.view;

import ua.gram.DDGame;
import ua.gram.controller.stage.LoadingStage;
import ua.gram.utils.Log;
import ua.gram.view.screen.ErrorScreen;
import ua.gram.view.screen.MainMenuScreen;

/**
 * LevelScreen handles resource loading invocation.
 * In 'show' you specify resources to be loaded.
 * In 'onLoad' you specify program logic that will be executed
 */
public abstract class AbstractLoadingScreen extends AbstractScreen {

    protected LoadingStage loadingStage;
    protected int progress;

    public AbstractLoadingScreen(DDGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        loadingStage = new LoadingStage(game);
    }

    @Override
    public void renderUiElements(float delta) {
        progress = (int) game.getAssetManager().getProgress() * 100;
        loadingStage.update(progress);
        loadingStage.act(delta);
        loadingStage.draw();
        if (game.getAssetManager().update()) {
            try {
                onLoad();
            } catch (Exception e) {
                game.setScreen(new ErrorScreen(game, "Error at loading", e));
            }
        }
    }

    @Override
    public void renderOtherElements(float delta) {

    }

    public void onLoad() {
        loadingStage.update(progress);
        Log.info("Loading " + progress + "%");
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void hide() {
        super.hide();
        progress = 0;
    }
}

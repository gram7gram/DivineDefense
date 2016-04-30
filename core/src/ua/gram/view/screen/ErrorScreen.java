package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ua.gram.DDGame;
import ua.gram.controller.stage.ErrorStage;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class ErrorScreen extends AbstractScreen {

    private final Stage uiStage;

    public ErrorScreen(DDGame game, String error, Exception e) {
        super(game);
        uiStage = new ErrorStage(game, error, e);
        Log.info("ErrorScreen is OK");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);
        Log.info("Screen set to ErrorScreen");
    }

    @Override
    public void renderAlways(float delta) {
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void renderNoPause(float delta) {

    }

    @Override
    public void dispose() {
        Log.warn("ErrorScreen disposed");
    }
}

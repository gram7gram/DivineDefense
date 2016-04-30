package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.controller.stage.MainMenuStage;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuScreen extends AbstractScreen {

    private final MainMenuStage uiStage;

    public MainMenuScreen(DDGame game) {
        super(game);
        uiStage = new MainMenuStage(game);
        Log.info("MainMenuScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void renderAlways(float delta) {
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void renderNoPause(float delta) {
    }

}

package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.controller.stage.FactionStage;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram gram7gram@gmail.com
 */
public class FactionScreen extends AbstractScreen {

    private final FactionStage stage;

    public FactionScreen(DDGame game) {
        super(game);
        stage = new FactionStage(game);
        Log.info("FractionScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderUiElements(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderOtherElements(float delta) {
    }
}

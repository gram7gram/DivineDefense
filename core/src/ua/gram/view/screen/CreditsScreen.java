package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.controller.stage.CreditsStage;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CreditsScreen extends AbstractScreen {

    private final CreditsStage stage;

    public CreditsScreen(DDGame game) {
        super(game);
        stage = new CreditsStage(game);
        Log.info("CreditsScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void renderAlways(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void renderNoPause(float delta) {

    }
}

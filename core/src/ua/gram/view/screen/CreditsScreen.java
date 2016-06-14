package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;

import ua.gram.DDGame;
import ua.gram.controller.listener.CameraControlsListener;
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
        InputMultiplexer multiplexer = new InputMultiplexer();
        InputAdapter inputAdapter = new CameraControlsListener(game);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(inputAdapter);
        Gdx.input.setInputProcessor(multiplexer);
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

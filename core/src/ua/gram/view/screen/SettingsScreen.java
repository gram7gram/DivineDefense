package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;

import ua.gram.DDGame;
import ua.gram.controller.stage.SettingsStage;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SettingsScreen extends AbstractScreen {

    private SettingsStage stage;

    public SettingsScreen(DDGame game) {
        super(game);
        Log.info("LevelSelectScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        stage = new SettingsStage(game);
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

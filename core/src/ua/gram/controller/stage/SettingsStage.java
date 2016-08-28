package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.model.window.SettingsWindow;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class SettingsStage extends AbstractStage {

    public SettingsStage(DDGame game) {
        super(game);
        Actor window = new SettingsWindow(game);
        window.setVisible(true);
        addActor(window);
        Log.info("SettingsStage is OK");
    }
}

package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.model.window.CreditsWindow;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CreditsStage extends AbstractStage {

    private final WindowPrototype prototype;

    public CreditsStage(DDGame game) {
        super(game);
        prototype = game.getPrototype().ui.getWindow("credits");
        Actor window = new CreditsWindow(game, getSkin(), prototype);
        addActor(window);

        Log.info("CreditsStage is OK");
    }

    public WindowPrototype getPrototype() {
        return prototype;
    }
}

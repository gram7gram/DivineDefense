package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.model.window.LevelSelectWindow;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectStage extends AbstractStage {

    public LevelSelectStage(DDGame game, LevelPrototype[] prototypes) {
        super(game);
        Actor window = new LevelSelectWindow(game, prototypes);
        window.setVisible(true);
        addActor(window);
        Log.info("LevelSelectStage is OK");
    }
}

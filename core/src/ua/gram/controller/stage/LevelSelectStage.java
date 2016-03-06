package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.group.LevelSelectWindow;
import ua.gram.model.prototype.GamePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectStage extends AbstractStage {

    public LevelSelectStage(DDGame game, GamePrototype prototype) {
        super(game);
        Actor window = new LevelSelectWindow(game, prototype.levelConfig.levels);
        window.setVisible(true);
        addActor(window);
        Log.info("LevelSelectStage is OK");
    }

}

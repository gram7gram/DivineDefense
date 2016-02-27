package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import ua.gram.DDGame;
import ua.gram.model.group.MainMenuWindow;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuStage extends AbstractStage {

    public MainMenuStage(DDGame game) {
        super(game);
        Skin skin = game.getResources().getSkin();
        Window window = new MainMenuWindow(game, skin);
        addActor(window);
    }

}

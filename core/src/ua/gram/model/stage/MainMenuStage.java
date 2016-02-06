package ua.gram.model.stage;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.model.group.MainMenuGroup;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class MainMenuStage extends AbstractStage {

    public MainMenuStage(DDGame game) {
        super(game);
        Skin skin = game.getResources().getSkin();
        Group group = new MainMenuGroup(game, skin);
        addActor(group);
    }

}

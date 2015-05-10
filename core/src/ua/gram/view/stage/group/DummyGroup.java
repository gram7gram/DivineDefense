package ua.gram.view.stage.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DummyGroup extends Group {

    public DummyGroup(Stage stage) {
        for (int i = 0; i < DDGame.WORLD_HEIGHT / DDGame.TILEHEIGHT - 1; i++) {
            Actor actor = new Actor();
            actor.setSize(0, 0);
            actor.setBounds(0, 0, 0, 0);
            actor.setVisible(true);
            stage.addActor(actor);
        }
        Gdx.app.log("INFO", "Added "
                + (DDGame.WORLD_HEIGHT / DDGame.TILEHEIGHT - 1)
                + " dummy actors");
        Gdx.app.log("INFO", "Max zIndex:  "
                + stage.getActors().get(stage.getActors().size - 1).getZIndex());
    }
}

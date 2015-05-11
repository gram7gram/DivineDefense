package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ua.gram.DDGame;

/**
 * Created by Gram on 15/1.
 */
public class LoadingStage extends Stage {

    public Label label;

    public LoadingStage(DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.setDebugAll(DDGame.DEBUG);
        label = new Label("Loading: 0%", game.getSkin(), "action64black");
        label.setPosition(
                DDGame.WORLD_WIDTH / 2 - label.getWidth() / 2,
                DDGame.WORLD_HEIGHT / 2 - label.getHeight() / 2
        );
        this.addActor(label);
    }

    public void update(byte progress) {
        label.setText("Loading: " + progress + "%");
    }
}

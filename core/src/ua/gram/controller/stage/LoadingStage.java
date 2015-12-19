package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ua.gram.DDGame;

/**
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class LoadingStage extends AbstractStage {

    public Label label;

    public LoadingStage(DDGame game) {
        super(game);
        label = new Label("Loading: 0%", game.getResources().getSkin(), "header1black");
        label.setPosition(
                DDGame.WORLD_WIDTH / 2 - label.getWidth() / 2,
                DDGame.WORLD_HEIGHT / 2 - label.getHeight() / 2
        );
        this.addActor(label);
    }

    public void update(int progress) {
        label.setText("Loading: " + progress + "%");
    }
}

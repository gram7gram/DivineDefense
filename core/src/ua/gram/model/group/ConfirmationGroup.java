package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ConfirmationGroup extends Group {

    public ConfirmationGroup(final DDGame game, ClickListener action0, ClickListener action1, ClickListener action2, String action1Text, String action2Text, String message) {

        Skin skin = game.getResources().getSkin();

        WindowGroup window = new WindowGroup(skin, "default", this, action0);
        window.setVisible(true);

        Button cont = new TextButton(action1Text, skin, "diablo-green");
        cont.setSize(180, 60);
        cont.setTouchable(Touchable.enabled);
        cont.setVisible(true);
        cont.setPosition(
                (DDGame.WORLD_WIDTH - cont.getWidth()) / 2,
                window.getWindow().getY() + 20);
        cont.addListener(action1);

        if (action2 != null) {
            Button rest = new TextButton(action2Text, skin, "green-button-smaller");
            rest.setSize(110, 45);
            rest.setTouchable(Touchable.enabled);
            rest.setVisible(true);
            rest.addListener(action2);
            rest.setPosition(
                    DDGame.WORLD_WIDTH / 2 - (DDGame.WORLD_WIDTH / 2 - window.getWindow().getX()) / 2 - rest.getWidth() + 15,
                    window.getWindow().getY() + 20 + (cont.getHeight() - rest.getHeight()) / 2);
            window.addActor(rest);
        }

        Label messageLabel = new Label(message, skin, "header1white");
        messageLabel.setPosition((DDGame.WORLD_WIDTH - messageLabel.getWidth()) / 2f, DDGame.WORLD_HEIGHT / 2f - 10);
        messageLabel.setVisible(true);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        window.addActor(messageLabel);
        window.addActor(cont);
        this.addActor(window);

        this.setVisible(false);
    }

}

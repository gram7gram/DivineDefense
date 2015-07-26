package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ua.gram.DDGame;
import ua.gram.model.window.ConfirmationWindow;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ConfirmationGroup extends Group {

    public ConfirmationGroup(final DDGame game, ClickListener abortListener, ClickListener confirmListener, String confirmButtonText, String message) {

        Window window = new ConfirmationWindow(game.getResources().getSkin(), "default");
        window.setVisible(true);

        final Button closeBut = new Button(game.getResources().getSkin(), "close-button");
        closeBut.addListener(abortListener);
        closeBut.setVisible(true);
        closeBut.setSize(80, 80);
        closeBut.setPosition(
                (DDGame.WORLD_WIDTH + window.getWidth() - closeBut.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT + window.getHeight() - closeBut.getHeight()) / 2f
        );
        closeBut.toFront();

        Button confirmBut = new TextButton(confirmButtonText, game.getResources().getSkin(), "pretty-button");
        confirmBut.setSize(200, 80);
        confirmBut.setPosition(
                (DDGame.WORLD_WIDTH - confirmBut.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT - confirmBut.getHeight()) / 2f - 60
        );
        confirmBut.setVisible(true);
        confirmBut.addListener(confirmListener);

        Label messageLabel = new Label(message, game.getResources().getSkin(), "header1black");
        messageLabel.setPosition((DDGame.WORLD_WIDTH - messageLabel.getWidth()) / 2f, DDGame.WORLD_HEIGHT / 2f - 10);
        messageLabel.setVisible(true);
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        this.addActor(window);
        this.addActor(closeBut);
        this.addActor(messageLabel);
        this.addActor(confirmBut);

        this.setVisible(false);
    }

}

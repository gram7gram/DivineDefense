package ua.gram.view.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ua.gram.DDGame;
import ua.gram.model.Player;
import ua.gram.view.screen.MainMenuScreen;
import ua.gram.view.window.ConfirmationWindow;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ConfirmationGroup extends Group {

    private Window window;
    private Button closeBut;
    private Label messageLabel;
    private Button confirmBut;

    public ConfirmationGroup(final DDGame game, ClickListener abortListener, String confirmButtonText, String message) {

        window = new ConfirmationWindow(game.getSkin());
        window.setVisible(true);

        closeBut = new Button(game.getSkin(), "close-button");
        closeBut.addListener(abortListener);
        closeBut.setVisible(true);
        closeBut.setSize(80, 80);
        closeBut.setPosition(
                (DDGame.WORLD_WIDTH + window.getWidth() - closeBut.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT + window.getHeight() - closeBut.getHeight()) / 2f
        );
        closeBut.toFront();

        confirmBut = new TextButton(confirmButtonText, game.getSkin(), "green-button");
        confirmBut.setSize(200, 80);
        confirmBut.setPosition(
                (DDGame.WORLD_WIDTH - confirmBut.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT - confirmBut.getHeight()) / 2f - 60
        );
        confirmBut.setVisible(true);
        confirmBut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("INFO", "Player Fraction set to " + Player.PLAYER_FRACTION);
                Gdx.app.log("INFO", "System Fraction set to " + Player.SYSTEM_FRACTION);
                closeBut.toFront();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        messageLabel = new Label(message, game.getSkin(), "archery64black");
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

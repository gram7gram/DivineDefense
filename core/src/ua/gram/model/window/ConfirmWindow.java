package ua.gram.model.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ConfirmWindow extends Window {

    public ConfirmWindow(String title, Skin skin, ClickListener onConfirm, ClickListener onClose) {
        super("", skin, "window-dark-gold");

        ClickListener defaultListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setVisible(false);
                remove();
            }
        };

        setSize(400, 250);
        setPosition((DDGame.WORLD_WIDTH - getWidth()) / 2, (DDGame.WORLD_HEIGHT - getHeight()) / 2);
        Button continueButton = new Button(skin, "ok-big");
        continueButton.addListener(onConfirm);

        Button cancelButton = null;
        if (onClose != null) {
            cancelButton = new Button(skin, "close-big");
            cancelButton.addListener(defaultListener);
            cancelButton.addListener(onClose);
        }

        Label messageLabel = new Label(title, skin, "header2altwhite");
        messageLabel.setWrap(true);
        messageLabel.setAlignment(Align.center);

        add(messageLabel).pad(10).colspan(2).expand().row();
        add(cancelButton).padRight(10).size(80).right().expand();
        add(continueButton).padLeft(10).size(80).left().expand();

        setMovable(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDebug(DDGame.DEBUG, true);
    }
}

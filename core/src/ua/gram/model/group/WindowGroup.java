package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.window.EmptyWindow;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class WindowGroup extends Group {

    private final Window window;

    public WindowGroup(Skin skin, String style, final Group parent, ClickListener action) {

        window = new EmptyWindow(skin, style);
        Button close = new Button(skin, "close-button");

        close.addListener(action != null ? action : new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                parent.setVisible(false);
            }
        });
        close.setVisible(true);
        close.setSize(60, 60);
        close.setPosition(
                (DDGame.WORLD_WIDTH + window.getWidth() - close.getWidth()) / 2f,
                (DDGame.WORLD_HEIGHT + window.getHeight() - close.getHeight()) / 2f
        );
        this.addActor(window);
        this.addActor(close);
        close.toFront();
        this.setDebug(DDGame.DEBUG);
    }

    public Window getWindow() {
        return window;
    }
}

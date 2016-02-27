package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.model.window.AbstractWindow;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectWindow extends AbstractWindow {

    public LevelSelectWindow(final DDGame game, LevelPrototype[] prototypes) {
        super("SELECT LEVEL", game.getResources().getSkin());

        Table nested = new Table();
        nested.setVisible(true);
        for (LevelPrototype prototype : prototypes) {
            LevelTile tile1 = new LevelTile(game, prototype);
            tile1.setVisible(true);
            nested.add(tile1).pad(10)
                    .width(DDGame.DEFAULT_BUTTON_HEIGHT * 2.5f)
                    .height(DDGame.DEFAULT_BUTTON_HEIGHT * 2.5f);
        }

        Button back = new Button(game.getResources().getSkin(), "back-button-right");
        back.setVisible(true);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreen(new MainMenuScreen(game));
            }
        });

        ScrollPane scroll = new ScrollPane(nested);
        scroll.setScrollingDisabled(false, true);
        scroll.setVisible(true);

        add().expandX().height(DDGame.DEFAULT_BUTTON_HEIGHT);
        add(back).height(DDGame.DEFAULT_BUTTON_HEIGHT)
                .width(DDGame.DEFAULT_BUTTON_HEIGHT).row();
        add(scroll).colspan(2).expand();
    }
}

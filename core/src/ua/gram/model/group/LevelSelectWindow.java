package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.factory.LevelTileFactory;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.model.window.WindowGroup;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectWindow extends WindowGroup {

    public LevelSelectWindow(final DDGame game, LevelPrototype[] prototypes) {
        super(game.getResources().getSkin());
        Skin skin = game.getResources().getSkin();

        Table nested = new Table();
        nested.setVisible(true);
        for (LevelPrototype prototype : prototypes) {
            Actor tile1 = LevelTileFactory.create(game, prototype);
            tile1.setVisible(true);
            nested.add(tile1).pad(10)
                    .width(DDGame.DEFAULT_BUTTON_HEIGHT * 2.5f)
                    .height(DDGame.DEFAULT_BUTTON_HEIGHT * 2.5f);
        }

        Button back = new Button(skin, "right-small");
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

        setActionRight(back);
        setTitle(new Label("SELECT LEVEL", skin, "header2altwhite"));
        getContent().add(scroll).expand();
    }
}

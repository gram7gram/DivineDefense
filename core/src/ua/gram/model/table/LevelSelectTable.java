package ua.gram.model.table;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.group.LevelTile;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.screen.MainMenuScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelSelectTable extends Table {

    public LevelSelectTable(final DDGame game, LevelPrototype[] prototypes) {
        Table nested = new Table();
        nested.setVisible(true);
        for (LevelPrototype prototype : prototypes) {
            LevelTile tile1 = new LevelTile(game, prototype);
            tile1.setVisible(true);
            nested.add(tile1).width(200).height(200).pad(10);
        }

        Button back = new Button(game.getResources().getSkin(), "back-button");
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

        this.setDebug(DDGame.DEBUG);
        this.setFillParent(true);
        this.add(back).height(80).width(80);
        this.add().expandX().height(80).row();
        this.add(scroll).colspan(2).expand();
    }
}

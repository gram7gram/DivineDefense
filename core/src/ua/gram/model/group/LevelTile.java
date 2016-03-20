package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTile extends Table {

    public LevelTile(final DDGame game, final LevelPrototype prototype) {
        super(game.getResources().getSkin());
        Skin skin = game.getResources().getSkin();

        final byte lvl = prototype.level;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getPlayer().setLevel(lvl);
                Log.info("Level " + game.getPlayer().getLevel() + " is selected");
                game.setScreen(new LevelLoadingScreen(game, prototype));
            }
        });

        setTouchable(Touchable.enabled);

        setBackground(skin.getDrawable(prototype.preview));

        Log.info("Level tile " + lvl + " is OK");
    }
}

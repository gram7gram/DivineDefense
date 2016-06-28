package ua.gram.model.group;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import ua.gram.DDGame;
import ua.gram.model.prototype.level.LevelPrototype;
import ua.gram.utils.Log;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTile extends Table {

    public LevelTile(final DDGame game, final LevelPrototype prototype) {
        super(game.getResources().getSkin());

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

        String previewName = prototype.preview.name 
        + "@" + prototype.preview.size 
        + "." + prototype.preview.format;

        Texture background = game.getResources()
                .getRegisteredTexture(previewName);
        SpriteDrawable drawable = new SpriteDrawable(new Sprite(background));

        setBackground(drawable);

        Log.info("Level tile " + lvl + " is OK");
    }
}

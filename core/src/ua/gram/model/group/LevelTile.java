package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.model.Level;
import ua.gram.model.prototype.LevelPrototype;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTile extends Table {

    private Sprite lock_level;

    public LevelTile(final DDGame game, final LevelPrototype prototype) {
        super(game.getResources().getSkin());
        Skin skin = game.getResources().getSkin();
        byte stars = prototype.ranking;
        final byte lvl = prototype.level;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getPlayer().setLevel(lvl);
                Gdx.app.log("INFO", "Level " + game.getPlayer().getLevel() + " is selected");
                Level level = new Level(game, prototype);
                game.setScreen(new LevelLoadingScreen(game, level));
            }
        });
        if (!prototype.locked) {
            this.setTouchable(Touchable.enabled);
        } else {
            this.setTouchable(Touchable.disabled);
            lock_level = new Sprite(skin.getRegion("level_lock"));
        }
        this.setBackground(skin.getDrawable(prototype.preview));
        Gdx.app.log("INFO", "Level tile " + lvl + " is OK");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (lock_level != null) batch.draw(lock_level, this.getX(), this.getY());
    }
}

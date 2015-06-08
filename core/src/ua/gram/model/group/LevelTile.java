package ua.gram.model.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.gram.DDGame;
import ua.gram.view.screen.LevelLoadingScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelTile extends Group {

    private final Button preview;
    private final int stars;
    private Sprite star_disabled;
    private Sprite star_enabled;
    private Sprite lock_level;

    /**
     * @param game  - access the resources
     * @param lvl   level to load
     * @param stars Rating for current level: 1, 2 or 3 stars, 0 if level is not cleared
     * @param lock  if the tile is locked (disables touch events and draws a lock image over it)
     */
    public LevelTile(final DDGame game, final int lvl, int stars, boolean lock) {
        if (stars > 3 || stars < 0) throw new IllegalArgumentException("Rating can be only from 0 to 3 stars! Yet, you want " + stars);
        this.stars = stars;
        Skin skin = game.getResources().getSkin();
        if (stars > 0) star_enabled = new Sprite(skin.getRegion("star_small_enabled"));
        if (stars < 3) star_disabled = new Sprite(skin.getRegion("star_small_disabled"));
        int tile_width = 210;
        int gap = 20;
        preview = new Button(skin, "level" + lvl + "-preview");
        preview.setSize(tile_width, tile_width);
        preview.setPosition(
                lvl == 1 ? gap : gap + (lvl / 2) * (tile_width + gap),
                lvl % 2 == 0 ? gap : tile_width + gap * 2
        );
        if (!lock) {
            this.setTouchable(Touchable.enabled);
            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    game.getPlayer().setLevel(lvl);
                    Gdx.app.log("INFO", "Level " + game.getPlayer().getLevel() + " is selected");
                    game.setScreen(new LevelLoadingScreen(game, lvl));
                }
            });
        } else {
            this.setTouchable(Touchable.disabled);
            lock_level = new Sprite(skin.getRegion("level_lock"));
        }
        this.addActor(preview);
        Gdx.app.log("INFO", "Chooser for level " + lvl + " is " + (lock ? "disabled" : "enabled"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        int gap = 10;
        int star_width = 50;
        switch (stars) {
            case 3: {
                batch.draw(star_enabled, preview.getX() + 20, preview.getY() + 13, star_width, star_width);
                batch.draw(star_enabled, preview.getX() + 20 + star_enabled.getWidth() + gap, preview.getY() + 13, star_width, star_width);
                batch.draw(star_enabled, preview.getX() + 20 + 2 * (star_enabled.getWidth() + gap), preview.getY() + 13, star_width, star_width);
                break;
            }
            case 2: {
                batch.draw(star_enabled, preview.getX() + 20, preview.getY() + 13, star_width, star_width);
                batch.draw(star_enabled, preview.getX() + 20 + star_enabled.getWidth() + gap, preview.getY() + 13, star_width, star_width);
                batch.draw(star_disabled, preview.getX() + 20 + 2 * (star_enabled.getWidth() + gap), preview.getY() + 13, star_width, star_width);
                break;
            }
            case 1: {
                batch.draw(star_enabled, preview.getX() + 20, preview.getY() + 13, star_width, star_width);
                batch.draw(star_disabled, preview.getX() + 20 + star_enabled.getWidth() + gap, preview.getY() + 13, star_width, star_width);
                batch.draw(star_disabled, preview.getX() + 20 + 2 * (star_enabled.getWidth() + gap), preview.getY() + 13, star_width, star_width);
                break;
            }
            case 0: {
                batch.draw(star_disabled, preview.getX() + 20, preview.getY() + 13, star_width, star_width);
                batch.draw(star_disabled, preview.getX() + 20 + star_disabled.getWidth() + gap, preview.getY() + 13, star_width, star_width);
                batch.draw(star_disabled, preview.getX() + 20 + 2 * (star_disabled.getWidth() + gap), preview.getY() + 13, star_width, star_width);
                break;
            }
        }
        if (lock_level != null) batch.draw(lock_level, preview.getX(), preview.getY());
    }
}

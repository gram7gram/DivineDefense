package ua.gram.model.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.model.prototype.level.LevelPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LockedLevelTile extends LevelTile {

    private final Sprite lockIcon;

    public LockedLevelTile(DDGame game, LevelPrototype prototype) {
        super(game, prototype);
        Skin skin = game.getResources().getSkin();
        lockIcon = new Sprite(skin.getRegion("ui/button/dark/60/button-lock"));
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(lockIcon,
                getX() + (getWidth() - lockIcon.getWidth()) / 2f,
                getY() + (getHeight() - lockIcon.getHeight()) / 2f,
                lockIcon.getWidth(), lockIcon.getHeight());
    }
}

package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.view.stage.group.TowerGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Range extends Actor {

    private TowerGroup group;
    private final Sprite range;
    private float radius;

    public Range(Resources resources) {
        range = new Sprite(resources.getTexture(Resources.RANGE_TEXTURE));
        this.setTouchable(Touchable.disabled);
        this.setDebug(DDGame.DEBUG);
        this.setVisible(true);
    }

    public void setGroup(TowerGroup group) {
        this.group = group;
        this.radius = group.getTower().getRange();
        Gdx.app.log("INFO", "Range for " + group.getTower() + " is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            range.setPosition(
                    group.getTower().getX() + group.getTower().getWidth() / 2f - radius * DDGame.TILEHEIGHT,
                    group.getTower().getY() + 15 - radius * DDGame.TILEHEIGHT
            );
            range.setSize(
                    radius * 2 * DDGame.TILEHEIGHT,
                    radius * 2 * DDGame.TILEHEIGHT
            );
            range.setBounds(
                    range.getX(), range.getY(),
                    range.getWidth(), range.getHeight()
            );
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (group.getTower().isActive && group.getControls().isVisible())
            range.draw(batch);

    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

package ua.gram.model.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import ua.gram.DDGame;
import ua.gram.controller.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Range extends Actor {

    private final Sprite range;
    private Tower tower;
    private float radius;

    public Range(Resources resources) {
        range = new Sprite(resources.getTexture(Resources.RANGE_TEXTURE));
        this.setTouchable(Touchable.disabled);
        this.setDebug(DDGame.DEBUG);
    }

    public void setTower(Tower tower) {
        this.tower = tower;
        this.radius = tower.getRange();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && tower != null) {
            this.toBack();
            range.setSize(
                    radius * 2 * DDGame.TILE_HEIGHT,
                    radius * 2 * DDGame.TILE_HEIGHT
            );
            range.setBounds(
                    range.getX(), range.getY(),
                    range.getWidth(), range.getHeight()
            );
            range.setPosition(
                    tower.getX() + tower.getWidth() / 2f - radius * DDGame.TILE_HEIGHT,
                    tower.getY() + 15 - radius * DDGame.TILE_HEIGHT
            );
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && tower != null && tower.isActive)
            range.draw(batch);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

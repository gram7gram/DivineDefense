package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.actor.Weapon;


/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Laser extends Weapon {

    private final Color color_back;
    private final Color color_over;
    private final Sprite start_back;
    private final Sprite start_over;
    private final Sprite middle_back;
    private final Sprite middle_over;
    private final Sprite end_back;
    private final Sprite end_over;

    public Laser(Resources resources, Color color) {
        this(resources, color, Vector2.Zero, Vector2.Zero);
    }

    public Laser(Resources resources, Color color, Vector2 towerPos) {
        this(resources, color, towerPos, Vector2.Zero);
    }

    public Laser(Resources resources, Color color, Vector2 from, Vector2 to) {
        super(from, to);
        this.color_back = color;
        this.color_over = Color.WHITE;
        this.start_back = new Sprite(resources.getTexture(Resources.LASER_START_BACK));
        this.start_over = new Sprite(resources.getTexture(Resources.LASER_START_OVER));
        this.middle_back = new Sprite(resources.getTexture(Resources.LASER_MIDDLE_BACK));
        this.middle_over = new Sprite(resources.getTexture(Resources.LASER_MIDDLE_OVER));
        this.end_back = new Sprite(resources.getTexture(Resources.LASER_END_BACK));
        this.end_over = new Sprite(resources.getTexture(Resources.LASER_END_OVER));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE) {
            batch.end();// actual drawing is done on end(); if we do not end, we contaminate previous rendering.
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            batch.begin();

            start_back.setColor(color_back);
            start_over.setColor(color_over);
            middle_back.setColor(color_back);
            middle_over.setColor(color_over);
            end_back.setColor(color_back);
            end_over.setColor(color_over);

            middle_back.setSize(middle_back.getWidth(), position1.dst(position2));
            middle_over.setSize(middle_back.getWidth(), position1.dst(position2));

            start_back.setPosition(position1.x, position1.y);
            start_over.setPosition(position1.x, position1.y);
            middle_back.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight());
            middle_over.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight());
            end_back.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight() + middle_back.getHeight());
            end_over.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight() + middle_back.getHeight());

            start_back.setOrigin(start_back.getWidth() / 2, 0);
            start_over.setOrigin(start_back.getWidth() / 2, 0);
            middle_back.setOrigin(middle_back.getWidth() / 2, -start_back.getHeight());
            middle_over.setOrigin(middle_over.getWidth() / 2, -start_back.getHeight());
            end_back.setOrigin(middle_back.getWidth() / 2, -start_back.getHeight() - middle_back.getHeight());
            end_over.setOrigin(middle_over.getWidth() / 2, -start_back.getHeight() - middle_over.getHeight());

            float degrees = position1.angle(position2);
            start_back.setRotation(degrees);
            start_over.setRotation(degrees);
            middle_back.setRotation(degrees);
            middle_over.setRotation(degrees);
            end_back.setRotation(degrees);
            end_over.setRotation(degrees);

            start_back.draw(batch);
            start_over.draw(batch);
            middle_back.draw(batch);
            middle_over.draw(batch);
            end_back.draw(batch);
            end_over.draw(batch);

            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }
}

package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.prototype.weapon.LaserWeaponPrototype;

/**
 * Weapon for TowerSecondary
 *
 * @author Gram <gram7gram@gmail.com>
 */
public final class LaserWeapon extends Weapon {

    private final Color color_over;
    private final Sprite start_back;
    private final Sprite start_over;
    private final Sprite middle_back;
    private final Sprite middle_over;
    private final Sprite end_back;
    private final Sprite end_over;
    private Color color_back;

    public LaserWeapon(Resources resources, LaserWeaponPrototype prototype) {
        super(resources, prototype);
        this.color_back = prototype.colorBack;
        this.color_over = prototype.colorOver;
        this.start_back = new Sprite(resources.getRegisteredTexture(prototype.startBack));
        this.start_over = new Sprite(resources.getRegisteredTexture(prototype.startOver));
        this.middle_back = new Sprite(resources.getRegisteredTexture(prototype.middleBack));
        this.middle_over = new Sprite(resources.getRegisteredTexture(prototype.middleOver));
        this.end_back = new Sprite(resources.getRegisteredTexture(prototype.endBack));
        this.end_over = new Sprite(resources.getRegisteredTexture(prototype.endOver));
    }

    @Override
    public void update(float delta) {
        if (targetGroup == null || towerGroup == null) return;

        Vector2 towerPos = new Vector2(towerGroup.getOriginX(), towerGroup.getOriginY());
        Vector2 targetPos = new Vector2(targetGroup.getOriginX(), targetGroup.getOriginY());

        float dist = towerPos.dst(targetPos);
        start_back.setSize(start_back.getWidth(), start_back.getHeight());
        start_over.setSize(start_over.getWidth(), start_over.getHeight());
        middle_back.setSize(middle_back.getWidth(), dist - targetGroup.getHeight() / 3f);
        middle_over.setSize(middle_over.getWidth(), dist - targetGroup.getHeight() / 3f);
        end_back.setSize(end_back.getWidth(), end_back.getHeight());
        end_over.setSize(end_over.getWidth(), end_over.getHeight());

        start_back.setOrigin(start_back.getWidth() / 2f, start_back.getHeight() / 2f);
        start_over.setOrigin(start_back.getWidth() / 2f, start_back.getHeight() / 2f);
        middle_back.setOrigin(middle_back.getWidth() / 2f, -start_back.getHeight() / 2f);
        middle_over.setOrigin(middle_over.getWidth() / 2f, -start_back.getHeight() / 2f);
        end_back.setOrigin(middle_back.getWidth() / 2f, -start_back.getHeight() / 2f - middle_back.getHeight());
        end_over.setOrigin(middle_over.getWidth() / 2f, -start_back.getHeight() / 2f - middle_over.getHeight());

        start_back.setPosition(towerGroup.getOriginX() - start_back.getWidth() / 2f, towerGroup.getOriginY() - start_back.getHeight() / 2f);
        start_over.setPosition(towerGroup.getOriginX() - start_back.getWidth() / 2f, towerGroup.getOriginY() - start_back.getHeight() / 2f);
        middle_back.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight());
        middle_over.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight());
        end_back.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight() + middle_back.getHeight());
        end_over.setPosition(start_back.getX(), start_back.getY() + start_back.getHeight() + middle_back.getHeight());

        float degrees = towerPos.sub(targetPos).angle() + 90;
        start_back.setRotation(degrees);
        start_over.setRotation(degrees);
        middle_back.setRotation(degrees);
        middle_over.setRotation(degrees);
        end_back.setRotation(degrees);
        end_over.setRotation(degrees);

        start_back.setColor(color_back);
        start_over.setColor(color_over);
        middle_back.setColor(color_back);
        middle_over.setColor(color_over);
        end_back.setColor(color_back);
        end_over.setColor(color_over);

        this.setSize(
                start_back.getWidth(),
                start_back.getHeight() + middle_back.getHeight() + end_back.getWidth()
        );
        this.setOrigin(start_back.getWidth() / 2f, start_back.getHeight() / 2f);
        this.setPosition(start_back.getX(), start_back.getY());
        this.setRotation(degrees);
        this.setVisible(true);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (DDGame.PAUSE || targetGroup == null || towerGroup == null) return;

        //NOTE Actual drawing is done on end(); if we do not end, we contaminate previous rendering.
        batch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.begin();

        start_back.draw(batch);
        start_over.draw(batch);
        middle_back.draw(batch);
        middle_over.draw(batch);
        end_back.draw(batch);
        end_over.draw(batch);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void reset() {
        super.reset();
        this.setSize(0, 0);
        this.setPosition(towerGroup.getOriginX(), towerGroup.getOriginY());
        this.setRotation(0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public LaserWeaponPrototype getPrototype() {
        return (LaserWeaponPrototype) prototype;
    }

    public void setBackColor(Color color) {
        color_back = DDGame.DEBUG ? Color.BLUE : color;
    }

}
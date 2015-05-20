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
public class StatusIcon extends Actor {

    private final Sprite aim;
    private Enemy enemy;

    public StatusIcon(Resources resources) {
        aim = new Sprite(resources.getTexture(Resources.AIM_ICON));
        this.setVisible(false);
        this.setTouchable(Touchable.disabled);
        this.setSize(aim.getWidth(), aim.getHeight());
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.setDebug(DDGame.DEBUG);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && enemy != null) {
            this.setPosition(
                    enemy.getX() + (enemy.getWidth() - this.getWidth()) / 2f,
                    enemy.getY() + enemy.getHeight() + 2 + 3 + 3
            );
            aim.setPosition(this.getX(), this.getY());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && enemy != null) {
            aim.draw(batch);
        }
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}

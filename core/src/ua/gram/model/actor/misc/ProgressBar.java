package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.model.actor.tower.Tower;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ProgressBar extends Actor {

    private final Tower tower;
    private final NinePatchDrawable progressBar;
    private final float maxWidth;

    public ProgressBar(Skin skin, Tower tower) {
        this.tower = tower;
        progressBar = new NinePatchDrawable(
                new NinePatch(skin.getRegion("health-bar-100"), 0, 0, 0, 0));
        this.setTouchable(Touchable.disabled);
        this.setSize(tower.getWidth(), 3);
        maxWidth = this.getWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDebug(DDGame.DEBUG);
        if (!DDGame.PAUSE) {
            boolean isVisible = tower.buildCount <= tower.getPrototype().buildDelay;
            this.setVisible(isVisible);
            this.setPosition(tower.getX(), tower.getY() - this.getHeight() - 2);
            if (!isVisible) {
                Log.info("ProgressBar for " + tower + " removed");
                this.remove();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && this.isVisible()) {
            progressBar.draw(batch, this.getX(), this.getY(),
                    (tower.buildCount / tower.getPrototype().buildDelay) * maxWidth,
                    this.getHeight());
        }
    }
}

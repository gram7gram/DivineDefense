package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import ua.gram.DDGame;
import ua.gram.model.actor.Tower;

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
        this.setDebug(DDGame.DEBUG);
        this.setTouchable(Touchable.disabled);
        this.setSize(tower.getWidth(), 3);
        this.setPosition(tower.getX(), tower.getY() - this.getHeight() - 2);
        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        maxWidth = this.getWidth();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE && tower.isBuilding) {
            this.setVisible(tower.countBuilding <= tower.build_delay);
            if (!this.isVisible()) {
                this.remove();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (this.isVisible() && tower.isBuilding) {
            progressBar.draw(batch, this.getX(), this.getY(), (tower.countBuilding / tower.build_delay) * maxWidth, this.getHeight());
        }
    }
}

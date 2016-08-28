package ua.gram.model.actor.misc;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ProgressBar extends Actor implements Disposable {

    private final Tower tower;
    private final NinePatchDrawable progressBar;
    private final float maxWidth;
    private float progress;
    private float duration;

    public ProgressBar(Skin skin, Tower tower) {
        this.tower = tower;
        progressBar = new NinePatchDrawable(
                new NinePatch(skin.getRegion("health-bar-100"), 0, 0, 0, 0));
        setTouchable(Touchable.disabled);
        setSize(tower.getWidth(), 3);
        maxWidth = getWidth();
        progress = 0;
        duration = 0;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setDuration(float duration) {
        if (duration == 0)
            throw new IllegalArgumentException("Duration should be non-zero value");
        this.duration = duration;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (duration > 0) {
            setDebug(DDGame.DEBUG);
            if (!DDGame.PAUSE) {
                setPosition(tower.getX(), tower.getY() - getHeight() - 2);
                if (isFinished()) {
                    Log.info("ProgressBar for " + tower + " hidden");
                    dispose();
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE && isVisible()) {
            progressBar.draw(batch, getX(), getY(),
                    (progress / duration) * maxWidth,
                    getHeight());
        }
    }

    public void addProgress(float delta) {
        progress += delta;
    }

    public boolean isFinished() {
        return progress >= duration && duration > 0 && progress > 0;
    }

    @Override
    public void dispose() {
        progress = 0;
        duration = 0;
        setVisible(false);
    }
}

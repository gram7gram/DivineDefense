package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.model.prototype.Prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AnimatedActor<T extends Prototype> extends Actor {

    protected final Skin skin;
    protected final Animation animation;
    protected float stateTime;
    private TextureRegion currentFrame;

    public AnimatedActor(Skin skin, T prototype) {
        this.skin = skin;
        this.animation = createAnimation(prototype);
        stateTime = 0;
    }

    protected abstract Animation createAnimation(T prototype);

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || currentFrame == null) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = animation.getKeyFrame(stateTime, false);
        }
        if (currentFrame != null) batch.draw(currentFrame, getX(), getY());
    }

    public void reset() {
        stateTime = 0;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Animation getAnimation() {
        return animation;
    }
}

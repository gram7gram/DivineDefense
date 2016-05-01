package ua.gram.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Animator<T1, T2> {

    private PoolableAnimation poolableAnimation;
    private T1 primaryType;
    private T2 secondaryType;
    private TextureRegion currentFrame;

    public PoolableAnimation getPoolable() {
        return poolableAnimation;
    }

    public Animation getAnimation() {
        return poolableAnimation != null ? poolableAnimation.getAnimation() : null;
    }

    public void setPollable(PoolableAnimation pollable) {
        this.poolableAnimation = pollable;
    }

    public T1 getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(T1 primaryType) {
        this.primaryType = primaryType;
    }

    public T2 getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(T2 secondaryType) {
        this.secondaryType = secondaryType;
    }

    public boolean hasAnimation() {
        return poolableAnimation != null;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean hasCurrentFrame() {
        return currentFrame != null;
    }
}

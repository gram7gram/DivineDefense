package ua.gram.model.actor.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.BombWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BombWeapon extends Weapon {

    private Animation animation;
    private float stateTime = 0;
    private TextureRegion currentFrame;

    public BombWeapon(Resources resources, TowerGroup tower, EnemyGroup target) {
        super(tower, target);
        BombWeaponPrototype proto = getPrototype();
        TextureRegion region = resources.getSkin().getRegion(proto.region);
        this.setSize(region.getRegionWidth() / proto.frames, region.getRegionHeight());
        TextureRegion[] tiles = region.split((int) this.getWidth(), (int) this.getHeight())[0];
        animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    @Override
    public void update(float delta) {
        if (isNull()) {
            this.setPosition(
                    target.getOriginX() - this.getWidth() / 2f,
                    target.getOriginY() - this.getHeight() / 2f + 10);
        }
    }

    @Override
    public boolean isFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    @Override
    public void render(Batch batch) {
        if (!DDGame.PAUSE || currentFrame == null) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null && !isNull()) batch.draw(currentFrame, getX(), getY());
    }

    private boolean isNull() {
        return getX() == 0 && getY() == 0;
    }

    @Override
    public void reset() {
        this.setPosition(0, 0);
        stateTime = 0;
        currentFrame = null;
    }

    @Override
    public BombWeaponPrototype getPrototype() {
        return (BombWeaponPrototype) prototype;
    }
}

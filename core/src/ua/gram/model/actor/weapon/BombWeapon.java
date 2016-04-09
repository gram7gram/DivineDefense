package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.builder.WeaponBuilder;
import ua.gram.model.group.Layer;
import ua.gram.model.prototype.weapon.BombWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BombWeapon extends Weapon implements AOEWeapon {

    private Layer currentLayer;

    public BombWeapon(WeaponBuilder builder, Resources resources, WeaponPrototype prototype) {
        super(builder, resources, prototype);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        BombWeaponPrototype proto = getPrototype();
        TextureRegion region = skin.getRegion(proto.region);
        this.setSize(region.getRegionWidth() / proto.frames, region.getRegionHeight());
        TextureRegion[] tiles = region.split((int) this.getWidth(), (int) this.getHeight())[0];
        Animation animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        return animation;
    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        if (targetIndex < parentIndex) {
            this.setZIndex(0);
            towerGroup.getRootActor().setZIndex(1);
        } else {
            if (currentLayer == null && towerGroup.getLayer() != null)
                currentLayer = towerGroup.getRootActor().getStage()
                        .putOnLayer(this, towerGroup.getLayer().getZIndex() + 1);
        }
    }

    @Override
    public void update(float delta) {
        if (isOutOfBounds()) {
            setPosition(
                    targetGroup.getOriginX() - getWidth() / 2f,
                    targetGroup.getOriginY() - getHeight() / 2f + 10);
        }
    }

    @Override
    public void resetObject() {
        super.resetObject();
        setPosition(0, 0);
        currentLayer = null;
    }

    @Override
    public BombWeaponPrototype getPrototype() {
        return (BombWeaponPrototype) prototype;
    }

    @Override
    public float getAOERange() {
        return getPrototype().aoeRange;
    }
}

package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.Resources;
import ua.gram.model.group.Layer;
import ua.gram.model.prototype.weapon.BombWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BombWeapon extends Weapon implements AOEWeapon {

    private Layer currentLayer;

    public BombWeapon(Resources resources, BombWeaponPrototype prototype) {
        super(resources, prototype);
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
            this.setPosition(
                    targetGroup.getOriginX() - this.getWidth() / 2f,
                    targetGroup.getOriginY() - this.getHeight() / 2f + 10);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.setPosition(0, 0);
        //Return Weapon to TowerGroup
        this.remove();
        towerGroup.addActor(this);
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

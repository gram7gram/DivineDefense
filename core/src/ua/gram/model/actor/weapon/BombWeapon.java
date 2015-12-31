package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.Resources;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.Layer;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.BombWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BombWeapon extends Weapon {

    private Layer currentLayer;

    public BombWeapon(Resources resources, TowerGroup tower, EnemyGroup target) {
        super(resources, tower, target);
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
            tower.getRootActor().setZIndex(1);
        } else {
            if (currentLayer == null)
                currentLayer = tower.getRootActor().getStage().toggleZIndex(this, tower.getParent().getZIndex() + 1);
        }
    }

    @Override
    public void update(float delta) {
        if (isOutOfBounds()) {
            this.setPosition(
                    target.getOriginX() - this.getWidth() / 2f,
                    target.getOriginY() - this.getHeight() / 2f + 10);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.setPosition(0, 0);
        //Return Weapon to TowerGroup
        this.remove();
        tower.addActor(this);
        currentLayer = null;
    }

    @Override
    public BombWeaponPrototype getPrototype() {
        return (BombWeaponPrototype) prototype;
    }
}

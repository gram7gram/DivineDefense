package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.builder.WeaponBuilder;
import ua.gram.controller.stage.BattleStage;
import ua.gram.model.group.Layer;
import ua.gram.model.prototype.weapon.FreezeWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FreezeWeapon extends Weapon implements AOEWeapon {

    private Layer currentLayer;

    public FreezeWeapon(WeaponBuilder builder, Resources resources, WeaponPrototype prototype) {
        super(builder, resources, prototype);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        FreezeWeaponPrototype proto = getPrototype();
        TextureRegion region = skin.getRegion(proto.region);
        setSize(region.getRegionWidth() / proto.frames, region.getRegionHeight());
        TextureRegion[] tiles = region.split((int) getWidth(), (int) getHeight())[0];
        Animation animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.REVERSED);
        return animation;
    }

    @Override
    public void update(float delta) {
        this.setPosition(
                towerGroup.getOriginX() - this.getWidth() / 2f,
                towerGroup.getOriginY() - this.getHeight() / 2f
        );
    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        if (currentLayer == null) {
            BattleStage stage = towerGroup.getRootActor().getStage();
            currentLayer = stage.putOnLayer(this, parentIndex > 0 ? parentIndex - 1 : 0);
            toBack();
        }
    }

    @Override
    public void resetObject() {
        super.resetObject();
        //Return Weapon to TowerGroup
        if (getParent() != towerGroup) {
            remove();
            towerGroup.addActor(this);
        }
        currentLayer = null;
    }

    @Override
    public FreezeWeaponPrototype getPrototype() {
        return (FreezeWeaponPrototype) prototype;
    }

    @Override
    public float getAOERange() {
        return getPrototype().aoeRange;
    }
}

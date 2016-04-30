package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.weapon.WeaponProvider;
import ua.gram.model.group.Layer;
import ua.gram.model.prototype.weapon.LightningWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LightningWeapon extends Weapon {

    private Layer currentLayer;
    private float originalHeight;

    public LightningWeapon(WeaponProvider builder, Resources resources, WeaponPrototype prototype) {
        super(builder, resources, prototype);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        LightningWeaponPrototype proto = getPrototype();
        TextureRegion region = skin.getRegion(proto.region);
        originalHeight = region.getRegionHeight();
        setSize(region.getRegionWidth() / proto.frames, originalHeight);
        TextureRegion[] tiles = region.split((int) getWidth(), (int) getHeight())[0];
        Animation animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.REVERSED);
        return animation;
    }

    @Override
    public void update(float delta) {
        if (isOutOfBounds()) {
            float scaler = getScaler(originalHeight);
            setSize(getWidth(), originalHeight * scaler);
            scale(1, scaler);
            setPosition(targetGroup.getOriginX() - getWidth() / 2f, targetGroup.getOriginY());
        }
    }

    private float getScaler(float height) {
        float diff = DDGame.WORLD_HEIGHT - height;
        return diff >= 0 ? 1 + (diff / DDGame.WORLD_HEIGHT) : 1;
    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        if (currentLayer == null) {
            BattleStage stage = towerGroup.getRootActor().getStage();
            currentLayer = stage.putOnLayer(this, parentIndex + 1);
            toFront();
        }
    }

    @Override
    public void resetObject() {
        super.resetObject();
        //Return Weapon to TowerGroup
        if (getParent() != towerGroup) {
            remove();
            towerGroup.addActor(this);
            toBack();
        }
        setPosition(0, 0);
        currentLayer = null;
    }

    @Override
    public LightningWeaponPrototype getPrototype() {
        if (!(prototype instanceof LightningWeaponPrototype))
            throw new GdxRuntimeException("Prototype is not instance of LightningWeaponPrototype");
        return (LightningWeaponPrototype) prototype;
    }
}

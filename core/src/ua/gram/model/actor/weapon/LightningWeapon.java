package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.Resources;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.model.group.Layer;
import ua.gram.model.prototype.weapon.LightningWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LightningWeapon extends Weapon {

    private Layer currentLayer;
    private float originalHeight;

    public LightningWeapon(Resources resources, WeaponPrototype prototype) {
        super(resources, prototype);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        LightningWeaponPrototype proto = getPrototype();
        TextureRegion region = skin.getRegion(proto.region);
        originalHeight = region.getRegionHeight();
        this.setSize(region.getRegionWidth() / proto.frames, originalHeight);
        TextureRegion[] tiles = region.split((int) this.getWidth(), (int) this.getHeight())[0];
        Animation animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.REVERSED);
        return animation;
    }

    @Override
    public void update(float delta) {
        if (isOutOfBounds()) {
            float scaler = getScaler(originalHeight);
            this.setSize(this.getWidth(), originalHeight * scaler);
            this.scale(1, scaler);
            this.setPosition(targetGroup.getOriginX() - this.getWidth() / 2f, targetGroup.getOriginY());
        }
    }

    private float getScaler(float height) {
        float diff = DDGame.WORLD_HEIGHT - height;
        return diff >= 0 ? 1 + (diff / DDGame.WORLD_HEIGHT) : 1;
    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        if (currentLayer == null) {
            GameBattleStage stage = towerGroup.getRootActor().getStage();
            currentLayer = stage.putOnLayer(this, parentIndex + 1);
            this.toFront();
        }
    }

    @Override
    public void reset() {
        super.reset();
        //Return Weapon to TowerGroup
        if (this.getParent() != towerGroup) {
            this.remove();
            towerGroup.addActor(this);
            this.toBack();
        }
        this.setPosition(0, 0);
        currentLayer = null;
    }

    @Override
    public LightningWeaponPrototype getPrototype() {
        return (LightningWeaponPrototype) prototype;
    }
}

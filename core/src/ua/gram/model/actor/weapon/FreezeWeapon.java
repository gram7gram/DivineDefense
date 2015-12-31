package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ua.gram.controller.Resources;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.prototype.FreezeWeaponPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FreezeWeapon extends Weapon {

    public FreezeWeapon(Resources resources, TowerGroup tower, EnemyGroup target) {
        super(resources, tower, target);
    }

    @Override
    protected Animation createAnimation(Skin skin) {
        FreezeWeaponPrototype proto = getPrototype();
        TextureRegion region = skin.getRegion(proto.region);
        this.setSize(region.getRegionWidth() / proto.frames, region.getRegionHeight());
        TextureRegion[] tiles = region.split((int) this.getWidth(), (int) this.getHeight())[0];
        Animation animation = new Animation(proto.delay, tiles);
        animation.setPlayMode(Animation.PlayMode.REVERSED);

        this.setPosition(
                tower.getOriginX() - this.getWidth() / 2f,
                tower.getOriginY() - this.getHeight() / 2f
        );

        return animation;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        this.toBack();
    }

    @Override
    public FreezeWeaponPrototype getPrototype() {
        return (FreezeWeaponPrototype) prototype;
    }
}

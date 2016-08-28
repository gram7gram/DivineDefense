package ua.gram.model.actor.weapon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.controller.weapon.WeaponProvider;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.Layer;
import ua.gram.model.group.TargetPoints;
import ua.gram.model.prototype.weapon.FreezeWeaponPrototype;
import ua.gram.model.prototype.weapon.WeaponPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FreezeWeapon extends Weapon implements AOEWeapon {

    private Layer currentLayer;

    public FreezeWeapon(WeaponProvider builder, Resources resources, WeaponPrototype prototype) {
        super(builder, resources, prototype);
    }

    @Override
    public void preAttack(Tower tower, Enemy victim) {
        super.preAttack(tower, victim);
        EnemyStateManager manager = victim.getStateManager();
        manager.swapLevel4State(victim, manager.getStunState());
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
        TargetPoints targets = towerGroup.getTargetPoints();
        Actor target = targets.getBase();
        setPosition(
                target.getX() - this.getWidth() / 2f,
                target.getY() - this.getHeight() / 2f
        );
    }

    @Override
    protected void handleIndexes(int targetIndex, int parentIndex) {
        if (currentLayer == null) {
            BattleStage stage = towerGroup.getRootActor().getStage();
            currentLayer = stage.putOnLayer(this, 0);
            toBack();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
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

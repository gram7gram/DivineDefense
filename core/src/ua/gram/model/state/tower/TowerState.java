package ua.gram.model.state.tower;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.enemy.StateSwapper;
import ua.gram.controller.pool.animation.AnimationPool;
import ua.gram.controller.tower.TowerAnimationProvider;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.model.state.AbstractState;

/**
 * Representation of Actor at the moment of time.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class TowerState extends AbstractState<Tower> {

    protected StateSwapper<Tower> stateSwapper;

    public TowerState(DDGame game) {
        super(game);
        stateSwapper = new StateSwapper<>();
        Log.info("Tower " + this.getClass().getSimpleName() + " state is OK");
    }

    public void initAnimation(Tower tower) {
        TowerShop shop = tower.getTowerShop();
        TowerAnimationProvider provider = shop.getAnimationProvider();

        setUncheckedType(tower);

        AnimationPool pool = provider.get(tower.getPrototype(), getType(),
                getType(tower.getProperty().getTowerLevel()));
        tower.setAnimation(pool.obtain());
    }

    @SuppressWarnings("unchecked")
    private void setUncheckedType(Tower tower) {
        tower.getAnimator().setPrimaryType(getType());
        tower.getAnimator().setSecondaryType(getType(tower.getProperty().getTowerLevel()));
    }

    private Types.TowerLevels getType(int level) {
        switch (level) {
            case 1:
                return Types.TowerLevels.Lvl1;
            case 2:
                return Types.TowerLevels.Lvl2;
            case 3:
                return Types.TowerLevels.Lvl3;
            case 4:
                return Types.TowerLevels.Lvl4;
            default:
                throw new NullPointerException("Unkonwn tower level: " + level);
        }
    }

    protected abstract Types.TowerState getType();
}

package ua.gram.model.state.tower.level2;

import ua.gram.DDGame;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AttackState extends Level2State {

    public AttackState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.ATTACK;
    }

    @Override
    public void preManage(Tower actor) {

    }

    @Override
    public void manage(Tower actor, float delta) {

    }

    @Override
    public void postManage(Tower actor) {

    }
}

package ua.gram.controller.state.boss.level2;

import ua.gram.DDGame;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class IdleState extends Level2State {

    public IdleState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.IDLE;
    }

    @Override
    public void preManage(Boss actor) {
        manager.getAnimationChanger()
                .update(actor, getType());
        super.preManage(actor);
    }
}

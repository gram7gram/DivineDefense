package ua.gram.controller.state.boss.level2;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ExclamationState extends Level2State {

    public ExclamationState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.EXCLAMATE;
    }

    @Override
    public void preManage(Boss actor) {
        manager.getAnimationChanger()
                .update(actor, getType());

        super.preManage(actor);
    }

    @Override
    public void manage(Boss actor, float delta) {
        super.manage(actor, delta);
        BossStateManager manager = actor.getStateManager();
        Counters counters = actor.getCounters();
        float count = counters.get("exclamation_count");
        if (count >= counters.get("exclamation_duration")) {
            manager.swapLevel1State(actor, manager.getActiveState());
            manager.swapLevel2State(actor, manager.getIdleState());
            counters.set("exclamation_count", 0);
        } else {
            counters.set("exclamation_count", count + delta);
        }
    }
}

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
        return Types.BossState.EXCLAMATION;
    }

    @Override
    public void preManage(Boss actor) {
        actor.getLevel().getBossAnimationManager()
                .getSkeletonState()
                .setAnimation(0, getType().name(), false);

        super.preManage(actor);

        actor.getCounters()
                .set("exclamation_count", 0)
                .set("exclamation_duration", 3);
    }

    @Override
    public void manage(Boss actor, float delta) {
        super.manage(actor, delta);
        BossStateManager manager = actor.getStateManager();
        Counters counters = actor.getCounters();
        float count = counters.get("exclamation_count");
        if (count >= counters.get("exclamation_duration")) {
            counters.set("exclamation_count", 0);
            manager.swapLevel2State(actor, manager.getIdleState());
        } else {
            counters.set("exclamation_count", count + delta);
        }
    }
}

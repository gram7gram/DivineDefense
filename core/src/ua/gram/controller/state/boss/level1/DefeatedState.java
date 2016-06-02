package ua.gram.controller.state.boss.level1;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatedState extends Level1State {

    public DefeatedState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.DEFEATED;
    }

    @Override
    public void preManage(Boss actor) {
        actor.getLevel().getBossAnimationManager()
                .getSkeletonState()
                .setAnimation(0, getType().name(), false);
        super.preManage(actor);
        manager.swapLevel2State(actor, null);
        actor.clearActions();
    }

    @Override
    public void manage(Boss actor, float delta) {
        super.manage(actor, delta);
        BossStateManager manager = actor.getStateManager();
        Counters counters = actor.getCounters();
        float count = counters.get("defeat_count");
        if (count >= counters.get("defeat_duration")) {
            manager.swapLevel1State(actor, manager.getActiveState());
            manager.swapLevel2State(actor, manager.getIdleState());
            counters.set("defeat_count", 0);
        } else {
            counters.set("defeat_count", count + delta);
        }
    }

    @Override
    public void postManage(Boss actor) {
        super.postManage(actor);
        actor.remove();
    }
}

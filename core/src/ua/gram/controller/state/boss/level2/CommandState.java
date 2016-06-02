package ua.gram.controller.state.boss.level2;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CommandState extends Level2State {

    public CommandState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.COMMAND;
    }

    @Override
    public void preManage(Boss actor) {
        actor.getLevel().getBossAnimationManager()
                .getSkeletonState()
                .setAnimation(0, getType().name(), false);

        super.preManage(actor);

        actor.getCounters()
                .set("command_count", 0)
                .set("command_duration", 3);
    }

    @Override
    public void manage(Boss actor, float delta) {
        super.manage(actor, delta);
        BossStateManager manager = actor.getStateManager();
        Counters counters = actor.getCounters();
        float count = counters.get("command_count");
        if (count >= counters.get("command_duration")) {
            counters.set("command_count", 0);
            manager.swapLevel2State(actor, manager.getIdleState());
        } else {
            counters.set("command_count", count + delta);
        }
    }
}

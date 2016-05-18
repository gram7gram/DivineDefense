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
        manager.getAnimationChanger()
                .update(actor, getType());

        super.preManage(actor);
    }

    @Override
    public void manage(Boss actor, float delta) {
        super.manage(actor, delta);
        BossStateManager manager = actor.getStateManager();
        Counters counters = actor.getCounters();
        float count = counters.get("command_count");
        if (count >= counters.get("command_duration")) {
            manager.swapLevel1State(actor, manager.getActiveState());
            manager.swapLevel2State(actor, manager.getIdleState());
            counters.set("command_count", 0);
        } else {
            counters.set("command_count", count + delta);
        }
    }
}

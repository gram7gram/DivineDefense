package ua.gram.controller.state.boss.level2;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.SpineStatePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ExclamationState extends Level2State {

    private final String STATE_COUNT = "exclamation_count";
    private final String STATE_DURATION = "exclamation_duration";

    public ExclamationState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.EXCLAMATION;
    }

    @Override
    public void preManage(Boss boss) {
        boss.updateAnimation(getType(), false);

        super.preManage(boss);

        SpineStatePrototype prototype = boss.getPrototype()
                .getSpineStatePrototype(getType());

        boss.getCounters()
                .set(STATE_COUNT, 0)
                .set(STATE_DURATION, prototype.duration);
    }

    @Override
    public void manage(Boss boss, float delta) {
        super.manage(boss, delta);
        BossStateManager manager = boss.getStateManager();
        Counters counters = boss.getCounters();
        float count = counters.get(STATE_COUNT);
        if (count >= counters.get(STATE_DURATION)) {
            counters.set(STATE_COUNT, 0);
            manager.swapLevel2State(boss, manager.getIdleState());
        } else {
            counters.set(STATE_COUNT, count + delta);
        }
    }
}

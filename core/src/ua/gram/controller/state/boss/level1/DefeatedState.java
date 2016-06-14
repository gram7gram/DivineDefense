package ua.gram.controller.state.boss.level1;

import ua.gram.DDGame;
import ua.gram.controller.Counters;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;
import ua.gram.model.prototype.SpineStatePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DefeatedState extends Level1State {

    private final String STATE_COUNT = "defeat_count";
    private final String STATE_DURATION = "defeat_duration";

    public DefeatedState(DDGame game, BossStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.BossState getType() {
        return Types.BossState.DEFEAT;
    }

    @Override
    public void preManage(Boss boss) {
        boss.updateAnimation(getType(), false);

        super.preManage(boss);

        manager.swapLevel2State(boss, null);

        boss.clearActions();

        SpineStatePrototype prototype = boss.getPrototype()
                .getSpineStatePrototype(getType());

        boss.getCounters()
                .set(STATE_DURATION, prototype.duration)
                .set(STATE_COUNT, 0);

        boss.getLevel().setInterrupted(true);
    }

    @Override
    public void manage(Boss boss, float delta) {
        super.manage(boss, delta);
        BossStateManager manager = boss.getStateManager();
        Counters counters = boss.getCounters();
        float count = counters.get(STATE_COUNT);
        if (count >= counters.get(STATE_DURATION)) {
            counters.set(STATE_COUNT, 0);
            manager.swapLevel1State(boss, null);
        } else {
            counters.set(STATE_COUNT, count + delta);
        }
    }

    @Override
    public void postManage(Boss boss) {
        super.postManage(boss);
        boss.setDefeated(true);
    }
}

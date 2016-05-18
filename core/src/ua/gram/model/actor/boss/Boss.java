package ua.gram.model.actor.boss;

import ua.gram.controller.Counters;
import ua.gram.controller.enemy.DirectionHolder;
import ua.gram.controller.listener.LevelFinishedListener;
import ua.gram.controller.listener.PlayerDefeatedListener;
import ua.gram.controller.listener.WaveStartedListener;
import ua.gram.controller.state.boss.BossStateHolder;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.Initializer;
import ua.gram.model.actor.GameActor;
import ua.gram.model.enums.Types;
import ua.gram.model.level.FinalLevel;
import ua.gram.model.map.Path;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Boss
        extends GameActor<Types.BossState, Path.Direction, BossStateManager>
        implements Initializer {

    protected final BossStateManager manager;
    protected final BossStateHolder stateHolder;
    private final Counters counters;
    protected DirectionHolder directionHolder;
    private FinalLevel level;

    public Boss(BossPrototype prototype, BossStateManager manager) {
        super(prototype);
        this.manager = manager;

        setBounds(prototype.position.x,
                prototype.position.y,
                prototype.width,
                prototype.height);

        stateHolder = new BossStateHolder();

        counters = new Counters();

        Log.info(getClass().getSimpleName() + " is OK");
    }

    @Override
    public void init() {
        directionHolder = new DirectionHolder(this);

        addListener(new PlayerDefeatedListener(this));
        addListener(new WaveStartedListener(this));
        addListener(new LevelFinishedListener(this));

        manager.swapLevel1State(this, manager.getActiveState());
    }

    @Override
    public BossStateManager getStateManager() {
        return manager;
    }

    public BossStateHolder getStateHolder() {
        return stateHolder;
    }

    public DirectionHolder getDirectionHolder() {
        return directionHolder;
    }

    @Override
    public BossPrototype getPrototype() {
        return (BossPrototype) prototype;
    }

    public FinalLevel getLevel() {
        return level;
    }

    public void setLevel(FinalLevel level) {
        this.level = level;
    }

    public Counters getCounters() {
        return counters;
    }

    @Override
    public void resetObject() {
        super.resetObject();
        counters.resetObject();
    }
}

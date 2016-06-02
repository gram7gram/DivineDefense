package ua.gram.model.level;

import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.DDGame;
import ua.gram.controller.animation.boss.BossAnimationManager;
import ua.gram.controller.factory.LevelFactory;
import ua.gram.controller.listener.LevelFinishedListener;
import ua.gram.controller.listener.PlayerDefeatedListener;
import ua.gram.controller.listener.WaveStartedListener;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.actor.boss.FinalBoss;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.model.prototype.level.LevelPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class FinalLevel extends Level {

    private final Boss boss;
    private final BossStateManager manager;
    private final BossAnimationManager bossAnimationManager;

    public FinalLevel(DDGame game, LevelPrototype prototype, LevelFactory.Type type) {
        super(game, prototype, type);

        manager = new BossStateManager(game);

        BossPrototype bossPrototype = findBossPrototype(prototype);

        boss = new FinalBoss(game, bossPrototype, manager);
        bossAnimationManager = new BossAnimationManager(game.getResources(), bossPrototype);
    }

    private BossPrototype findBossPrototype(LevelPrototype prototype) {
        if (prototype.bosses.length == 0)
            throw new GdxRuntimeException("Final level requires the boss");

        BossPrototype proto = null;

        for (BossPrototype p : game.getPrototype().bosses) {
            for (String boss : prototype.bosses) {
                if (p.name.equals(boss)) {
                    proto = p;
                    break;
                }
            }
        }

        if (proto == null)
            throw new NullPointerException("Final level boss prototype was not found");

        return proto;
    }

    @Override
    public void init() {
        super.init();
        manager.init();
        boss.setLevel(this);
        boss.init();
        battleStage.addActor(boss);

        battleStage.getRoot().addListener(new PlayerDefeatedListener(boss));
        battleStage.getRoot().addListener(new WaveStartedListener(boss));
        battleStage.getRoot().addListener(new LevelFinishedListener(boss));
    }

    public BossAnimationManager getBossAnimationManager() {
        return bossAnimationManager;
    }
}

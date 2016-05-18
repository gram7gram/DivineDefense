package ua.gram.model.level;

import ua.gram.DDGame;
import ua.gram.controller.animation.boss.BossAnimationProvider;
import ua.gram.controller.factory.LevelFactory;
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
    private final BossAnimationProvider animationProvider;


    public FinalLevel(DDGame game, LevelPrototype prototype, LevelFactory.Type type) {
        super(game, prototype, type);

        manager = new BossStateManager(game);

        animationProvider = new BossAnimationProvider(
                game.getResources().getSkin(),
                game.getPrototype().bosses);

        boss = new FinalBoss(findBossPrototype(prototype), manager);
    }

    private BossPrototype findBossPrototype(LevelPrototype prototype) {
        BossPrototype proto = null;

        for (BossPrototype p : game.getPrototype().bosses) {
            if (p.type.equals(prototype.boss)) {
                proto = p;
                break;
            }
        }

        if (proto == null)
            throw new NullPointerException(prototype.boss + " boss prototype is not registered");

        return proto;
    }

    @Override
    public void init() {
        super.init();
        boss.setLevel(this);
        battleStage.addActor(boss);
    }

    public BossAnimationProvider getAnimationProvider() {
        return animationProvider;
    }
}

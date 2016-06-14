package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.controller.event.LevelFinishedEvent;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossDefeatedListener implements EventListener {

    private final Boss boss;

    public BossDefeatedListener(Boss boss) {
        this.boss = boss;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof LevelFinishedEvent)) return false;

        BossStateManager manager = boss.getStateManager();

        if (boss.getStateHolder().getCurrentLevel1State() != manager.getDefeatedState()) {
            manager.swapLevel1State(boss, manager.getDefeatedState());
        }

        return true;
    }
}

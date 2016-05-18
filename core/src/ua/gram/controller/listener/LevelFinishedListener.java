package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.controller.event.LevelFinishedEvent;
import ua.gram.controller.state.boss.BossStateManager;
import ua.gram.model.actor.boss.Boss;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelFinishedListener implements EventListener {

    private final Boss boss;

    public LevelFinishedListener(Boss boss) {
        this.boss = boss;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof LevelFinishedEvent)) return false;

        BossStateManager manager = boss.getStateManager();

        manager.swapLevel2State(boss, manager.getExclamationState());

        return false;
    }
}

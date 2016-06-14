package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.DDGame;
import ua.gram.controller.event.PlayerDefeatedEvent;
import ua.gram.controller.stage.StageHolder;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PlayerDefeatedListener implements EventListener {

    private final StageHolder holder;

    public PlayerDefeatedListener(StageHolder holder) {
        this.holder = holder;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof PlayerDefeatedEvent)) return false;

        holder.getUiStage().showDefeatWindow();

        DDGame.pauseGame();

        return false;
    }
}

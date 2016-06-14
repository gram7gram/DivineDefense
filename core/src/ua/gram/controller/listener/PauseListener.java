package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.DDGame;
import ua.gram.controller.event.PauseEvent;
import ua.gram.controller.stage.StageHolder;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PauseListener implements EventListener {

    private final StageHolder stageHolder;

    public PauseListener(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof PauseEvent)) return false;

        DDGame.PAUSE = ((PauseEvent) event).isPaused();
        stageHolder.getUiStage().showPauseWindow();

        return false;
    }
}

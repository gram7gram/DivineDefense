package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ua.gram.DDGame;
import ua.gram.controller.event.LevelFinishedEvent;
import ua.gram.controller.stage.StageHolder;
import ua.gram.model.level.Level;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class LevelFinishedListener implements EventListener {

    private final StageHolder holder;

    public LevelFinishedListener(StageHolder holder) {
        this.holder = holder;
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof LevelFinishedEvent)) return false;

        Level level = holder.getBattleStage().getLevel();

        level.finish();

        for (Actor actor : holder.getBattleStage().getActors()) {
            if (!(actor instanceof Image)) {
                actor.remove();
            }
        }

        DDGame.pauseGame();

        holder.getUiStage().showVictoryWindow();

        return true;
    }
}

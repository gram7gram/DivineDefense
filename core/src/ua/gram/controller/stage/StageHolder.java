package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import ua.gram.controller.listener.BattleStageClickListener;
import ua.gram.controller.listener.LevelFinishedListener;
import ua.gram.controller.listener.PauseListener;
import ua.gram.controller.listener.PlayerDefeatedListener;
import ua.gram.controller.listener.TowerControlsToggleListener;
import ua.gram.model.Initializer;
import ua.gram.model.group.TowerControls;
import ua.gram.utils.Log;
import ua.gram.view.screen.GameScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StageHolder implements Initializer {

    private final UIStage uiStage;
    private final BattleStage battleStage;
    private final GameScreen screen;

    public StageHolder(GameScreen screen, UIStage uiStage, BattleStage battleStage) {
        this.screen = screen;
        this.uiStage = uiStage;
        this.battleStage = battleStage;
    }

    @Override
    public void init() {
        battleStage.init();
        uiStage.init();
        EventListener levelFinished = new PlayerDefeatedListener(this);
        EventListener playerDefeated = new LevelFinishedListener(this);
        EventListener pauseListener = new PauseListener(this);
        uiStage.addListener(levelFinished);
        uiStage.addListener(playerDefeated);
        uiStage.addListener(pauseListener);

        TowerControls controls = uiStage.getTowerControls();
        EventListener controlsListener = new TowerControlsToggleListener(controls);
        EventListener battleStageClickListener = new BattleStageClickListener(this);
        battleStage.addListener(controlsListener);
        battleStage.addListener(battleStageClickListener);
    }

    /**
     * Fire event on all roots of the stages in this holder
     */
    public void fire(Event event) {
        String eventName = event.getClass().getSimpleName();
        Log.warn(eventName + " was fired on all stages");

        fireLevel(event);

        if (!event.isHandled()) {
            fireUI(event);
        } else {
            Log.warn(eventName + " was not executed on UI stage. It was handled before");
        }

        if (!event.isHandled()) {
            fireBattle(event);
        } else {
            Log.warn(eventName + " was not executed on Battle stage. It was handled before");
        }
    }

    /**
     * Fire event on Battle stage's root
     */
    public void fireBattle(Event event) {
        battleStage.getRoot().fire(event);
    }

    /**
     * Fire event on UI stage's root
     */
    public void fireUI(Event event) {
        uiStage.getRoot().fire(event);
    }

    /**
     * Fire event on Level. Perform level specific actions there
     */
    public void fireLevel(Event event) {
        battleStage.getLevel().propagateEvent(event);
    }

    public UIStage getUiStage() {
        return uiStage;
    }

    public BattleStage getBattleStage() {
        return battleStage;
    }

    public GameScreen getGameScreen() {
        return screen;
    }
}

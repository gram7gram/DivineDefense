package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Event;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StageHolder {

    private final UIStage uiStage;
    private final BattleStage battleStage;

    public StageHolder(UIStage uiStage, BattleStage battleStage) {
        this.uiStage = uiStage;
        this.battleStage = battleStage;
    }

    public UIStage getUiStage() {
        return uiStage;
    }

    public BattleStage getBattleStage() {
        return battleStage;
    }

    public void fire(Event event) {
        battleStage.getRoot().fire(event);
        uiStage.getRoot().fire(event);
    }
}

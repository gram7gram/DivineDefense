package ua.gram.controller.stage;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class StageHolder {

    private final UIStage uiStage;
    private final ua.gram.controller.stage.BattleStage battleStage;

    public StageHolder(UIStage uiStage, ua.gram.controller.stage.BattleStage battleStage) {
        this.uiStage = uiStage;
        this.battleStage = battleStage;
    }

    public UIStage getUiStage() {
        return uiStage;
    }

    public ua.gram.controller.stage.BattleStage getBattleStage() {
        return battleStage;
    }
}

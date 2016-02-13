package ua.gram.model.stage;

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
}

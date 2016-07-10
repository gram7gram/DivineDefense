package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.controller.event.TowerControlsToggleEvent;
import ua.gram.controller.stage.StageHolder;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BattleStageClickListener extends ClickListener {

    private final StageHolder stageHolder;

    public BattleStageClickListener(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (event.isStopped()) return;
        stageHolder.fireBattle(new TowerControlsToggleEvent(null, Types.TowerControls.INVISIBLE));
    }
}

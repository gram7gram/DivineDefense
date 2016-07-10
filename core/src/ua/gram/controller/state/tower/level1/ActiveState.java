package ua.gram.controller.state.tower.level1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.event.TowerControlsToggleEvent;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.state.tower.TowerStateManager;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.enums.Types;
import ua.gram.model.group.TowerGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ActiveState extends Level1State {

    public ActiveState(DDGame game, TowerStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.TowerState getType() {
        return Types.TowerState.IDLE;
    }

    @Override
    public void preManage(Tower tower) {
        super.preManage(tower);
        final StageHolder stageHolder = tower.getStage().getStageHolder();
        final TowerGroup towerGroup = tower.getParent();

        tower.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.stop();
                stageHolder.fireBattle(new TowerControlsToggleEvent(towerGroup, Types.TowerControls.VISIBLE));
            }
        });
    }

    @Override
    public void postManage(Tower actor) {
        actor.clearListeners();
    }
}

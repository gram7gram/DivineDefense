package ua.gram.controller.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;
import ua.gram.controller.listener.DebugListener;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractStage extends Stage {

    protected final DDGame game;
    protected final DebugListener debugListener;
    protected StageHolder stageHolder;

    public AbstractStage(DDGame game) {
        super(game.getViewport(), game.getBatch());
        this.game = game;
        debugListener = new DebugListener();
        addListener(debugListener);
    }

    @Override
    public void act() {
        super.act();
        setDebugAll(DDGame.DEBUG);
    }

    public StageHolder getStageHolder() {
        return stageHolder;
    }

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    protected Skin getSkin() {
        return game.getResources().getSkin();
    }
}

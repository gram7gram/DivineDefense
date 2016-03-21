package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.StageHolder;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DebugListener extends InputListener {

    private StageHolder stageHolder;

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.PLUS:
                if (DDGame.DEBUG) break;
                DDGame.DEBUG = true;
                Log.info("Debuging is on");
                break;
            case Input.Keys.MINUS:
                if (!DDGame.DEBUG) break;
                DDGame.DEBUG = false;
                Log.info("Debuging is off");
                break;
            case Input.Keys.ESCAPE:
                Log.warn("Pressed ESC in debug mode. Will exit");
                Gdx.app.exit();
                break;
            case Input.Keys.D:
                if (stageHolder != null) {
                    stageHolder.getUiStage().toggleDefeatWindow();
                    DDGame.PAUSE = !DDGame.PAUSE;
                }
                break;
            case Input.Keys.V:
                if (stageHolder != null) {
                    stageHolder.getUiStage().toggleVictoryWindow();
                    DDGame.PAUSE = !DDGame.PAUSE;
                }
                break;
            case Input.Keys.P:
                if (stageHolder != null) {
                    stageHolder.getUiStage().togglePauseWindow();
                    DDGame.PAUSE = !DDGame.PAUSE;
                }
                break;
        }
        return super.keyDown(event, keycode);
    }
}

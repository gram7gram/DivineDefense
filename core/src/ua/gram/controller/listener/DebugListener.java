package ua.gram.controller.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import ua.gram.DDGame;
import ua.gram.controller.stage.StageHolder;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class DebugListener extends InputListener {

    private StageHolder stageHolder;

    public void setStageHolder(StageHolder stageHolder) {
        this.stageHolder = stageHolder;
    }

    private void debug(boolean enable) {
        if (DDGame.DEBUG == enable) return;
        DDGame.DEBUG = enable;
        Log.info("Debuging is " + (DDGame.DEBUG ? "on" : "off"));
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.MENU:
                Log.info("Android menu button pressed in debug mode");
                debug(!DDGame.DEBUG);
                break;
            case Input.Keys.PLUS:
                debug(true);
                break;
            case Input.Keys.MINUS:
                debug(false);
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

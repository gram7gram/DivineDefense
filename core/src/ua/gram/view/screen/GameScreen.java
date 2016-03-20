package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.Level;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameScreen extends AbstractScreen {

    private final OrthogonalTiledMapRenderer renderer;
    private final BattleStage battleStage;
    private final UIStage uiStage;
    private final Level level;

    public GameScreen(DDGame game, Level level) {
        super(game);
        this.level = level;
        DDGame.PAUSE = false;
        game.getSpeed().reset();
        game.getPlayer().reset();
        renderer = new OrthogonalTiledMapRenderer(level.getMap().getTiledMap());
        renderer.setView(game.getCamera());
        battleStage = new BattleStage(game, level);
        uiStage = new UIStage(game, level);
        StageHolder stageHolder = new StageHolder(uiStage, battleStage);
        battleStage.setStageHolder(stageHolder);
        uiStage.setStageHolder(stageHolder);
        Log.info("GameScreen is OK");
    }

    @Override
    public void show() {
        Log.info("Screen set to GameScreen");
        battleStage.init();
        uiStage.init();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(battleStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        uiStage.getGameUIGroup().showNotification("LEVEL " + (level.getIndex()));
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderer.render(); //show tile map
        battleStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }

    /**
     * act() is placed in separated method due to battleStage
     * should not be updated if game is paused.
     */
    @Override
    public void renderOtherElements(float delta) {
        battleStage.act(delta);
    }

    @Override
    public void hide() {
        Log.warn("Hiding GameScreen");
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        Log.warn("GameScreen disposed");
    }

}

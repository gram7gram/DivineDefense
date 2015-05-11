package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ua.gram.DDGame;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.model.Level;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameScreen extends AbstractScreen {

    private final OrthogonalTiledMapRenderer renderer;
    private final GameBattleStage stage_battle;
    private final GameUIStage stage_ui;

    public GameScreen(DDGame game, Level level) {
        super(game);
        renderer = new OrthogonalTiledMapRenderer(level.getMap().getTiledMap());
        renderer.setView(game.getCamera());
        stage_battle = new GameBattleStage(game, level);
        stage_ui = new GameUIStage(game, stage_battle, level);
        Gdx.app.log("INFO", "Screen set to GameScreen");
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage_ui);
        inputMultiplexer.addProcessor(stage_battle);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render_ui(float delta) {
        Gdx.gl.glClearColor(0, 111 / 255f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderer.render(); //show tile map
        stage_battle.draw();
        stage_ui.act(delta);
        stage_ui.draw();
    }

    /**
     * act() is placed in separated method due to stage_battle should not be updated is game is paused.
     *
     * @param delta
     */
    @Override
    public void render_other(float delta) {
        stage_battle.act(delta);
    }

    @Override
    public void hide() {
        Gdx.app.log("WARN", "Hiding GameScreen");
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        Gdx.app.log("WARN", "GameScreen disposed");
    }

}

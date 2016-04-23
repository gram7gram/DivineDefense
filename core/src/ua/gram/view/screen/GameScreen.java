package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import ua.gram.DDGame;
import ua.gram.controller.listener.CameraListener;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.StageHolder;
import ua.gram.controller.stage.UIStage;
import ua.gram.model.actor.BackgroundImage;
import ua.gram.model.actor.ForegroundImage;
import ua.gram.model.level.Level;
import ua.gram.model.prototype.level.ResoursePrototype;
import ua.gram.utils.Log;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameScreen extends AbstractScreen {

    private final OrthogonalTiledMapRenderer renderer;
    private final BattleStage battleStage;
    private final UIStage uiStage;
    private final Level level;
    private final Color color;

    public GameScreen(DDGame game, Level level) {
        super(game);
        this.level = level;
        DDGame.PAUSE = false;
        game.getSpeed().reset();
        game.getPlayer().resetObject();
        renderer = new OrthogonalTiledMapRenderer(level.getMap().getTiledMap());
        battleStage = new BattleStage(game, level);
        uiStage = new UIStage(game, level, game.getPrototype().ui);
        StageHolder stageHolder = new StageHolder(uiStage, battleStage);
        battleStage.setStageHolder(stageHolder);
        uiStage.setStageHolder(stageHolder);
        color = level.getPrototype().backgroundColor;
        Log.info("GameScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        battleStage.init();
        uiStage.init();
        loadBackgroundElements();
        loadForegroundElements();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new CameraListener(game.getCamera(), level.getMap().getFirstLayer()));
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(battleStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        uiStage.getGameUIGroup().showNotification("LEVEL " + (level.getIndex()));
    }

    @Override
    public void renderUiElements(float delta) {
        Gdx.gl.glClearColor(color.r / 255f, color.g / 255f, color.b / 255f, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderer.setView(game.getCamera());
        renderer.render();
        battleStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }

    protected void loadBackgroundElements() {
        int size = level.getPrototype().assets.back.length;
        Log.info("Level " + level.getIndex() + " require " + size + " background textures");
        if (size == 0) return;

        for (ResoursePrototype resource : level.getPrototype().assets.back) {
            game.getResources().loadTexture(resource.file);
        }

        game.getResources().getManager().finishLoading();

        for (ResoursePrototype resource : level.getPrototype().assets.back) {
            Texture texture = game.getResources().getRegisteredTexture(resource.file);
            Image image = new BackgroundImage(texture, resource);
            battleStage.addActor(image);
            Log.info("Added background texture: " + resource.file);
        }
    }

    protected void loadForegroundElements() {
        int size = level.getPrototype().assets.front.length;
        Log.info("Level " + level.getIndex() + " require " + size + " foreground textures");
        if (size == 0) return;

        for (ResoursePrototype resource : level.getPrototype().assets.front) {
            game.getResources().loadTexture(resource.file);
        }

        game.getResources().getManager().finishLoading();

        for (ResoursePrototype resource : level.getPrototype().assets.front) {
            Texture texture = game.getResources().getRegisteredTexture(resource.file);
            Image image = new ForegroundImage(texture, resource);
            battleStage.addActor(image);
            Log.info("Added foreground texture: " + resource.file);
        }
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
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }

}

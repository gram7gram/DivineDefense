package ua.gram.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import ua.gram.model.prototype.level.LevelAssetPrototype;
import ua.gram.model.prototype.level.ResoursePrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;
import ua.gram.view.AbstractScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameScreen extends AbstractScreen {

    private final OrthogonalTiledMapRenderer renderer;
    private final Level level;
    private final InputMultiplexer inputMultiplexer;
    private CameraListener cameraListener;
    private StageHolder stageHolder;

    public GameScreen(DDGame game, Level level) {
        super(game);
        this.level = level;

        DDGame.resumeGame();
        game.getSpeed().reset();
        game.getPlayer().dispose();

        renderer = new OrthogonalTiledMapRenderer(level.getMap().getTiledMap());

        inputMultiplexer = new InputMultiplexer();

        Log.info("GameScreen is OK");
    }

    @Override
    public void show() {
        super.show();
        backgroundStage.clear();

        BattleStage battleStage = new BattleStage(game, level);
        UIStage uiStage = new UIStage(game, level, game.getPrototype().ui);

        cameraListener = new CameraListener(uiStage);

        stageHolder = new StageHolder(game, this, uiStage, battleStage);

        battleStage.setStageHolder(stageHolder);
        uiStage.setStageHolder(stageHolder);
        level.setStageHolder(stageHolder);

        level.init();

        setBackgroundColor(level.getPrototype().backgroundColor);

        renderer.setView(game.getCamera());
        stageHolder.init();

        loadBackgroundElements();
        loadForegroundElements();

        inputMultiplexer.addProcessor(cameraListener);
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(battleStage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        uiStage.getGameUIGroup().showNotification("LEVEL " + (level.getIndex()));
    }

    @Override
    public void renderAlways(float delta) {
        UIStage uiStage = stageHolder.getUiStage();
        BattleStage battleStage = stageHolder.getBattleStage();
        uiStage.act(delta);
        renderer.setView(game.getCamera());
        renderer.render();
        battleStage.draw();
        uiStage.draw();
    }

    @Override
    public void renderNoPause(float delta) {
        stageHolder.getBattleStage().act(delta);
    }

    private ResoursePrototype[] getBackgroundAssets(LevelAssetPrototype prototype) {
        return prototype == null || prototype.front == null ?
                null : prototype.front;
    }

    private ResoursePrototype[] getForegroundAssets(LevelAssetPrototype prototype) {
        return prototype == null || prototype.back == null ?
                null : prototype.back;
    }

    private int getBackgroundAssetsSize(LevelAssetPrototype prototype) {
        ResoursePrototype[] prototypes = getBackgroundAssets(prototype);
        return prototypes == null ? 0 : prototypes.length;
    }

    private int getForegroundAssetsSize(LevelAssetPrototype prototype) {
        ResoursePrototype[] prototypes = getForegroundAssets(prototype);
        return prototypes == null ? 0 : prototypes.length;
    }

    protected void loadBackgroundElements() {
        LevelAssetPrototype prototype = level.getPrototype().assets;
        int size = getBackgroundAssetsSize(prototype);
        Log.info("Level " + level.getIndex() + " require " + size + " background textures");
        if (size == 0) return;

        for (ResoursePrototype resource : getBackgroundAssets(prototype)) {
            game.getResources().loadTexture(resource.file);
        }

        game.getResources().getManager().finishLoading();

        for (ResoursePrototype resource : getBackgroundAssets(prototype)) {
            Texture texture = game.getResources().getRegisteredTexture(resource.file);
            Image image = new BackgroundImage(texture, resource);
            stageHolder.getBattleStage().addActor(image);
            Log.info("Added background texture: " + resource.file);
        }
    }

    protected void loadForegroundElements() {
        LevelAssetPrototype prototype = level.getPrototype().assets;
        int size = getForegroundAssetsSize(prototype);

        Log.info("Level " + level.getIndex() + " require " + size + " foreground textures");

        if (size == 0) return;

        Resources resources = game.getResources();

        for (ResoursePrototype resource : getForegroundAssets(prototype)) {
            resources.loadTexture(resource.file);
        }

        game.getResources().getManager().finishLoading();

        for (ResoursePrototype resource : getForegroundAssets(prototype)) {
            Texture texture = resources.getRegisteredTexture(resource.file);
            Image image = new ForegroundImage(texture, resource);
            stageHolder.getBattleStage().addActor(image);
            Log.info("Added foreground texture: " + resource.file);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.input.setInputProcessor(null);
        renderer.dispose();
        level.dispose();
    }

    public void disableCameraProcessor() {
        inputMultiplexer.removeProcessor(cameraListener);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void enableCameraListener() {
        for (InputProcessor processor : inputMultiplexer.getProcessors()) {
            if (processor == cameraListener) {
                Log.warn("CameraListener input processor is already enabled");
                return;
            }
        }
        inputMultiplexer.addProcessor(cameraListener);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void disableBattleStageProcessor() {
        inputMultiplexer.removeProcessor(stageHolder.getBattleStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void enableBattleStageProcessor() {
        for (InputProcessor processor : inputMultiplexer.getProcessors()) {
            if (processor == stageHolder.getBattleStage()) {
                Log.warn("BattleStage input processor is already enabled");
                return;
            }
        }
        inputMultiplexer.addProcessor(stageHolder.getBattleStage());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}

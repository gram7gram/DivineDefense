package ua.gram.controller.listener;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.GameBattleStage;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.Level;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.map.Map;
import ua.gram.model.prototype.MapPrototype;
import ua.gram.view.screen.ErrorScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopInputListener extends ClickListener {

    private final DDGame game;
    private final GameUIStage stage_ui;
    private final GameBattleStage stage_battle;
    private final TiledMapTileLayer layer;
    private final String type;
    private final TowerShop shop;
    private TiledMapTileLayer layer_object;
    private Tower tower;
    private MapPrototype prototype;

    public TowerShopInputListener(DDGame game,
                                  TowerShop shop,
                                  String type) {
        this.game = game;
        this.shop = shop;
        this.type = type;
        this.stage_ui = shop.getUiStage();
        this.stage_battle = shop.getStageBattle();
        Map map = stage_battle.getLevel().getMap();
        MapLayers layers = map.getTiledMap().getLayers();
        this.prototype = map.getPrototype();
        this.layer = (TiledMapTileLayer) layers.get(prototype.layer);
        this.layer_object = (TiledMapTileLayer) layers.get(prototype.mapObject);
    }

    /**
     * Put the TowerState under the cursor.
     */
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        try {
            tower = shop.preorder(type, X, Y);
            return true;
        } catch (Exception e) {
            Log.exc("Could not get Tower from Shop: " + type, e);
            return false;
        }
    }

    /**
     * Move the TowerState across the map.
     */
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        if (canBeBuild(X, Y)) {
            tower.setPosition(X, Y);
            tower.toFront();
        }
    }

    /**
     * Puts the TowerState on the map if building is allowed.
     * As soon as towerGroup is put on map, display building animation for 1.5 sec.
     * Then switch to idle animation and activate towerGroup.
     */
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        if (canBeBuild(X, Y)) {
            shop.buy(tower, X, Y);
            Level level = stage_ui.getLevel();
            try {
                if (level.getWave() == null || (!level.getWave().isStarted && !level.isCleared)) {
                    stage_ui.getLevel().nextWave();
                    stage_ui.getGameUIGroup().getCounterBut().setVisible(false);
                }
            } catch (Exception e) {
                game.setScreen(new ErrorScreen(game,
                        "Unappropriate wave [" + stage_ui.getLevel().getCurrentWave()
                                + "] in level " + stage_ui.getLevel().getCurrentLevel(), e));
            }
        } else {
            shop.refund(tower);
        }
    }

    private boolean canBeBuild(float X, float Y) {
        TiledMapTileLayer.Cell cell1 = layer.getCell((int) (X / DDGame.TILE_HEIGHT), (int) (Y / DDGame.TILE_HEIGHT));
        MapProperties prop1 = cell1.getTile().getProperties();
        if (layer_object != null) {
            TiledMapTileLayer.Cell cell2 = layer_object.getCell(
                    (int) (X / DDGame.TILE_HEIGHT),
                    (int) (Y / DDGame.TILE_HEIGHT));
            if (cell2 != null) {
                MapProperties prop2 = cell2.getTile().getProperties();
                if (prop2 != null) {
                    return !prop1.containsKey(prototype.walkableProperty)
                            && !prop2.containsKey(prototype.blockedProperty)
                            && stage_battle.isPositionEmpty(X, Y);
                }
            }
        }
        return !prop1.containsKey(prototype.walkableProperty)
                && !prop1.containsKey(prototype.blockedProperty)
                && stage_battle.isPositionEmpty(X, Y);

    }
}

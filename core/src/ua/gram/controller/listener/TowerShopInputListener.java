package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.UIStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.controller.voter.TiledMapVoter;
import ua.gram.model.Level;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.TowerGroup;
import ua.gram.view.screen.ErrorScreen;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopInputListener extends ClickListener {

    private final DDGame game;
    private final UIStage uiStage;
    private final BattleStage battleStage;
    private final String type;
    private final TowerShop shop;
    private final TiledMapVoter voter;
    private TowerGroup towerGroup;

    public TowerShopInputListener(DDGame game, TowerShop shop, String type) {
        this.game = game;
        this.shop = shop;
        this.type = type;
        this.uiStage = shop.getUiStage();
        this.battleStage = shop.getBattleStage();
        voter = new TiledMapVoter(battleStage.getLevel().getMap());
    }

    /**
     * Put the Tower under the cursor
     */
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        try {
            towerGroup = shop.preorder(type, X, Y);
            return true;
        } catch (Exception e) {
            Log.exc("Could not get Tower from Shop: " + type, e);
            return false;
        }
    }

    /** Move the Tower across the map */
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        boolean canBeBuild = voter.canBeBuildOnAllLayers(X, Y);
        boolean isFree = battleStage.isPositionEmpty(X, Y);
        if (canBeBuild && isFree
                && !(Float.compare(X, towerGroup.getX()) == 0
                && Float.compare(Y, towerGroup.getY()) == 0)) {
            Tower tower = towerGroup.getRootActor();
            tower.setPosition(X, Y + 40);
            tower.toFront();
            shop.getMarker().setPosition(X, Y);
            shop.getMarker().setVisible(true);
        }
    }

    /**
     * Puts the Tower on the map if building is allowed.
     * As soon as towerGroup is put on map, display building animation for 1.5 sec.
     * Then switch to idle animation and activate towerGroup.
     */
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        boolean canBeBuild = voter.canBeBuildOnAllLayers(X, Y);
        boolean isFree = battleStage.isPositionEmpty(X, Y);
        if (canBeBuild && isFree) {
            shop.buy(towerGroup, X, Y);
            Level level = uiStage.getLevel();
            try {
                if (level.getWave() == null || (!level.getWave().isStarted && !level.isCleared)) {
                    uiStage.getLevel().nextWave();
                    uiStage.getGameUIGroup().getCounterBut().setVisible(false);
                }
            } catch (Exception e) {
                game.setScreen(new ErrorScreen(game,
                        "Unappropriate wave [" + uiStage.getLevel().getCurrentWaveIndex()
                                + "] in level " + uiStage.getLevel().getCurrentLevel(), e));
            }
        } else {
            shop.refund(towerGroup);
        }
        shop.getMarker().reset();
    }
}

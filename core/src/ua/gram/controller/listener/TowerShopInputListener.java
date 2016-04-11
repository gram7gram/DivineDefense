package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.gram.DDGame;
import ua.gram.controller.stage.BattleStage;
import ua.gram.controller.stage.UIStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.controller.voter.TiledMapVoter;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.TowerGroup;
import ua.gram.model.level.Level;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopInputListener extends ClickListener {

    private final UIStage uiStage;
    private final BattleStage battleStage;
    private final String type;
    private final TowerShop shop;
    private final TiledMapVoter voter;
    private TowerGroup towerGroup;

    public TowerShopInputListener(TowerShop shop, String type) {
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

    /**
     * Move the Tower across the map
     */
    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        if (!isEqual(X, Y, towerGroup)) {
            boolean canBeBuild = voter.canBeBuildOnAllLayers(X, Y);
            boolean isFree = battleStage.isPositionEmpty(X, Y);
            if (canBeBuild && isFree) {
                Tower tower = towerGroup.getRootActor();
                tower.setPosition(X, Y + 40);
                tower.toFront();
                shop.getMarker().setPosition(X, Y);
                shop.getMarker().setVisible(true);
            }
        }
    }

    private boolean isEqual(float x, float y, TowerGroup group) {
        return Float.compare(x, group.getX()) == 0
                && Float.compare(y, group.getY()) == 0;
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
            if (canStartNextWave(uiStage.getLevel())) {
                uiStage.getLevel().nextWave();
                uiStage.getCounter().setVisible(false);
            }
        } else {
            shop.refund(towerGroup);
        }
        shop.getMarker().reset();
    }

    private boolean canStartNextWave(Level level) {
        return level.getWave() == null || (!level.getWave().isStarted && !level.isCleared);
    }
}

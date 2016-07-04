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
        voter = battleStage.getLevel().getMap().getVoter();
    }

    /**
     * Put the Tower under the cursor
     */
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        event.stop();
        try {
            towerGroup = shop.preorder(type, X, Y);
            uiStage.disableNonUiProcessors();
            return true;
        } catch (Exception e) {
            Log.exc("Could not get Tower from Shop: " + type, e);
            return false;
        }
    }

    /** Move the Tower across the map */
    @Override
    public void touchDragged(InputEvent event, float eventX, float eventY, int pointer) {
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        event.stop();
        if (!isEqual(X, Y, towerGroup)) {

            int xIndex = (int) (X / DDGame.TILE_HEIGHT);
            int yIndex = (int) (Y / DDGame.TILE_HEIGHT);

            boolean canBeHovered = voter.isHoverable(xIndex, yIndex);
            boolean isFree = battleStage.isPositionEmpty(X, Y);
            if (isFree && canBeHovered) {
                Tower tower = towerGroup.getRootActor();
                tower.setPosition(X, Y + 40);
                tower.toFront();
                shop.getMarker().setPosition(X, Y);
                shop.getMarker().setVisible(true);
            }
        }
    }

    /**
     * Puts the Tower on the map if building is allowed.
     * As soon as towerGroup is put on map, display building animation for 1.5 sec.
     * Then switch to idle animation and activate tower.
     */
    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        event.stop();
        float X = event.getStageX() - event.getStageX() % DDGame.TILE_HEIGHT;
        float Y = event.getStageY() - event.getStageY() % DDGame.TILE_HEIGHT;
        int xIndex = (int) (X / DDGame.TILE_HEIGHT);
        int yIndex = (int) (Y / DDGame.TILE_HEIGHT);

        boolean canBeBuild = voter.isBuildable(xIndex, yIndex);
        boolean isFree = battleStage.isPositionEmpty(X, Y);

        if (isFree && canBeBuild) {

            shop.buy(towerGroup, X, Y);

            if (canStartNextWave(uiStage.getLevel())) {
                uiStage.getLevel().nextWave();
                uiStage.getCounter().setVisible(false);
            }
        } else {
            shop.refund(towerGroup);
        }

        shop.getMarker().reset();
        uiStage.enableNonUiProcessors();
    }

    private boolean isEqual(float x, float y, TowerGroup group) {
        return Float.compare(x, group.getX()) == 0
                && Float.compare(y, group.getY()) == 0;
    }

    private boolean canStartNextWave(Level level) {
        return level.getWave() == null || (!level.getWave().isStarted() && !level.isCleared());
    }
}

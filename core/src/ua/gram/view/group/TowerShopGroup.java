package ua.gram.view.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ua.gram.DDGame;
import ua.gram.controller.listener.TowerShopInputListener;
import ua.gram.controller.stage.GameUIStage;
import ua.gram.controller.tower.TowerShop;
import ua.gram.model.actor.tower.TowerCannon;
import ua.gram.model.actor.tower.TowerPrimary;
import ua.gram.model.actor.tower.TowerSpecial;
import ua.gram.model.actor.tower.TowerStun;
import ua.gram.view.AbstractGroup;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TowerShopGroup extends AbstractGroup {

    private final DDGame game;
    private final Label towerPrimaryLabel;
    private final Label towerCannonLabel;
    private final Label towerStunLabel;
    private final Label towerSpecialLabel;
    private final Button towerPrimaryBut;
    private final Button towerStunBut;
    private final Button towerSpecialBut;
    private final Button towerCannonBut;

    public TowerShopGroup(DDGame game, GameUIStage stage, TowerShop shop) {
        super();
        this.game = game;

        towerPrimaryBut = new Button(game.getSkin(), "tower-primary");
        towerPrimaryBut.setVisible(true);
        towerPrimaryBut.setSize(butHeight, butHeight);
        towerPrimaryBut.setPosition(DDGame.WORLD_WIDTH - butHeight * 4 - gap * 4, gap);

        towerCannonBut = new Button(game.getSkin(), "tower-cannon");
        towerCannonBut.setVisible(true);
        towerCannonBut.setSize(butHeight, butHeight);
        towerCannonBut.setPosition(DDGame.WORLD_WIDTH - butHeight * 3 - gap * 3, gap);

        towerStunBut = new Button(game.getSkin(), "tower-stun");
        towerStunBut.setVisible(true);
        towerStunBut.setSize(butHeight, butHeight);
        towerStunBut.setPosition(DDGame.WORLD_WIDTH - butHeight * 2 - gap * 2, gap);

        towerSpecialBut = new Button(game.getSkin(), "tower-special");
        towerSpecialBut.setVisible(true);
        towerSpecialBut.setSize(butHeight, butHeight);
        towerSpecialBut.setPosition(DDGame.WORLD_WIDTH - butHeight - gap, gap);

        towerPrimaryLabel = new Label("" + shop.getPoolPrimary().obtain().getCost(), game.getSkin(), "16_tinted");
        towerPrimaryLabel.setVisible(true);
        towerPrimaryLabel.setPosition(
                towerPrimaryBut.getX() + towerPrimaryBut.getWidth() - towerPrimaryLabel.getWidth(),
                towerPrimaryBut.getY()
        );
        towerCannonLabel = new Label("" + shop.getPoolCannon().obtain().getCost(), game.getSkin(), "16_tinted");
        towerCannonLabel.setVisible(true);
        towerCannonLabel.setPosition(
                towerCannonBut.getX() + towerCannonBut.getWidth() - towerCannonLabel.getWidth(),
                towerCannonBut.getY()
        );

        towerStunLabel = new Label("" + shop.getPoolStun().obtain().getCost(), game.getSkin(), "16_tinted");
        towerStunLabel.setVisible(true);
        towerStunLabel.setPosition(
                towerStunBut.getX() + towerStunBut.getWidth() - towerStunLabel.getWidth(),
                towerStunBut.getY()
        );

        towerSpecialLabel = new Label("" + shop.getPoolSpecial().obtain().getCost(), game.getSkin(), "16_tinted");
        towerSpecialLabel.setVisible(true);
        towerSpecialLabel.setPosition(
                towerSpecialBut.getX() + towerSpecialBut.getWidth() - towerSpecialLabel.getWidth(),
                towerSpecialBut.getY()
        );

        towerPrimaryBut.addListener(new TowerShopInputListener(shop, stage, TowerPrimary.class));
        towerCannonBut.addListener(new TowerShopInputListener(shop, stage, TowerCannon.class));
        towerStunBut.addListener(new TowerShopInputListener(shop, stage, TowerStun.class));
        towerSpecialBut.addListener(new TowerShopInputListener(shop, stage, TowerSpecial.class));

        this.addActor(towerPrimaryBut);
        this.addActor(towerPrimaryLabel);
        this.addActor(towerCannonBut);
        this.addActor(towerCannonLabel);
        this.addActor(towerStunBut);
        this.addActor(towerStunLabel);
        this.addActor(towerSpecialBut);
        this.addActor(towerSpecialLabel);
        Gdx.app.log("INFO", "TowerShopGroup is OK");
    }

    /**
     * Updates tower buttons. You will not be able to press the button, if you do not have enough coins.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            int money = game.getPlayer().getCoins();
            int primaryCost = Integer.parseInt(towerPrimaryLabel.getText().toString());
            int cannonCost = Integer.parseInt(towerCannonLabel.getText().toString());
            int stunCost = Integer.parseInt(towerStunLabel.getText().toString());
            int specialCost = Integer.parseInt(towerSpecialLabel.getText().toString());
            towerPrimaryBut.setTouchable(money < primaryCost ? Touchable.disabled : Touchable.enabled);
            towerCannonBut.setTouchable(money < cannonCost ? Touchable.disabled : Touchable.enabled);
            towerStunBut.setTouchable(money < stunCost ? Touchable.disabled : Touchable.enabled);
            towerSpecialBut.setTouchable(money < specialCost ? Touchable.disabled : Touchable.enabled);
            towerPrimaryBut.setDisabled(money < primaryCost);
            towerCannonBut.setDisabled(money < cannonCost);
            towerStunBut.setDisabled(money < stunCost);
            towerSpecialBut.setDisabled(money < specialCost);
        }
    }

}

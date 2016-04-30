package ua.gram.model.window;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import ua.gram.DDGame;
import ua.gram.controller.listener.NextLevelClickListener;
import ua.gram.model.Resetable;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.level.Level;
import ua.gram.model.prototype.ui.window.VictoryWindowPrototype;
import ua.gram.model.prototype.ui.window.WindowPrototype;
import ua.gram.utils.Log;

/**
 * NOTE Add social integration?
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class VictoryWindow extends AbstractWindow implements Resetable {

    private final CustomLabel reward;
    private final DDGame game;
    private final VictoryWindowPrototype prototype;

    public VictoryWindow(final DDGame game, final Level level, WindowPrototype proto) {
        super(game, proto);

        if (!(proto instanceof VictoryWindowPrototype))
            throw new IllegalArgumentException("Prototype is not instance of VictoryWindowPrototype");

        this.game = game;
        prototype = (VictoryWindowPrototype) proto;

        Skin skin = game.getResources().getSkin();

        TextureRegion region = skin.getRegion(prototype.titleRegion);
        if (region == null)
            throw new NullPointerException("Missing texture region: " + prototype.titleRegion);

        Image victoryBanner = new Image(region);
        victoryBanner.setSize(region.getRegionWidth(), region.getRegionHeight());

        Button nextLevel = new Button(skin, prototype.buttonTextStyle);
        nextLevel.setVisible(true);
        nextLevel.addListener(new NextLevelClickListener(game, level));

        reward = new CustomLabel("", skin, prototype.rewardTextStyle);
        reward.setVisible(true);
        reward.setAlignment(Align.center);

        add(victoryBanner).height(120).expandX().row();
        add(reward).height(40).expand().row();
        add(nextLevel).expandX().padTop(20)
                .width(120)
                .height(120);

        resetObject();

        Log.info("VictoryWindow is OK");
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible)
            resetObject();
    }

    @Override
    public void resetObject() {
        if (reward != null) {
            int gemsReward = prototype.getReward();
            String text;
            if (gemsReward == 0) {
                text = "Unlucky...\nNo reward for now";
            } else {
                text = "Reward: +" + gemsReward + " gems";
                game.getPlayer().addGems(gemsReward);
            }
            reward.updateText(text);
            Log.info("Reward for victory: " + gemsReward + " gems");
        }
    }
}

package ua.gram.model.group;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import ua.gram.DDGame;
import ua.gram.model.actor.misc.CustomLabel;
import ua.gram.model.player.Player;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PlayerDataTable extends Table {

    public PlayerDataTable(DDGame game) {
        super(game.getResources().getSkin());
        Player player = game.getPlayer();
        Skin skin = game.getResources().getSkin();
        CustomLabel coins = new CustomLabel("COINS: " + player.getCoins(), skin, "small_tinted");
        CustomLabel gems = new CustomLabel("GEMS: " + player.getGems(), skin, "small_tinted");

        this.add(coins).width(180).center();
        this.add(gems).width(180).center();
    }

}

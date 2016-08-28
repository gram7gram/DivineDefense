package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.DDGame;
import ua.gram.controller.event.CoinSpentEvent;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CoinSpentListener extends GameEvent {

    private final static String NAME = "COIN_SPENT";

    public CoinSpentListener(DDGame game) {
        super(game);
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof CoinSpentEvent)) return false;

        CoinSpentEvent coinEvent = (CoinSpentEvent) event;

        int amount = coinEvent.getAmount();

        game.getPlayer().chargeCoins(amount);

        Log.info("Player spends " + amount + " coins");

        String sound = game.getPrototype().player.getSoundByState(NAME);

        game.getAudioManager().playSound(sound);

        return true;
    }
}

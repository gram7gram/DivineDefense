package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.DDGame;
import ua.gram.controller.event.CoinEarnedEvent;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class CoinEarnedListener extends GameEvent {

    private final static String NAME = "COIN_EARNED";

    public CoinEarnedListener(DDGame game) {
        super(game);
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof CoinEarnedEvent)) return false;

        CoinEarnedEvent coinEvent = (CoinEarnedEvent) event;

        int amount = coinEvent.getAmount();

        game.getPlayer().addCoins(amount);

        Log.info("Player got " + amount + " coins");

        String sound = game.getPrototype().player.getSoundByState(NAME);

        game.getAudioManager().playSound(sound);

        return true;
    }
}

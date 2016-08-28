package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.DDGame;
import ua.gram.controller.event.GemEarnedEvent;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GemEarnedListener extends GameEvent {

    private final static String NAME = "GEM_EARNED";

    public GemEarnedListener(DDGame game) {
        super(game);
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof GemEarnedEvent)) return false;

        GemEarnedEvent gemEvent = (GemEarnedEvent) event;

        int amount = gemEvent.getAmount();

        game.getPlayer().addGems(amount);

        Log.info("Player got " + amount + " gems");

        String sound = game.getPrototype().player.getSoundByState(NAME);

        game.getAudioManager().playSound(sound);

        return true;
    }
}

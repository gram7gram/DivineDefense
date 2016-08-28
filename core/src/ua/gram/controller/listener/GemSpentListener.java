package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.DDGame;
import ua.gram.controller.event.GemSpentEvent;
import ua.gram.model.player.Player;
import ua.gram.utils.Log;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GemSpentListener extends GameEvent {

    private final static String NAME = "GEM_SPENT";

    public GemSpentListener(DDGame game) {
        super(game);
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof GemSpentEvent)) return false;

        GemSpentEvent gemEvent = (GemSpentEvent) event;

        Player player = game.getPlayer();

        int amount = gemEvent.getAmount();

        player.chargeGems(amount);

        Log.info("Player spends " + amount + " gems");

        String sound = game.getPrototype().player.getSoundByState(NAME);

        game.getAudioManager().playSound(sound);

        return true;
    }
}

package ua.gram.controller.listener;

import com.badlogic.gdx.scenes.scene2d.Event;

import ua.gram.DDGame;
import ua.gram.controller.event.PlayerDamagedEvent;
import ua.gram.model.player.Player;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class HealthDecreasedListener extends GameEvent {

    private final static String NAME = "HEALTH_DECREASED";

    public HealthDecreasedListener(DDGame game) {
        super(game);
    }

    @Override
    public boolean handle(Event event) {
        if (!(event instanceof PlayerDamagedEvent)) return false;

        PlayerDamagedEvent damagedEvent = (PlayerDamagedEvent) event;

        Player player = game.getPlayer();

        player.decreaseHealth(damagedEvent.getDamage());

        String sound = game.getPrototype().player.getSoundByState(NAME);

        game.getAudioManager().playSound(sound);

        return false;
    }
}

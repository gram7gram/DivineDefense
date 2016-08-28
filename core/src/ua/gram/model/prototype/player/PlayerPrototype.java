package ua.gram.model.prototype.player;

import ua.gram.model.prototype.Prototype;
import ua.gram.model.prototype.SoundStatePrototype;
import ua.gram.model.prototype.progress.ProgressPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public final class PlayerPrototype extends Prototype {
    public long id;
    public int health;
    public int coins;
    public int gems;
    public String faction;
    public ProgressPrototype progress;
    public PlayerPreferences preferences;
    public SoundStatePrototype[] sounds;

    public String getSoundByState(String state) {
        String sound = null;

        for (SoundStatePrototype soundState : sounds) {
            if (soundState.state.equals(state)) {
                sound = soundState.sound;
                break;
            }
        }

        if (sound == null) {
            throw new NullPointerException("No sound found by tower state: " + state);
        }

        return sound;
    }
}

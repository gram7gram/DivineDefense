package ua.gram.model.prototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class GameActorPrototype extends Prototype {
    public String type;
    public String name;
    public float height;
    public float width;
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

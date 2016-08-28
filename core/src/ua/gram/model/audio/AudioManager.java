package ua.gram.model.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ua.gram.DDGame;
import ua.gram.model.prototype.AudioPreferencesPrototype;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class AudioManager {

    private final Resources resources;
    private final AudioPreferencesPrototype prototype;
    private final Map<String, Long> activeSounds;
    private final Set<String> activeMusic;

    public AudioManager(DDGame game) {
        resources = game.getResources();
        prototype = game.getPlayerPreferences().audio;
        activeSounds = new HashMap<>();
        activeMusic = new TreeSet<>();
    }

    public void loopMusic(String s) {
        if (!canPlayMusic()) return;

        playMusic(s, true);
    }

    public void playMusic(String s) {
        if (!canPlayMusic()) return;

        playMusic(s, false);
    }

    public void playMusic(String s, boolean loop) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.setLooping(loop);
        music.setVolume(prototype.music.volume);
        music.play();

        synchronized (activeMusic) {
            if (!activeMusic.contains(s)) {
                activeMusic.add(s);
            }
        }
    }

    public void loopSound(String s) {
        if (!canPlaySound()) return;

        playSound(s, true);
    }

    public void playSound(String s) {
        if (!canPlaySound()) return;

        playSound(s, false);
    }

    public void playSound(String s, boolean loop) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        long id = sound.play();
        sound.setLooping(id, loop);
        sound.setVolume(id, prototype.sound.volume);

        synchronized (activeSounds) {
            if (!activeSounds.containsKey(s)) {
                activeSounds.put(s, id);
            }
        }
    }

    public void stopMusic(String s) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.stop();
    }

    public void stopSound(String s) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        sound.stop();
    }

    public void pauseMusic(String s) {
        if (!canPlayMusic()) return;

        Music music = resources.getRegisteredMusic(s);
        music.pause();

        synchronized (activeMusic) {
            if (!activeMusic.contains(s)) {
                throw new NullPointerException("Music \"" + "\" was not found in registry");
            }
            activeMusic.remove(s);
        }
    }

    public void pauseSound(String s) {
        if (!canPlaySound()) return;

        Sound sound = resources.getRegisteredSound(s);
        sound.pause();

        synchronized (activeSounds) {
            if (!activeSounds.containsKey(s)) {
                throw new NullPointerException("Sound \"" + "\" was not found in registry");
            }
            activeSounds.remove(s);
        }
    }

    private boolean canPlayMusic() {
        return prototype.music.state;
    }

    private boolean canPlaySound() {
        return prototype.sound.state;
    }

    public boolean toggleSound() {
        prototype.sound.state = !prototype.sound.state;

        return prototype.sound.state;
    }

    public boolean toggleMusic() {
        prototype.music.state = !prototype.music.state;

        return prototype.music.state;
    }

    public void setMusicVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid music volume: " + volume);
        }

        if (volume != prototype.music.volume) {
            prototype.music.volume = volume;
            musicVolumeChanged();
        }
    }

    public void setSoundVolume(float volume) {
        if (!isValidVolume(volume)) {
            throw new IllegalArgumentException("Invalid sound volume: " + volume);
        }

        if (volume != prototype.sound.volume) {
            prototype.sound.volume = volume;
            soundVolumeChanged();
        }
    }

    public boolean isValidVolume(float number) {
        number = (int) number;
        return number == 0 || number == 1;
    }

    protected void soundVolumeChanged() {
        if (prototype.sound.volume == 0) {
            activeSounds.clear();
            return;
        }

        for (String name : activeSounds.keySet()) {
            long id = activeSounds.get(name);
            Sound sound = resources.getRegisteredSound(name);
            sound.setVolume(id, prototype.sound.volume);
        }
    }

    protected void musicVolumeChanged() {
        if (prototype.music.volume == 0) {
            activeMusic.clear();
            return;
        }

        for (String name : activeMusic) {
            Music music = resources.getRegisteredMusic(name);
            music.setVolume(prototype.music.volume);
        }
    }
}

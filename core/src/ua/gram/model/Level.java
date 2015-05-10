package ua.gram.model;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import ua.gram.DDGame;
import ua.gram.controller.enemy.EnemySpawner;
import ua.gram.model.actor.Enemy;
import ua.gram.model.map.Map;
import ua.gram.view.stage.GameBattleStage;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Level {

    private Map map;
    private DDGame game;
    private EnemySpawner spawner;
    private GameBattleStage stage_battle;
    private final Wave wave;
    public boolean isCleared;
    public int currentLevel;

    /**
     * <pre>
     * Is created using Factory!
     * Holds the Map, Wave and EnemySpawner objects.
     * For successful creation of the Level object, follow the order:
     * 	Level level = new Level(...)
     * 	level.create(...)
     * 	level.createSpawner()
     * </pre>
     */
    public Level(ArrayList<ArrayList<Class<? extends Enemy>>> waves) {
        wave = new Wave(this, waves);
        isCleared = false;
        Gdx.app.log("INFO", "Level obtained waves");
    }

    /**
     * Should be called after receiving the Level object from Factory.
     *
     * @param game
     * @param lvl
     */
    public void create(DDGame game, int lvl) {
        this.game = game;
        currentLevel = lvl;
        map = new Map(game.getResources().getMap(lvl));
        Gdx.app.log("INFO", "Level obtained map");
        Gdx.app.log("INFO", "Level " + lvl + " is OK");
    }

    public void createSpawner() {
        spawner = new EnemySpawner(game, this, stage_battle);
        wave.setSpawner(spawner);
    }

    public void update(float delta) {
        if (wave.getCurrentWave() <= wave.getMaxWaves()) {
            wave.update(delta);
            if (wave.isStarted) {
                spawner.update(delta);
            }
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public Map getMap() {
        return map;
    }

    public int getCurrentWave() {
        return wave.getCurrentWave();
    }

    public int getMaxWaves() {
        return wave.getMaxWaves();
    }

    public Wave getWave() {
        return wave;
    }

    public void setStage(GameBattleStage stage) {
        this.stage_battle = stage;
    }

    public GameBattleStage getStage() {
        return stage_battle;
    }

}

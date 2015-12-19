package ua.gram.controller.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import ua.gram.DDGame;
import ua.gram.controller.Log;
import ua.gram.controller.listener.ToggleTowerControlsListener;
import ua.gram.model.Level;
import ua.gram.model.actor.enemy.Enemy;
import ua.gram.model.actor.misc.Range;
import ua.gram.model.actor.tower.Tower;
import ua.gram.model.group.EnemyGroup;
import ua.gram.model.group.TowerGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains major game objects, like towers and enemies.
 * <p/>
 * TODO Check for occupied cells if tower is build.
 *
 * @author Gram <gram7gram@gmail.com>
 */
public class GameBattleStage extends AbstractStage {

    private final Level level;
    private GameUIStage stage_ui;
    private volatile ArrayList<Group> indexes;
    private Range range;
    private ToggleTowerControlsListener controlsListener;
    private ArrayList<int[]> towerPositions;

    public GameBattleStage(DDGame game, Level level) {
        super(game);
        this.level = level;
        towerPositions = new ArrayList<int[]>();
        indexes = new ArrayList<Group>();
        for (int i = 0; i < DDGame.MAP_HEIGHT; i++) {
            Group group = new Group();
            indexes.add(group);
            this.addActor(group);
        }
        range = new Range(game.getResources());
        range.setVisible(false);
        this.addActor(range);
        this.setDebugAll(DDGame.DEBUG);
        Gdx.app.log("INFO", indexes.size() + " indexes are OK");
        Gdx.app.log("INFO", "BattleStage is OK");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!DDGame.PAUSE) {
            if (level.getStage() == null) {
                level.setStage(this);
                level.createSpawner();
                controlsListener = new ToggleTowerControlsListener(this, stage_ui);
                this.addListener(controlsListener);
            }
            level.update(delta);
        }
    }

    /**
     * Handles storing of indexes for Towers and Enemies on the map,
     * so that they can be rendered in appropriate order.
     *
     * @param newGroup group, which is added on the stage
     */
    public void updateZIndexes(Group newGroup) {
        for (Actor actor : newGroup.getChildren()) {
            if (actor instanceof Enemy || actor instanceof Tower) {
                int index = DDGame.MAP_HEIGHT - Math.abs((int) (actor.getY() / DDGame.TILE_HEIGHT) - 1);
                Group group = indexes.get(index);
                if (group != null) {
                    group.addActor(newGroup);
                    Gdx.app.log("INFO", actor.getClass().getSimpleName() + " added to " + index + " index");
                    break;
                } else {
                    Log.warn(this.getClass().getSimpleName() + " failed to get Group at " + index + " index");
                }
            }
        }

        int count = countLayers();
        Gdx.app.log("INFO", "Stage now has " + count + (count > 1 ? " layers" : " layer"));
    }

    public int countLayers() {
        int count = 0;
        for (Group group : indexes) {
            if (group.hasChildren()) ++count;
        }
        return count;
    }

    /**
     * If there is at least one instance of EnemyGroup in
     * corresponding Group, true is returned.
     *
     * @return true - at least one Enemy
     */
    public boolean hasEnemiesOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    return true;
                }
            }
        }
        Gdx.app.log("INFO", "No enemies on map!");
        return false;
    }

    /**
     * If there is at least one instance of TowerGroup in corresponding Group, true is returned.
     *
     * @return true - at least one Tower
     */
    public boolean hasTowersOnMap() {
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    return true;
                }
            }
        }
        Gdx.app.log("INFO", "No towers on map!");
        return false;
    }

    /**
     * Get Enemies directly from stage and corresponding groups.
     *
     * @return enemies on map
     */
    public ArrayList<Enemy> getEnemiesOnMap() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof EnemyGroup) {
                    for (Actor actor2 : ((EnemyGroup) actor).getChildren()) {
                        if (actor2 instanceof Enemy) {
                            enemies.add((Enemy) actor2);
                        }
                    }
                }
            }
        }
        return enemies;
    }

    /**
     * Get Towers directly from stage and corresponding groups.
     *
     * @return towers on map
     */
    public ArrayList<Tower> getTowersOnMap() {
        ArrayList<Tower> towers = new ArrayList<Tower>();
        for (Group group : indexes) {
            for (Actor actor : group.getChildren()) {
                if (actor instanceof TowerGroup) {
                    for (Actor actor2 : ((TowerGroup) actor).getChildren()) {
                        if (actor2 instanceof Tower) {
                            towers.add((Tower) actor2);
                        }
                    }
                }
            }
        }
        return towers;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isPositionEmpty(float x, float y) {
        for (int[] position : towerPositions) {
            if (position[0] == (int) x / DDGame.TILE_HEIGHT && position[1] == (int) y / DDGame.TILE_HEIGHT) {
                return false;
            }
        }
        return true;
    }

    public Range getRange() {
        return range;
    }

    public ArrayList<Group> getIndexes() {
        return indexes;
    }

    /**
     * Swaps Enemies between Groups on Stage, that represent Z-indexes.
     *
     * @param enemy enemy and it's parent group to swap
     */
    public void updateActorIndex(Enemy enemy) {
        if (enemy.getParent() != null) {
            int index = (int) (DDGame.MAP_HEIGHT - (enemy.getY()) / DDGame.TILE_HEIGHT) - 1;
            if (index != enemy.getParent().getZIndex()) {
                enemy.getParent().remove();
                Group enemyGroup = enemy.getParent();
                try {
                    Group indexGroup = indexes.get(index);
                    if (enemyGroup != null && indexGroup != null) {
                        indexGroup.addActor(enemyGroup);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Gdx.app.error("EXC", "GameBattleStage failed to update indexes"
                            + "\r\n" + Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    public void setUIStage(GameUIStage stage_ui) {
        this.stage_ui = stage_ui;
    }

    public ToggleTowerControlsListener getControlsListener() {
        return controlsListener;
    }

    public void addTowerPosition(Tower tower) {
        towerPositions.add(new int[]{(int) tower.getX() / DDGame.TILE_HEIGHT, (int) tower.getY() / DDGame.TILE_HEIGHT});
    }

    public void removeTowerPosition(Tower tower) {
        towerPositions.remove(new int[]{(int) tower.getX() / DDGame.TILE_HEIGHT, (int) tower.getY() / DDGame.TILE_HEIGHT});
    }
}
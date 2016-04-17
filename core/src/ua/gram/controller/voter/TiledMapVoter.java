package ua.gram.controller.voter;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

import ua.gram.controller.factory.VoterPolicyFactory;
import ua.gram.model.enums.Voter;
import ua.gram.model.map.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TiledMapVoter {

    private final Map map;
    private final VoterPolicy voter;

    public TiledMapVoter(Map map) {
        this.map = map;
        voter = VoterPolicyFactory.create(Voter.Policy.UNANIMOUS);
    }

    public boolean isBuildable(int x, int y) {
        return !isWalkable(x, y) && !isBlocked(x, y);
    }

    public boolean isWalkable(int x, int y) {
        String property = map.getPrototype().walkableProperty;
        return is(x, y, property);
    }

    public boolean isSpawn(int x, int y) {
        String property = map.getPrototype().spawnProperty;
        return is(x, y, property);
    }

    public boolean isBase(int x, int y) {
        String property = map.getPrototype().baseProperty;
        return is(x, y, property);
    }

    public boolean isBlocked(int x, int y) {
        String property = map.getPrototype().blockedProperty;
        return is(x, y, property);
    }

    /**
     * Check all layers whether tile contains provided property
     */
    public boolean is(int x, int y, String property) {
        List<Voter.Value> values = new ArrayList<Voter.Value>(map.getLayers().size());

        for (TiledMapTileLayer layer : map.getLayers()) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);

            if (cell == null) {
                values.add(Voter.Value.NEUTRAL);
                continue;
            }

            TiledMapTile tile = cell.getTile();

            if (tile == null) {
                values.add(Voter.Value.NEUTRAL);
                continue;
            }

            MapProperties properties = tile.getProperties();

            values.add(getVoterValue(properties.containsKey(property)));
        }

        return voter.isGranted(values);
    }

    private Voter.Value getVoterValue(boolean condition) {
        return condition ? Voter.Value.FOR : Voter.Value.AGAINST;
    }
}
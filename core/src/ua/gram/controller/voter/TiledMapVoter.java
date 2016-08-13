package ua.gram.controller.voter;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

import ua.gram.controller.factory.VoterPolicyFactory;
import ua.gram.controller.voter.policy.VoterPolicy;
import ua.gram.model.enums.Voter;
import ua.gram.model.map.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TiledMapVoter implements ua.gram.controller.voter.Voter {

    protected final Map map;
    protected VoterPolicy voter;

    public TiledMapVoter(Map map) {
        this(map, Voter.Policy.UNANIMOUS);
    }

    public TiledMapVoter(Map map, Voter.Policy policy) {
        this.map = map;
        voter = VoterPolicyFactory.create(policy);
    }

    public boolean isHoverable(int x, int y) {
        return !isWalkable(x, y)
                && !isSpawn(x, y, Voter.Policy.AFFIRMATIVE)
                && !isBase(x, y, Voter.Policy.AFFIRMATIVE);
    }

    public boolean isBuildable(int x, int y) {
        return !isBlocked(x, y) && isHoverable(x, y);
    }

    public boolean isWalkable(int x, int y) {
        String property = map.getPrototype().walkableProperty;
        return isGranted(x, y, property);
    }

    public boolean isSpawn(int x, int y, Voter.Policy policy) {
        String property = map.getPrototype().spawnProperty;
        this.voter = VoterPolicyFactory.create(policy);
        return isGranted(x, y, property);
    }

    public boolean isBase(int x, int y, Voter.Policy policy) {
        String property = map.getPrototype().baseProperty;
        this.voter = VoterPolicyFactory.create(policy);
        return isGranted(x, y, property);
    }

    public boolean isBlocked(int x, int y) {
        String property = map.getPrototype().blockedProperty;
        return isGranted(x, y, property);
    }

    /**
     * Check all layers whether tile contains provided property
     */
    public boolean isGranted(int x, int y, String property) {
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

    protected Voter.Value getVoterValue(boolean condition) {
        return condition ? Voter.Value.FOR : Voter.Value.AGAINST;
    }
}
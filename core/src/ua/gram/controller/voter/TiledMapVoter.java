package ua.gram.controller.voter;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

import ua.gram.DDGame;
import ua.gram.model.enums.Voter;
import ua.gram.model.map.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class TiledMapVoter {

    private final Map map;
    private final MapLayers layers;
    private final VoterPolicyInterface voter;

    public TiledMapVoter(Map map) {
        this.map = map;
        layers = map.getTiledMap().getLayers();
        voter = VoterPolicyFactory.create(Voter.Policy.UNANIMOUS);
    }

    public boolean canBeBuildOnAllLayers(float X, float Y) {
        List<Voter.Value> values = new ArrayList<>(layers.getCount());

        for (MapLayer layer : layers) {
            values.add(canBeBuild((TiledMapTileLayer) layer, X, Y));
        }

        return voter.isGranted(values);
    }

    public Voter.Value canBeBuild(TiledMapTileLayer layer, float X, float Y) {
        TiledMapTileLayer.Cell cell = layer.getCell(
                (int) (X / DDGame.TILE_HEIGHT),
                (int) (Y / DDGame.TILE_HEIGHT));

        if (cell == null) return Voter.Value.NEUTRAL;

        MapProperties prop = cell.getTile().getProperties();

        return isPossibleToBuild(prop);
    }

    private Voter.Value isPossibleToBuild(MapProperties properties) {
        return !map.isWalkable(properties) && !map.isBlocked(properties)
                ? Voter.Value.FOR : Voter.Value.AGAINST;
    }
}
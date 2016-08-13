package ua.gram.controller.voter;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

import ua.gram.model.enums.Voter;
import ua.gram.model.map.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class Pseudo3DMapVoter extends TiledMapVoter {

    public Pseudo3DMapVoter(Map map) {
        super(map);
    }

    @Override
    public boolean isGranted(int x, int y, String property) {
        List<Voter.Value> values = new ArrayList<Voter.Value>(map.getLayers().size());

        for (TiledMapTileLayer layer : map.getLayers()) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);

            if (cell == null) {
                continue;
            }

            TiledMapTile tile = cell.getTile();

            if (tile == null) {
                continue;
            }

            MapProperties properties = tile.getProperties();

            values.add(getVoterValue(properties.containsKey(property)));
        }

        if (values.size() > 0) {
            Voter.Value lastValue = values.get(values.size() - 1);

            return lastValue == Voter.Value.FOR;
        } else {
            return false;
        }
    }
}

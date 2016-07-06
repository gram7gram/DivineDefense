package ua.gram.controller.factory;

import ua.gram.controller.voter.Pseudo3DMapVoter;
import ua.gram.controller.voter.TiledMapVoter;
import ua.gram.model.map.Map;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class VoterFactory {

    public static final String TILED_MAP = "TILED_MAP";
    public static final String PSEUDO_3D = "PSEUDO_3D";

    public static TiledMapVoter create(Map map, String type) {
        switch (type) {
            case TILED_MAP:
                return new TiledMapVoter(map);
            case PSEUDO_3D:
                return new Pseudo3DMapVoter(map);
            default:
                throw new IllegalArgumentException("Unknown tiled map voter type");
        }
    }
}

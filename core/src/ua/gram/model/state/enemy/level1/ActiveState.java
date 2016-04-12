package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ActiveState extends Level1State {

    public ActiveState(DDGame game) {
        super(game);
    }

    @Override
    protected Types.EnemyState getType() {
        return null;
    }

}

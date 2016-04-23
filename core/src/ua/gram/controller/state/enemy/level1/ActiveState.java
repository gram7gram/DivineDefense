package ua.gram.controller.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.controller.state.enemy.EnemyStateManager;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class ActiveState extends Level1State {

    public ActiveState(DDGame game, EnemyStateManager manager) {
        super(game, manager);
    }

    @Override
    protected Types.EnemyState getType() {
        return null;
    }

}

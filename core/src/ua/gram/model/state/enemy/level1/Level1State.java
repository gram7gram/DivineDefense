package ua.gram.model.state.enemy.level1;

import ua.gram.DDGame;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class Level1State extends ua.gram.model.state.enemy.EnemyState {

    private Types.EnemyState animationType;

    public Level1State(DDGame game) {
        super(game);
    }

    public Types.EnemyState getAnimationType() {
        return animationType;
    }

    public void setAnimationType(Types.EnemyState animationType) {
        this.animationType = animationType;
    }
}

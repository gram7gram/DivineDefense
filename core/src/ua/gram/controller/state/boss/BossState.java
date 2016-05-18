package ua.gram.controller.state.boss;

import ua.gram.DDGame;
import ua.gram.controller.state.State;
import ua.gram.model.actor.boss.Boss;
import ua.gram.model.enums.Types;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class BossState extends State<Boss> {

    protected final BossStateManager manager;

    public BossState(DDGame game, BossStateManager manager) {
        super(game);
        this.manager = manager;
    }

    protected abstract Types.BossState getType();
}

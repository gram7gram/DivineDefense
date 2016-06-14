package ua.gram.model.prototype.boss;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.model.enums.Types;
import ua.gram.model.prototype.GameActorPrototype;
import ua.gram.model.prototype.SpineStatePrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossPrototype extends GameActorPrototype {
    public int level;
    public Vector2 position;
    public float scale;
    public SpineStatePrototype[] states;

    public SpineStatePrototype getSpineStatePrototype(Types.BossState bossState) {
        if (bossState == null) {
            throw new GdxRuntimeException("Could not find SpineStatePrototype for NULL type");
        }

        for (SpineStatePrototype state : states) {
            if (state.name.equals(bossState.name()))
                return state;
        }

        throw new GdxRuntimeException("Missing SpineStatePrototype with name: " + bossState.name());
    }

}

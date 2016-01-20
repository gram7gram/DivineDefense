package ua.gram.model.shader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

import ua.gram.model.actor.GameActor;
import ua.gram.model.prototype.ShaderPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class OutlineShader extends AbstractShader {

    public OutlineShader(ShaderPrototype prototype) {
        super(prototype);
    }

    @Override
    public void draw(TextureRegion region, GameActor target, Batch batch) {
        batch.end();

        ShaderProgram previousShader = batch.getShader();
        batch.setShader(shader);

        float animationFrames = target.getAnimator().getAnimation().getKeyFrames().length;

        shader.begin();
        shader.setUniformf("u_viewportInverse", new Vector2(
                1f / (region.getRegionWidth() * animationFrames),
                1f / region.getRegionHeight()
        ));
        shader.setUniformf("u_offset", prototype.offset);
        shader.setUniformf("u_step", prototype.step);
        shader.setUniformf("u_color", prototype.color);
        shader.end();

        batch.begin();
        batch.draw(region,
                target.getX(), target.getY(),
                target.getWidth(), target.getHeight());
        batch.end();

        batch.setShader(previousShader);

        batch.begin();
    }
}

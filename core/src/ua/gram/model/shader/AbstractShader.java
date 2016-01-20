package ua.gram.model.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import ua.gram.model.actor.GameActor;
import ua.gram.model.prototype.ShaderPrototype;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public abstract class AbstractShader {

    protected final ShaderProgram shader;
    protected final ShaderPrototype prototype;

    public AbstractShader(ShaderPrototype prototype) {
        this.prototype = prototype;
        FileHandle vertexShader = Gdx.files.internal(prototype.vertexFile);
        FileHandle fragmentShader = Gdx.files.internal(prototype.fragmentFile);
        shader = new ShaderProgram(vertexShader.readString(), fragmentShader.readString());
        if (!shader.isCompiled())
            throw new GdxRuntimeException("Could not compile shader " + prototype.name
                    + " " + shader.getLog());
    }

    public abstract void draw(TextureRegion region, GameActor target, Batch batch);
}

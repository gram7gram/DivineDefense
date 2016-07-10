package ua.gram.model.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ua.gram.DDGame;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class PositionMarker extends Actor {

    public static final String SUCCESS = "success";
    public static final String DANGER = "danger";

    private final Animation animation;
    private float stateTime;
    private TextureRegion currentFrame;
    private String markerStyle;

    public PositionMarker(Skin skin) {
        String region = "position-marker";

        TextureRegion texture = skin.getRegion(region);

        if (texture == null)
            throw new NullPointerException("Texture not found: " + region);

        TextureRegion[][] regions = texture.split(DDGame.TILE_HEIGHT, DDGame.TILE_HEIGHT);

        if (regions == null || regions[0] == null)
            throw new NullPointerException("Texture not loaded: " + region);

        this.setSize(DDGame.TILE_HEIGHT, DDGame.TILE_HEIGHT);

        animation = new Animation(1 / 5f, regions[0]);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        markerStyle = SUCCESS;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        toBack();
        if (DDGame.DEBUG) setDebug(DDGame.DEBUG);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!DDGame.PAUSE || currentFrame == null) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if (currentFrame != null) {
            Color initialColor = batch.getColor();
            batch.setColor(getColorByStyle());
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
            batch.setColor(initialColor);
        }
    }

    public void reset() {
        setPosition(0, 0);
        setVisible(false);
        markerStyle = SUCCESS;
    }

    private Color getColorByStyle() {
        switch (markerStyle) {
            case SUCCESS:
                return Color.GREEN;
            case DANGER:
                return Color.RED;
            default:
                throw new IllegalArgumentException("Unknown marker style: " + markerStyle);
        }
    }

    public void setMarkerStyle(String markerStyle) {
        this.markerStyle = markerStyle;
    }
}

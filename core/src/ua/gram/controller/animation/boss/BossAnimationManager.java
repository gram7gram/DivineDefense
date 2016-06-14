package ua.gram.controller.animation.boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

import ua.gram.model.player.Player;
import ua.gram.model.prototype.boss.BossPrototype;
import ua.gram.utils.Log;
import ua.gram.utils.Resources;

/**
 * @author Gram <gram7gram@gmail.com>
 */
public class BossAnimationManager {

    protected final Skeleton skeleton;
    private final Resources resources;
    private final BossPrototype prototype;
    private final SkeletonRenderer<Batch> skeletRenderer;
    private final AnimationStateData stateData;
    private final AnimationState skeletonState;
    private final float animationScale;

    public BossAnimationManager(Resources resources, BossPrototype prototype) {
        this.resources = resources;
        this.prototype = prototype;
        this.animationScale = prototype.scale;

        SkeletonData skeletonData = getSkeleton(prototype.name);

        stateData = new AnimationStateData(skeletonData);
        skeletRenderer = new SkeletonRenderer<>();
        skeletonState = new AnimationState(stateData);
        skeleton = new Skeleton(skeletonData);
        skeleton.setFlip(true, false);
    }

    public void init() {
        stateData.setMix("IDLE", "COMMAND", 0.2f);
        stateData.setMix("COMMAND", "IDLE", 0.2f);
        stateData.setMix("IDLE", "EXCLAMATION", 0.2f);
        stateData.setMix("EXCLAMATION", "IDLE", 0.2f);
        stateData.setMix("COMMAND", "EXCLAMATION", 0);
        stateData.setMix("EXCLAMATION", "COMMAND", 0);
        stateData.setMix("EXCLAMATION", "DEFEAT", 0);
        stateData.setMix("COMMAND", "DEFEAT", 0);
        stateData.setMix("IDLE", "DEFEAT", 0);

        Log.info("AnimationManager for " + prototype.name + " is OK");
    }

    private SkeletonData getSkeleton(String name) {
        String path = "data/spine/bosses/"
                + Player.SYSTEM_FACTION + "/" + name + "/skeleton";
        TextureAtlas atlas = resources.getAtlas(path);
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(animationScale);
        return json.readSkeletonData(Gdx.files.internal(path + ".json"));
    }

    public AnimationState getSkeletonState() {
        return skeletonState;
    }

    public SkeletonRenderer<Batch> getSkeletonRenderer() {
        return skeletRenderer;
    }

    public float getScale() {
        return animationScale;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }
}

package fi.tuni.retkue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * AnimatedActor is Actor that is animated and holds two different states
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class AnimatedActor extends Actor {
    /**
     * Animation animation is the current animation
     */
    Animation animation;

    /**
     * Animation woundedAnimation is the wounded animation
     */
    Animation woundedAnimation;

    /**
     * Animation normalAnimation is the normal animation
     */
    Animation normalAnimation;

    /**
     * float stateTime is the state of the animation
     */
    private float stateTime;

    /**
     * float speed is the speed of the animation
     */
    private float speed;

    /**
     * float width is the width of the animation
     */
    private float width;

    /**
     * float height is the height of the animation
     */
    private float height;

    /**
     * int cols is the cols of the animation sheet
     */
    private int cols;

    /**
     * int rows is the rows of the animation sheet
     */
    private int rows;

    /**
     * TextureRegion currentFrame is the current displayed animation frame
     */
    TextureRegion currentFrame;

    /**
     * Constructor for AnimatedActor
     * @param file filename of the animation sheet
     * @param cols columns in animation sheet
     * @param rows rows in animation sheet
     * @param speed speed of the animation
     * @param width width of the animation
     * @param height height of the animation
     */
    public AnimatedActor(String file, int cols, int rows, float speed, float width, float height) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.cols = cols;
        this.rows = rows;
        Texture animationSheet = Utils.loadTexture(file);
        TextureRegion[][] tmp = TextureRegion.split(
                animationSheet,
                animationSheet.getWidth() / cols,
                animationSheet.getHeight() / rows);
        TextureRegion[] frames = Utils.transformTo1D(tmp, cols, rows);
        normalAnimation = new Animation(speed, frames);
        animation = normalAnimation;
    }

    /**
     * initiateWoundedAnimation generates the woundedAnimation. It uses same parameters as the
     * normal animnation
     * @param file filename of the woundedanimation sheet
     */
    public void initateWoundedAnimation(String file) {
        Texture animationSheet = Utils.loadTexture(file);
        TextureRegion[][] tmp = TextureRegion.split(
                animationSheet,
                animationSheet.getWidth() / cols,
                animationSheet.getHeight() / rows);
        TextureRegion[] frames = Utils.transformTo1D(tmp, cols, rows);
        woundedAnimation = new Animation(speed, frames);
    }

    /**
     * Override for draw to draw our animation
     * @param batch spriteBatch
     * @param parentAlpha parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        currentFrame = (TextureRegion)animation.getKeyFrame(stateTime, true);
        float x = getX();
        float y = getY();
        batch.draw(currentFrame, x, y,  width, height);
    }

    /**
     * Override for act to run our stateTime
     * @param delta deltaTime
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }

    /**
     * Sets the animation to woundedAnimation
     */
    public void setWoundedAnimation() {
        animation = woundedAnimation;
    }

    /**
     * Sets the animation to normalAnimation
     */
    public void setNormalAnimation() {
        animation = normalAnimation;
    }
}
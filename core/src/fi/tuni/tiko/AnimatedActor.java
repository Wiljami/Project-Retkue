package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedActor extends Actor {
    Animation animation;
    private float stateTime;
    private float speed;
    private float width;
    private float height;
    TextureRegion currentFrame;

    public AnimatedActor(String file, int cols, int rows, float speed, float width, float height) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        Texture animationSheet = Utils.loadTexture(file);
        TextureRegion[][] tmp = TextureRegion.split(
                animationSheet,
                animationSheet.getWidth() / cols,
                animationSheet.getHeight() / rows);
        TextureRegion[] frames = Utils.transformTo1D(tmp, cols, rows);
        animation = new Animation(speed, frames);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        currentFrame = (TextureRegion)animation.getKeyFrame(stateTime, true);
        float x = getX();
        float y = getY();
        batch.draw(currentFrame, x, y,  width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }
}
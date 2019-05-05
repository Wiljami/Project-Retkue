package fi.tuni.retkue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Actor that fades in and out of the screen
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class FadeActor extends Image {
    /**
     * FadeActor constructor
     * @param texture Texture of the FadeActor
     */
    public FadeActor(Texture texture) {
        super(texture);
        fadeAction();
    }

    /**
     * fadeAction generates an action that runs alpha of the image from 0 1 and back to 0 and adds
     * this action to the actor
     */
    private void fadeAction() {
        AlphaAction actionFadeOut = new AlphaAction();
        actionFadeOut.setAlpha(0f);
        actionFadeOut.setDuration(2f);
        AlphaAction actionFadeIn = new AlphaAction();
        actionFadeIn.setAlpha(1f);
        actionFadeIn.setDuration(2f);

        SequenceAction fade = new SequenceAction();
        fade.addAction(actionFadeOut);
        fade.addAction(actionFadeIn);

        this.addAction(Actions.forever(fade));
    }

    /**
     * Override of the draw to get our alpha changes work
     * @param batch spriteBatch
     * @param parentAlpha parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        getDrawable().draw(batch, getX(), getY(), getWidth() * getScaleX(),
                getHeight() * getScaleY());
        batch.setColor(color.r, color.g, color.b, 1f);
    }
}
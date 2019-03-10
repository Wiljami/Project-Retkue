package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * HealthBar extends ProgressBar.
 *
 * Its function is to show health % as a green and red bar.
 * TODO: Make this neater for our purposes
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */

public class HealthBar extends ProgressBar{
    /**
     * The style that is shared by all the HealthBars
     */
    private static ProgressBarStyle progressBarStyle;

    /**
     * HealthBar constructor
     * If the progressBarStyle is null, we create it.
     *
     * @param barWidth the width we wish for the bar
     * @param barHeight the height we wish for the bar
     */
    public HealthBar(float barWidth, float barHeight) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        if (progressBarStyle == null) setupStyle();
        this.setStyle(progressBarStyle);
        setWidth(barWidth);
        setHeight(barHeight);
    }

    /**
     * setupStyle() generates a new ProgressBarStyle using pixmaps.
     */
    private void setupStyle() {
        Pixmap pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle = new ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        pixmap = new Pixmap(100, 20, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;
    }
}
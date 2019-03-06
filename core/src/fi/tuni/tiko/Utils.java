package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Utils class contains utility methods used in the project
 *
 * @author Viljami Pietarila
 * @version 2019.0228
 */

//TODO: Make a bundlereader method that makes sure that the game does not crash when there is a missing string in the bundle

public class Utils {
    private final static String DEBUGTEXTURE = "debug-texture.png";

    /**
     * loadTexture attempts to load an image file from the disk. If it fails at it, it then returns
     * an easily spotted debug texture.
     *
     * @param file name of the image file we wish to load
     * @return Texture loaded or debug texture
     */
    public static Texture loadTexture(String file) {
        Texture texture;
        try {
            texture = new Texture(Gdx.files.internal(file));
        } catch (Exception e) {
            texture = new Texture(Gdx.files.internal(DEBUGTEXTURE));
        }
        return texture;
    }

    /**
     * loadButtonImage creates a TextureRegionDrawable from an image file on the disk.
     * @param file filename of the image
     * @param width width of the button image
     * @param height height of the button image
     * @return image TextureRegionDrawable
     */
    public static TextureRegionDrawable loadButtonImage(String file, int width, int height) {
        TextureRegionDrawable image = new TextureRegionDrawable(loadTexture(file));
        image.setMinWidth(width);
        image.setMinHeight(height);
        return image;
    }
}
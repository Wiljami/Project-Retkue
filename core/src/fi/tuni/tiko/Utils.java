package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Utils class contains utility methods used in the project
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */

public class Utils {
    /**
     * DEBUGTEXTURE is the filename of the image we use when we fail to load a Texture.
     */
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
     * //TODO: Check this functionality. width and height seem to do nothing.
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

    /**
     * readBundle attempts to read the localization Bundle using the key. If it fails to read it,
     * it returns error message as the String
     * @param bundle Bundle to be read
     * @param key Key we're seeking in the bundle
     * @return line matching the key
     */
    public static String readBundle(I18NBundle bundle, String key) {
        String line;
        try {
            line = bundle.get(key);
        } catch (Exception e) {
            line = key + " NOT FOUND";
        }
        return line;
    }
}
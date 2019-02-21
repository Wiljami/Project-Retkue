package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Utils class contains utility
 * @author Viljami Pietarila
 * @version 2019.0221
 */

public class Utils {
    private final static String DEBUGTEXTURE = "debug-texture.png";
    public static Texture loadTexture(String file) {
        Texture texture;
        try {
            texture = new Texture(Gdx.files.internal(file));
        } catch (Exception e) {
            texture = new Texture(Gdx.files.internal(DEBUGTEXTURE));
        }
        return texture;
    }
}
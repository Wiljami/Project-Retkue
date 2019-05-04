package fi.tuni.retkue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utils class contains utility methods used in the project
 *
 * @author Viljami Pietarila
 * @version 2019.0504
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

    /**
     * convertToPixels multiplies an array's values by the height of the screen in pixels
     *
     * The heightArray should have values between 0 and 1 and should represent the percentage of the
     * screen. It is then multipled by the overall height resolution of the screen.
     * @param heightArray array of percentage values
     * @return array pixel sizes
     */
    public static float[] convertToPixels(float[] heightArray) {
        for (int n = 0; n < heightArray.length; n++) {
            heightArray[n] = Main.WORLDPIXELHEIGHT * heightArray[n];
        }
        return heightArray;
    }

    /**
     * Check wether the int is a single digit or not. If single digit, then we add a 0 in front
     * of the String
     * @param number the int we check
     * @return the String we return
     */
    private static String toAddZero(int number) {
        String s;
        if (number < 10) {
            s = "0" + Integer.toString(number);
        } else {
            s = Integer.toString(number);
        }
        return s;
    }

    /**
     * Converts an Integer to a string and adds 0s in front to make it 4 digit String
     * @param id int to convert
     * @return the String result of the conversion
     */
    public static String convertToId(int id) {
        String idString;
        if (id < 10) {
            idString = "00";
            idString += Integer.toString(id);
        } else if (id < 100) {
            idString = "0";
            idString += Integer.toString(id);
        } else {
            idString = Integer.toString(id);
        }
        return idString;
    }

    /**
     * transformTo1D changes a 2D TextureRegion array to a 1D array
     * @param tmp Array to transform
     * @param cols Original columns
     * @param rows Original rows
     * @return resulting 1D TextureRegion array
     */
    public static TextureRegion[] transformTo1D(TextureRegion[][] tmp, int cols, int rows) {
        TextureRegion [] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    /**
     * currentDate() returns a String of the current date and time.
     * @return String of the date and time
     */
    public static String currentDate() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM yyyy HH:mm:ss");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    /**
     * Checks an int array whether it contains a specific int value.
     * @param array Array to search
     * @param target Value to search for
     * @return Boolean whether the value was found or not
     */
    public static boolean intArrayContains(int[] array, int target) {
        for (int n = 0; n < array.length; n++) {
            if (array[n] == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes time in milliseconds and converts it to a String in the format of HH:MM:SS
     * @param time long of the milliseconds
     * @return String of the timeStamp
     */
    public static String convertToTimeStamp (long time) {
        int clock[] = convertMillisToTime(time);
        String timeStamp = toAddZero(clock[0]) + ":"
                + toAddZero(clock[1]) + ":"
                + toAddZero(clock[2]);
        return timeStamp;
    }

    /**
     * converts a long value to three integers in an array.
     * [0] is hours, [1] is minutes, [2] is seconds
     * @param time long of the time in milliseconds
     * @return array of the time
     */
    private static int[] convertMillisToTime(long time) {
        int[] clock = new int[3];
        clock[0] = (int) ((time / (1000*60*60)) / 24);
        clock[1] = (int) ((time / (1000*60)) % 60);
        clock[2] = (int) (time / 1000) % 60;
        return clock;
    }
}
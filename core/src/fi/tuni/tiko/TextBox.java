package fi.tuni.tiko;

/**
 * TextBox is a class for containing information. It has info for text String, it's location and
 * the font to use.
 *
 * TODO: Figure out if we need this anymore
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class TextBox {
    public String text;
    public int x;
    public int y;
    String font;

    public TextBox(String text, int x, int y, String font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
    }

    public TextBox(String text, int x, int y) {
        this(text, x, y, "defaultFont");
    }

    @Override
    public String toString() {
        return "TextBox{" +
                "text='" + text + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", font=" + font +
                '}';
    }
}
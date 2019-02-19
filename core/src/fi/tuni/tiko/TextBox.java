package fi.tuni.tiko;

/**
 * TextBox is a class for containing information. It has info for text String, it's location and
 * the font to use.
 *
 * @author Viljami Pietarila
 * @version 2019.0219
 */
public class TextBox {
    public String text;
    public int x;
    public int y;
    public FontType type;
    public enum FontType {
        text, bigText, headline, comicSans, comicHeadline
    }

    public TextBox(String text, int x, int y, FontType type) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public TextBox(String text, int x, int y) {
        this(text, x, y, FontType.text);
    }

    @Override
    public String toString() {
        return "TextBox{" +
                "text='" + text + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }
}
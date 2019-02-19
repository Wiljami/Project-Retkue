package fi.tuni.tiko;

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
}
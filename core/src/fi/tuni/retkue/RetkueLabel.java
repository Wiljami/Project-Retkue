package fi.tuni.retkue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * RetkueLabel is a customized Label for the use of the game. We set text wrapping on and text
 * is centered.
 *
 * @author Viljami Pietarila
 * @version 2019.0504
 */
public class RetkueLabel extends Label {

    /**
     * Constructor for RetkueLabel
     * @param text String of text displayed
     */
    public RetkueLabel(String text) {
        super(text, RetkueDialog.skin);
        setWrap(true);
        setAlignment(1);
    }

    /**
     * Constructor for RetkueLabel
     * @param text String of text displayed
     * @param font String of name of the font to be used
     */
    public RetkueLabel(String text, String font) {
        super(text, RetkueDialog.skin, font, Color.WHITE);
        setWrap(true);
        setAlignment(1);
    }
}

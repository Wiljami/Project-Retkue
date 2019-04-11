package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * RetkueLabel is a customized Label for the use of the game. We set text wrapping on and text
 * is centered.
 */
public class RetkueLabel extends Label {

    public RetkueLabel(String text) {
        this(text, "label");
    }

    public RetkueLabel(String text, String font) {
        super(text, RetkueDialog.skin, font, Color.WHITE);
        setWrap(true);
        setAlignment(1);
    }
}

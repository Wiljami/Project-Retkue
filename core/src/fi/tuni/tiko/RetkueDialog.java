package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class RetkueDialog extends Dialog {

    public RetkueDialog(String title, Skin skin, String windowStyle) {
        super(title, skin, windowStyle);
    }

    /**
     * Override the hide method to get rid of the action ran on hide()
     */
    @Override
    public void hide() {
        hide(null);
    }

    /**
     * Override the show method to get rid of the action ran on show
     *
     * @param stage stage
     * @return this
     */
    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }
}
package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * CreditsPopUp holds the functionality and the UI calls for credits screen
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class CreditsPopUp extends RetkueDialog {
    /**
     * Constructor of CreditsPopUp
     */
    public CreditsPopUp() {
        super("Credits");
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        Image image = new Image(Utils.loadTexture("credits.png"));

        TextButton ok = new TextButton("OK", skin);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
            }
        });

        float ratio = popUpWidth / image.getWidth();

        float height = image.getHeight() * ratio;

        getContentTable().add(image).prefWidth(popUpWidth).prefHeight(height);
        getContentTable().row();
        getContentTable().add(ok).center();
    }
}

package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreditsPopUp extends RetkueDialog {
    public CreditsPopUp(Party party) {
        super("Credits");

        party.setGold(500);
        party.setSteps(5000);

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

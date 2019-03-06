package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    private static String title = "Shop";
    private String text = "This is the Shop pop up. Here you\n here you can buy stuff";

    public ShopPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        Label desc = new Label(text, skin);

        Button optionsButton = new ImageButton(Utils.loadButtonImage("shop button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in Shop");
            }
        });

        getContentTable().add(desc);
        getContentTable().row();
        getContentTable().add(optionsButton);

        button("Return", false);
    }
}

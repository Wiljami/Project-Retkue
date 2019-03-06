package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    private static String title = "";

    public OptionsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        System.out.println(getBundle());
        this.getTitleLabel().setText("blaah");
       // String text = getBundle().get("options_desc");
        Label desc = new Label("", skin);

        Button optionsButton = new ImageButton(Utils.loadButtonImage("options button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in options");
            }
        });

        getContentTable().add(desc);
        getContentTable().row();
        getContentTable().add(optionsButton);

        button(getBundle().get("return"), false);
    }
}
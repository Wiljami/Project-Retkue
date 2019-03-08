package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * OptionsPopUp contains the functionality of the options menu within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0307
 */

public class OptionsPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    private static String title = readLine("options");

    public OptionsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        this.getTitleLabel().setText(readLine("options"));
        String text = readLine("options_desc");
        RetkueLabel desc = new RetkueLabel(text);
        RetkueLabel volume = new RetkueLabel("Volume");

        Button optionsButton = new ImageButton(Utils.loadButtonImage("options button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in options");
            }
        });

        Slider volumeSlider = new Slider(0f, 100f, 1, false, this.getSkin());

        getContentTable().add(desc).prefWidth(250);
        getContentTable().row();
        getContentTable().row();
        getContentTable().add(volume).prefWidth(250);
        getContentTable().row();
        getContentTable().add(volumeSlider);

        button(readLine("return"), false);
    }
}
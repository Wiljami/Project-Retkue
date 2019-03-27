package fi.tuni.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * OptionsPopUp contains the functionality of the options menu within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */

public class OptionsPopUp extends RetkueDialog {
    /**
     * Receives scene in which it is opened
     */
    Scene currentScene;

    /**
     * WindowStyle for the options window
     */
    private static String windowStyle = "dialog";

    /**
     * Title for the options window
     */
    private static String title = readLine("options");

    /**
     * Checkbox for mute
     */
    CheckBox muteBox;

    /**
     * Constructor for OptionsPopUp
     */
    public OptionsPopUp(Scene currentScene) {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
        this.currentScene = currentScene;
    }

    /**
     * createMenu creates various visible UI actors.
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        this.getTitleLabel().setText(readLine("options"));
        String text = readLine("options_desc");
        RetkueLabel desc = new RetkueLabel(text);


        Button optionsButton = new ImageButton(Utils.loadButtonImage("options button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in options");
            }
        });

        muteBox = new CheckBox("Mute Music", getSkin());
        if(currentScene.isMuted) {
            muteBox.setChecked(true);
        }

        getContentTable().add(desc).prefWidth(popUpWidth);
        getContentTable().row();
        getContentTable().row();
        getContentTable().row();
        getContentTable().add(muteBox);

        muteBox.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.graphics.setContinuousRendering(muteBox.isChecked());
                currentScene.setMute(muteBox.isChecked());
                if(muteBox.isChecked()) {
                    currentScene.backgroundMusic.stop();
                } else {
                    currentScene.backgroundMusic.play();
                }
            }
        });

        button(readLine("return"), false);
    }
}
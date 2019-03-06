package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *CustomDialog class will hold the customized dialog options for the entire game. Changing this
 * will then change all the dialogs in the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0306
 */

//TODO: Implement some kind of automatic line change on text
//TODO: Implement localization
//TODO: Make this better
    public class CustomDialog extends Dialog {
    private static String windowStyle = "dialog";
    private static Skin skin;

    /**
     * Constructor that is called when you wish to add an image at the top of the dialog
     * //TODO: Figure out some smart way to resize it to a good size
     * @param text Text presented in the dialog
     * @param title Title of the Dialog
     * @param image Image to be presented on the top
     */
    public CustomDialog(String text, String title, Image image) {
        super(title, skin, windowStyle);
        getContentTable().add(image).prefWidth(image.getPrefWidth()/5).prefHeight(image.getPrefHeight()/5);
        getContentTable().row();
        buildDialog(text);
    }

    /**
     * Constructor that is called when you have text and title only
     * @param text Text presented in the dialog
     * @param title Title of the dialog
     */
    public CustomDialog(String text, String title) {
        super(title, skin, windowStyle);
        buildDialog(text);
    }

    /**
     * Builds the text and button portion of the dialog text
     * @param text Text of the dialog
     */
    private void buildDialog(String text) {
        if (Main.debug) this.debug();
        text(text);

        button("Yes", true); //sends "true" as the result
        button("No", false);  //sends "false" as the result
        remove();
    }

    /**
     * Override the hide action to get rid of the hide action
     */
    @Override
    public void hide() {
        hide(null);
    }

    @Override
    public Dialog show (Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }

    /**
     * Sets the static skin for all the dialogs. Called in main. This needs to be ran at the start
     * of the program for the CustomDialog to work
     * @param skin the skin we wish to use with the CustomDialogs.
     */
    public static void setDialogSkin(Skin skin) {
        CustomDialog.skin = skin;
    }
}
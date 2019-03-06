package fi.tuni.tiko;

import com.badlogic.gdx.Input;
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
//TODO: Make this better
    public class CustomDialog extends Dialog {
    private static String windowStyle = "dialog";
    private static Skin skin;

    public CustomDialog(String text, String title, Image image) {
        super(title, skin, windowStyle);
        getContentTable().add(image).prefWidth(image.getPrefWidth()/5).prefHeight(image.getPrefHeight()/5);
        getContentTable().row();
        buildDialog(text);
    }

    public CustomDialog(String text, String title) {
        super(title, skin, windowStyle);
        buildDialog(text);
    }

    private void buildDialog(String text) {
        if (Main.debug) this.debug();
        text(text);

        button("Yes", true); //sends "true" as the result
        button("No", false);  //sends "false" as the result
        key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        remove();
    }

    @Override
    public void hide() {
        hide(null);
    }

    public static void setDialogSkin(Skin skin) {
        CustomDialog.skin = skin;
    }
}
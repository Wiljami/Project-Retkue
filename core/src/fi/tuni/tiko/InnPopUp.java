package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * InnPopUp contains functionality of the inn within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0307
 */
public class InnPopUp extends RetkueDialog {
    /**
     * windowStyle
     */
    private static String windowStyle = "dialog";

    /**
     * Title of the InnPopUp window
     */
    private static String title = readLine("inn");

    /**
     * Constructor.
     */
    public InnPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu generates different visible UI actors.
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;

        String text = readLine("inn_desc");
        RetkueLabel desc = new RetkueLabel(text);

        Button optionsButton = new ImageButton(Utils.loadButtonImage("inn button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in Inn");
            }
        });

        getContentTable().add(desc).prefWidth(popUpWidth);
        getContentTable().row();
        getContentTable().add(optionsButton);

        button(readLine("return"), false);
    }
}

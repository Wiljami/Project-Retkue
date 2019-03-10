package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * ShopPopUp class holds the functionality and the UI elements of the game's shop.
 *
 * @author Viljami Pietarila
 * @version 2019.0310
 */
public class ShopPopUp extends RetkueDialog {
    /**
     * windowStyle of the shop
     */
    private static String windowStyle = "dialog";

    /**
     * Title name of the shop window
     */
    private static String title = readLine("shop");

    /**
     * ShopPopUp constructor
     */
    public ShopPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu creates various UI actors
     */
    private void createMenu() {
        String text = readLine("shop_desc");
        RetkueLabel desc = new RetkueLabel(text);

        Button optionsButton = new ImageButton(Utils.loadButtonImage("shop button", 50, 50));
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Hello, you clicked a button in Shop");
            }
        });

        getContentTable().add(desc).prefWidth(250);
        getContentTable().row();
        getContentTable().add(optionsButton);

        button(readLine("return"), false);
    }
}
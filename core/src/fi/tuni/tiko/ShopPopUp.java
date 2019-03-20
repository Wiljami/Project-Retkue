package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import javax.rmi.CORBA.Util;

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

    private Table shopItems;

    private float itemWidth;

    /**
     * ShopPopUp constructor
     */
    public ShopPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private RetkueLabel desc;

    /**
     * createMenu creates various UI actors
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*9f/10f;
        float descHeight = Main.WORLDPIXELHEIGHT*2f/5f;
        itemWidth = Main.WORLDPIXELWIDTH/6f;
        String text = readLine("shop_desc");
        desc = new RetkueLabel(text);

        generateItemGrid();

        getContentTable().add(desc).prefWidth(popUpWidth).prefHeight(descHeight).pad(5);
        getContentTable().row();
        getContentTable().add(shopItems);

        button(readLine("return"), false);
    }

    private void generateItemGrid() {
        shopItems = new Table();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                Image item = new Image(Utils.loadTexture("horze_white.png"));
                item.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        desc.setText("I am a horse, lol");
                    }
                });
                float scale = itemWidth / item.getWidth();
                float itemHeight = item.getHeight() * scale;
                shopItems.add(item).prefWidth(itemWidth).prefHeight(itemHeight).pad(1);
            }
            shopItems.row();
        }
    }
}
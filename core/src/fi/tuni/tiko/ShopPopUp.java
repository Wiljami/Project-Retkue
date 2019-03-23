package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    private Table shopItems;

    private float itemWidth;

    private TownInfo townInfo;


    /**
     * ShopPopUp constructor
     */
    public ShopPopUp() {
        super(title, skin, windowStyle);
        this.townInfo = Main.getTownInfo();
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
        for (int i = 0; i < 5; i++) {
            generateItemButton(i);

        }
        shopItems.row();
        for (int i = 5; i < 10; i++) {
            generateItemButton(i);
        }
    }

    private void generateItemButton(int i) {
        Item item = townInfo.findItem(i);
        Image itemButton = new Image(item.getIcon());
        final String description = item.getDescription();
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                desc.setText(description);
            }
        });
        float scale = itemWidth / itemButton.getWidth();
        float itemHeight = itemButton.getHeight() * scale;
        shopItems.add(itemButton).prefWidth(itemWidth).prefHeight(itemHeight).pad(1);
    }
}
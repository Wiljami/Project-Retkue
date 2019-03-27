package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private Item displayedItem;

    private Button buy;

    private Party party;


    /**
     * ShopPopUp constructor
     */
    public ShopPopUp(Party party) {
        super(title, skin, windowStyle);
        this.townInfo = Main.getTownInfo();
        this.party = party;
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

        itemWidth = Main.WORLDPIXELWIDTH/5f;
        String text = readLine("shop_desc");
        desc = new RetkueLabel(text);

        generateItemGrid();

        getContentTable().add(desc).prefWidth(popUpWidth).prefHeight(descHeight).pad(5);
        getContentTable().row();


        buy = new TextButton( readLine("buy"), getSkin());
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (displayedItem.getPrice() > party.getGold()) {
                    System.out.println("You're too poor");
                } else if (!party.isThereInventorySpace()) {
                    System.out.println("Your party inventory is full");
                } else {
                    System.out.println("Here you buy it");
                    party.spendGold(displayedItem.getPrice());
                    party.addItem(displayedItem);
                }
            }
        });

        getContentTable().add(buy).right();
        buy.setVisible(false);
        getContentTable().row();

        getContentTable().add(shopItems);
        getContentTable().row();

        button(readLine("return"), false);
    }

    /**
     * Override the show method to adjust the location of the shop PopUp slightly
     * @param stage stage
     * @return this
     */
    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                Math.round((stage.getHeight() - getHeight()) / 2)+ - 30f);
        return this;
    }

    /**
     * generateItemGrid creates a 2d table to be inserted in the UI. It adds 5 x 2 buttons to it.
     */
    private void generateItemGrid() {
        shopItems = new Table();
        for (int i = 0; i < 4; i++) {
            generateItemButton(i);
        }
        shopItems.row();
        for (int i = 4; i < 8; i++) {
            generateItemButton(i);
        }
    }

    /**
     * generateItemButton generates an item button for the shopItems UI table.
     *
     * It finds the items from townInfo and then generates the button and listener for each.
     * @param i id of the item in the items array within townInfo
     */
    private void generateItemButton(int i) {
        final Item item = townInfo.findItem(i);
        Group itemButton = item.getIcon();
        final String description = item.getDescription();
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String itemText = description + "\nATT: " + item.getAttack();
                itemText += "\nDEF: " +item.getDefense();
                itemText += "\n\n" + readLine("price") + ": " + item.getPrice();
                desc.setText(itemText);
                displayedItem = item;
                buy.setVisible(true);
            }
        });
        float itemHeight = itemWidth;
        System.out.println(itemWidth);
        itemButton.setSize(itemWidth,itemWidth);
        shopItems.add(itemButton).prefWidth(itemWidth).prefHeight(itemHeight).pad(3);
    }
}
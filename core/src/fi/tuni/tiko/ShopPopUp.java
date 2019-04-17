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
     * Title name of the shop window
     */
    private static String title = readLine("shop");

    private Table shopItems;

    private float itemSize;

    private TownInfo townInfo;

    private Item displayedItem;

    private Button buy;

    private Party party;


    /**
     * ShopPopUp constructor
     */
    public ShopPopUp(Party party) {
        super(title);
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

        itemSize = Main.WORLDPIXELWIDTH/5f;
        String text = readLine("shop_desc");
        desc = new RetkueLabel(text);

        shopItems = new Table();
        generateItemGrid();

        getContentTable().add(desc).prefWidth(popUpWidth).prefHeight(descHeight).pad(5);
        getContentTable().row();


        buy = new TextButton( readLine("buy"), getSkin());
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (displayedItem.getPrice() > party.getGold()) {
                    System.out.println("You're too poor");
                    desc.setText(readLine("too_poor"));
                } else if (!party.isThereInventorySpace()) {
                    System.out.println("Your party inventory is full");
                    desc.setText(readLine("inventory_full"));
                } else {
                    System.out.println("Here you buy it");
                    desc.setText(readLine("purchased") + " " + displayedItem.getName());
                    buy.setVisible(false);
                    party.spendGold(displayedItem.getPrice());
                    party.addItem(displayedItem);
                    townInfo.removeItem(displayedItem);
                    generateItemGrid();
                }
            }
        });

        getContentTable().add(buy).right();
        buy.setVisible(false);
        getContentTable().row();

        getContentTable().add(shopItems);
        getContentTable().row();

        TextButton close = new TextButton(readLine("return"), getSkin());
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        getContentTable().row();
        getContentTable().add(close);
    }

    private void closeMe() {
        getGame().saveGame();
        remove();
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
        shopItems.reset();
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
        if (i >= townInfo.noItemsLeft()) {
            generateEmptyButton();
        } else {
            final Item item = townInfo.findItem(i);
            Group itemButton = item.getIcon();
            final String description = item.itemText();
            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String itemText = description;
                    itemText += "\n\n" + readLine("price") + ": " + item.getPrice();
                    desc.setText(itemText);
                    displayedItem = item;
                    buy.setVisible(true);
                }
            });
            float itemHeight = itemSize;
            itemButton.setSize(itemSize, itemSize);
            shopItems.add(itemButton).prefWidth(itemSize).prefHeight(itemHeight).pad(3);
        }
    }

    private void generateEmptyButton() {
        Image emptySlot = new Image(Utils.loadTexture("items/empty_slot.png"));
        shopItems.add(emptySlot).prefWidth(itemSize).prefHeight(itemSize).pad(3);
    }
}
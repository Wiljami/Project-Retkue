package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fi.tuni.retkue.Item.Location;

/**
 * ItemPopUp class holds the functionality and features of the Item popup menu
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */

public class ItemPopUp extends RetkueDialog {
    /**
     * Reference to the Item in question
     */
    private Item item;
    /**
     * Reference to the party
     */
    private Party party;
    /**
     * Reference to the inn
     */
    private InnPopUp inn;

    /**
     * TextButton equipA
     */
    private TextButton equipA;

    /**
     * TextButton equipB
     */
    private TextButton equipB;

    /**
     * TextButton equipC
     */
    private TextButton equipC;

    /**
     * TextButton returnToInventory
     */
    private TextButton returnToInventory;

    /**
     * Constructor for ItemPopUp
     * @param title title of the screen
     * @param i Item in question
     * @param party player Player
     * @param inn reference to the InnPopUp that opened this
     */
    public ItemPopUp(String title, Item i, Party party, InnPopUp inn) {
        super(title);

        this.item = i;
        this.party = party;
        this.inn = inn;
        createMenu();
    }

    /**
     * Creates the UI elements
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        float itemWidth = Main.WORLDPIXELWIDTH/3f;

        Image itemImage = new Image (item.getPicture());
        float scale = itemWidth / itemImage.getWidth();
        float itemHeight = itemImage.getHeight() * scale;

        getContentTable().add(itemImage).prefWidth(itemWidth).prefHeight(itemHeight).left();

        getContentTable().row();

        RetkueLabel desc = new RetkueLabel(item.itemText(), "log");
        getContentTable().add(desc).prefWidth(popUpWidth).colspan(3);

        getContentTable().row();

        equipA = new TextButton (readLine("equipA"), getSkin());
        equipA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                equipItem(0);
            }
        });

        equipB = new TextButton (readLine("equipB"), getSkin());
        equipB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                equipItem(1);
            }
        });

        equipC = new TextButton (readLine("equipC"), getSkin());
        equipC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                equipItem(2);
            }
        });

        returnToInventory = new TextButton((readLine("returnToInv")), getSkin());
        returnToInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                moveToInventory();
            }
        });

        Table equipButtons = new Table();
        equipButtons.add(equipA);
        equipButtons.add(equipB);
        equipButtons.add(equipC);

        RetkueLabel equip = new RetkueLabel(readLine("equip"));
        getContentTable().add(equip).colspan(3);
        getContentTable().row();

        getContentTable().add(equipButtons).colspan(3);

        getContentTable().row();
        getContentTable().add(returnToInventory).colspan(3);
        getContentTable().row();

        TextButton sell = new TextButton(readLine("sell") + " $" + item.getPrice()/2, getSkin());
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sellItem(item);
            }
        });
        getContentTable().add(sell).left();

        getContentTable().add();

        TextButton back = new TextButton(readLine("return"), getSkin());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });
        getContentTable().add(back).right();
        updateButtons();
    }

    /**
     * Equips the Item item to the retku
     * @param retkuID retku slot to equip to item
     */
    private void equipItem(int retkuID) {
        party.findRetku(retkuID).equipItem(item);
        Location loc;
        switch(retkuID) {
            case 0: loc = Location.RETKUA; break;
            case 1: loc = Location.RETKUB; break;
            case 2: loc = Location.RETKUC; break;
            default: loc = Location.OTHER; break;
        }
        int id = findRetkuID(item.getLocation());
        if (id == -1) {
            party.removeItem(item);
        } else {
            party.findRetku(id).removeItem(item);
        }
        item.setLocation(loc);
        closeMe();
        inn.resetMe();
    }

    /**
     * returns retkuID based on the location
     * @param location location
     * @return i
     */
    private int findRetkuID(Location location) {
        int i;
        switch(location) {
            case RETKUA: i = 0; break;
            case RETKUB: i = 1; break;
            case RETKUC: i = 2; break;
            default: i = -1; break;
        }
        return i;
    }

    /**
     * Moves the item to the party inventory
     */
    private void moveToInventory() {
        removeItemFromRetku(item);
        party.addItem(item);
        inn.resetMe();
        closeMe();
    }

    /**
     * Removes the Item from the Retku
     * @param item Item to be removed
     */
    private void removeItemFromRetku(Item item) {
        int locID = findRetkuID(item.getLocation());
        if (locID != -1) {
            party.findRetku(locID).removeItem(item);
        }
    }

    /**
     * Sells the item
     * @param item Item to be sold
     */
    private void sellItem(Item item) {
        removeItemFromRetku(item);
        party.sellItem(item);
        inn.resetMe();
        closeMe();
    }

    /**
     * Updates the UI elements
     */
    private void updateButtons() {
        switch(item.getLocation()) {
            case RETKUA: equipA.setVisible(false); break;
            case RETKUB: equipB.setVisible(false); break;
            case RETKUC: equipC.setVisible(false); break;
            case PARTY: returnToInventory.setVisible(false); break;
            default: break;
        }

        if (party.getInventory().size() == 10) {
            returnToInventory.setDisabled(true);
            returnToInventory.setText(readLine("invFull"));
        }
    }

    /**
     * closeMe calls the game to be saved and closes ItemPopUp
     */
    private void closeMe() {
        getGame().saveGame();
        remove();
    }
}
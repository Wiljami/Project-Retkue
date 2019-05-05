package fi.tuni.retkue;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * InnPopUp contains functionality of the inn within the game.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class InnPopUp extends RetkueDialog {

    /**
     * Title of the InnPopUp window
     */
    private static String title = readLine("inn");

    /**
     * Reference to the party
     */
    private Party party;

    /**
     * size of the item pictures
     */
    private float itemSize;

    /**
     * Reference to itself
     */
    private InnPopUp inn;

    /**
     * Reference to the stage
     */
    private Stage stage;

    /**
     * Reference to the twon
     */
    private TownScene town;

    /**
     * Table holding the inventory UI parts
     */
    private Table inventory;

    /**
     * InnPopUp constructor
     * @param party player's party
     * @param townScene the townScene
     */
    public InnPopUp(Party party, TownScene townScene) {
        super(title);
        this.party = party;
        inn = this;
        this.town = townScene;
        this.stage = townScene.getStage();
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu generates different visible UI actors.
     */
    private void createMenu() {
        itemSize = Main.WORLDPIXELWIDTH/6f;

        inventory = new Table();
        generateInventory();

        charSlots = new Group[3][3];

        generateCharSheets();

        getContentTable().row();

        getContentTable().add(inventory);

        getContentTable().row();

        Table buttons = new Table();

        final TextButton rest = new TextButton(readLine("rest"), skin);
        rest.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RestDialog restDialog = new RestDialog(party, inn);
                restDialog.show(getStage());
            }
        });

        TextButton back = new TextButton(readLine("return"), getSkin());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });

        buttons.add(rest).pad(10);
        buttons.add(back).pad(10);
        getContentTable().add(buttons);
    }

    /**
     * Array of Groups
     */
    private Group[][] charSlots;

    /**
     * Table with the character portraits
     */
    Table charImages;

    /**
     * UI table for the Retku A's items
     */
    Table retkuAItems;

    /**
     * UI table for the Retku B's items
     */
    Table retkuBItems;

    /**
     * UI table for the Retku C's items
     */
    Table retkuCItems;

    /**
     * HealthBar bar0
     */
    HealthBar bar0;

    /**
     * HealthBar bar1
     */
    HealthBar bar1;

    /**
     * HealthBar bar2
     */
    HealthBar bar2;

    /**
     * AnimatedActor retkuA
     */
    private AnimatedActor retkuA;

    /**
     * AnimatedActor retkuB
     */
    private AnimatedActor retkuB;

    /**
     * AnimatedActor retkuC
     */
    private AnimatedActor retkuC;

    /**
     * generatePortraits generates the player portraits
     */
    private void generatePortraits() {
        retkuA = party.findRetku(0).getPortrait();
        retkuA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(0));
                retkuPopUp.show(stage);
            }
        });
        retkuB = party.findRetku(1).getPortrait();
        retkuB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(1));
                retkuPopUp.show(stage);
            }
        });
        retkuC = party.findRetku(2).getPortrait();
        retkuC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RetkuPopUp retkuPopUp = new RetkuPopUp(party.findRetku(2));
                retkuPopUp.show(stage);
            }
        });
    }

    /**
     * generates teh character sheets
     */
    private void generateCharSheets() {
        charImages = new Table();
        float charSize = Main.WORLDPIXELHEIGHT*(1f/7f);
        float healthBarWidth = Main.WORLDPIXELWIDTH*(1/20f);

        generatePortraits();

        bar0 = new HealthBar(healthBarWidth, charSize);
        bar1 = new HealthBar(healthBarWidth, charSize);
        bar2 = new HealthBar(healthBarWidth, charSize);

        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());

        retkuAItems = generateCharItems(party.findRetku(0), 0);
        retkuBItems = generateCharItems(party.findRetku(1), 1);
        retkuCItems = generateCharItems(party.findRetku(2), 2);

        charImages.add(retkuA).prefHeight(charSize).prefWidth(charSize);
        charImages.add(retkuAItems).center();
        charImages.row();
        charImages.add(bar0);
        charImages.row();
        charImages.add(retkuB).prefHeight(charSize).prefWidth(charSize/5f*4f);
        charImages.add(retkuBItems).center();
        charImages.row();
        charImages.add(bar1);
        charImages.row();
        charImages.add(retkuC).prefHeight(charSize).prefWidth(charSize/5f*4f);
        charImages.add(retkuCItems).center();
        charImages.row();
        charImages.add(bar2);

        getContentTable().add(charImages).left().pad(2);
    }

    /**
     * generates the character Items
     * @param retku Retku whose items are generated
     * @param i placement in charSlots
     * @return Table of the items
     */
    private Table generateCharItems(Retku retku, int i) {
        Table charInventories = new Table();

        Group slotA;
        if (retku.getSlotA() != null) {
            slotA = retku.getSlotA().getIcon(itemSize);
            addItemListener(slotA, retku.getSlotA());
            charInventories.add(slotA).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        } else {
            slotA = emptySlot("weapon");
            charInventories.add(slotA).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        }
        charSlots[i][0] = slotA;

        Group slotB;
        if (retku.getSlotB() != null) {
            slotB = retku.getSlotB().getIcon(itemSize);
            addItemListener(slotB, retku.getSlotB());
            charInventories.add(slotB).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        } else {
            slotB = emptySlot("armor");
            charInventories.add(slotB).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        }
        charSlots[i][1] = slotB;

        Group slotC;
        if (retku.getSlotC() != null) {
            slotC = retku.getSlotC().getIcon(itemSize);
            addItemListener(slotC, retku.getSlotC());
            charInventories.add(slotC).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        } else {
            slotC = emptySlot("trinket");
            charInventories.add(slotC).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        }
        charSlots[i][2] = slotC;

        return charInventories;
    }

    /**
     * Generates an emptySlot for the inveotry
     * @param slot Slot in question
     * @return Group holding graphics
     */
    private Group emptySlot (String slot) {
        String file = "items/empty_" + slot + ".png";
        Group button = new Group();
        Image tempImage = new Image(Utils.loadTexture(file));
        tempImage.setSize(itemSize, itemSize);
        button.addActor(tempImage);
        return button;
    }

    /**
     * Generates the inventory UI
     */
    private void generateInventory() {
        inventory.reset();
        int tmp = 0;
        for (int i = 0; i < 10; i++) {
            if (tmp == 5) {
                tmp = 0;
                inventory.row();
            }
            if (i < party.getInventory().size()) {
                generateItemButton(i);
            } else {
                Image emptySlot = new Image(Utils.loadTexture("items/empty_slot.png"));
                inventory.add(emptySlot).prefWidth(itemSize).prefHeight(itemSize).pad(1);
            }
            tmp++;
        }
    }

    /**
     * generates an itemButton
     * @param i placement of the item in the party inventory
     */
    private void generateItemButton(int i) {
        final Item item = party.getInventory().get(i);
        Group itemButton = item.getIcon(itemSize);
        addItemListener(itemButton, item);
        inventory.add(itemButton);
    }

    /**
     * addItemListener creates a click listener for the item buttons
     * @param itemButton Group of the item graphics
     * @param item Reference of the Item
     */
    private void addItemListener(final Group itemButton, final Item item) {
        itemButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ItemPopUp itemPopUp = new ItemPopUp(item.getName(), item, party, inn);
                itemPopUp.show(stage);
            }
        });
    }

    /**
     * Resets different UI elements
     */
    public void resetMe() {
        generatePortraits();
        getContentTable().reset();
        createMenu();
    }

    /**
     * closeMe saves the game and makes InnPopUp close itself
     */
    private void closeMe() {
        getGame().saveGame();
        getContentTable().reset();
        remove();
    }

    /**
     * updates the healthBars within the InnPopUp
     */
    public void updateHealthBars() {
        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());
        generatePortraits();
    }

    /**
     * Override the show method to adjust the location of the inn PopUp slightly
     * @param stage stage
     * @return this
     */
    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                Math.round((stage.getHeight() - getHeight()) / 2) - 40f);
        return this;
    }
}
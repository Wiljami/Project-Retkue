package fi.tuni.tiko;

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

    private Party party;

    private float itemSize;

    private InnPopUp inn;

    private Stage stage;

    private TownScene town;

    private Table inventory;
    /**
     * Constructor.
     */
    public InnPopUp(Party party, TownScene townScene) {
        super(title, skin, windowStyle);
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

        TextButton back = new TextButton(readLine("return"), getSkin());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });
        getContentTable().add(back);
    }

    private Group[][] charSlots;

    Table charImages;

    Table retkuAItems;
    Table retkuBItems;
    Table retkuCItems;

    private void generateCharSheets() {
        charImages = new Table();
        float charSize = Main.WORLDPIXELHEIGHT*(1f/7f);
        float healthBarWidth = Main.WORLDPIXELWIDTH*(1/20f);

        AnimatedActor retkuA = new AnimatedActor("bill_sprite_sheet_new.png",
                6 , 1, 1/2f, charSize, charSize);
        AnimatedActor retkuC = new AnimatedActor("miked_sprite_sheet.png",
                5 , 1, 1/1.5f, charSize*4/5f, charSize);
        Image retku1 = new Image(party.findRetku(1).getTexture());

        HealthBar bar0 = new HealthBar(healthBarWidth, charSize);
        HealthBar bar1 = new HealthBar(healthBarWidth, charSize);
        HealthBar bar2 = new HealthBar(healthBarWidth, charSize);

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
        charImages.add(retku1).prefHeight(charSize).prefWidth(charSize);
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

    private Group emptySlot (String slot) {
        String file = "items/empty_" + slot + ".png";
        Group button = new Group();
        Image tempImage = new Image(Utils.loadTexture(file));
        tempImage.setSize(itemSize, itemSize);
        button.addActor(tempImage);
        return button;
    }

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


    private void generateItemButton(int i) {
        final Item item = party.getInventory().get(i);
        Group itemButton = item.getIcon(itemSize);
        addItemListener(itemButton, item);
        inventory.add(itemButton);
    }

    private void addItemListener(final Group itemButton, final Item item) {
        itemButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ItemPopUp itemPopUp = new ItemPopUp(item.getName(), item, party, inn);
                itemPopUp.show(stage);
            }
        });
    }

    public void resetMe() {
        getContentTable().reset();
        createMenu();
    }

    private void closeMe() {
        getContentTable().reset();
        remove();
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
package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

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

    private Table inventory;
    /**
     * Constructor.
     */
    public InnPopUp(Party party) {
        super(title, skin, windowStyle);
        this.party = party;
        inn = this;
        createMenu();
        if (Main.debug) debug();
    }

    /**
     * createMenu generates different visible UI actors.
     */
    private void createMenu() {
        //float popUpWidth = Main.WORLDPIXELWIDTH*9f/10f;
        itemSize = Main.WORLDPIXELWIDTH/6f;

        inventory = new Table();
        generateInventory();

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

    private void generateCharSheets() {
        charSlots = new Group[3][3];
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

        Table charImages = new Table();

        charImages.add(retkuA).prefHeight(charSize).prefWidth(charSize);
        charImages.add(generateCharItems(party.findRetku(0), 0)).center();
        charImages.row();
        charImages.add(bar0);
        charImages.row();
        charImages.add(retku1).prefHeight(charSize).prefWidth(charSize);
        charImages.add(generateCharItems(party.findRetku(1), 1)).center();
        charImages.row();
        charImages.add(bar1);
        charImages.row();
        charImages.add(retkuC).prefHeight(charSize).prefWidth(charSize/5f*4f);
        charImages.add(generateCharItems(party.findRetku(2), 2)).center();
        charImages.row();
        charImages.add(bar2);

        getContentTable().add(charImages).left().pad(2);
    }

    private Table generateCharItems(Retku retku, int i) {
        Table charInventories = new Table();

        Group slotA;
        if (retku.getSlotA() != null) {
            slotA = retku.getSlotA().getIcon();
            charInventories.add(slotA).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        } else {
            slotA = emptySlot("weapon");
            charInventories.add(slotA).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        }
        charSlots[i][0] = slotA;

        Group slotB;
        if (retku.getSlotA() != null) {
            slotB = retku.getSlotA().getIcon();
            charInventories.add(slotB).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        } else {
            slotB = emptySlot("armor");
            charInventories.add(slotB).prefWidth(itemSize).prefHeight(itemSize).pad(1);

        }
        charSlots[i][1] = slotB;

        Group slotC;
        if (retku.getSlotA() != null) {
            slotC = retku.getSlotA().getIcon();
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

    public void reloadInventory() {
        generateInventory();
    }

    private boolean isDragging = false;
    private float dragX;
    private float dragY;

    private void generateItemButton(int i) {
        final Item item = party.getInventory().get(i);
        final Group itemButton = item.getIcon();
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDragging) {
                    ItemPopUp itemPopUp = new ItemPopUp(item.getName(), item, party, inn);
                    itemPopUp.show(getStage());
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                isDragging = true;
                super.touchDragged(event, x, y, pointer);
                itemButton.toFront();
                float xOrigin = itemButton.getX();
                float yOrigin = itemButton.getY();
                dragX = xOrigin + x - itemSize/2;
                dragY = yOrigin + y - itemSize/2;
                itemButton.setPosition(dragX, dragY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                equip(item);
                isDragging = false;
                reloadInventory();
            }
        });

        float scale = itemSize / itemButton.getWidth();
        float itemHeight = itemButton.getHeight() * scale;
        inventory.add(itemButton).prefWidth(itemSize).prefHeight(itemHeight).pad(1);
    }

    private void equip(Item item) {
        System.out.println("x:" + dragX +" y: " + dragY);

        for (int i = 0; i < 3; i++) {
            for (Group target: charSlots[i]) {
                System.out.println(target.getY());
                if (dragX > target.getX() && dragX < target.getX() + itemSize) {
                    if (dragY > target.getY() && dragY < target.getY() + itemSize) {
                        System.out.println("Hello");
                        System.out.println(target.getX() + " < " + dragX + " x < " + (target.getX()+itemSize));
                        System.out.println(target.getY() + " < " + dragX + " y < " + (target.getY()+itemSize));
                    }
                }
            }
        }
    }

    private void closeMe() {
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
package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

    private void generateCharSheets() {

        float charSize = Main.WORLDPIXELHEIGHT*(1f/7f);
        float healthBarWidth = Main.WORLDPIXELWIDTH*(1/20f);

        AnimatedActor retkuA = new AnimatedActor("bill_sprite_sheet_new.png", 6 , 1, 1/2f, charSize, charSize);
        AnimatedActor retkuC = new AnimatedActor("miked_sprite_sheet.png", 5 , 1, 1/1.5f, charSize*4/5f, charSize);
        Image retku1 = new Image(party.findRetku(1).getTexture());

        HealthBar bar0 = new HealthBar(healthBarWidth, charSize);
        HealthBar bar1 = new HealthBar(healthBarWidth, charSize);
        HealthBar bar2 = new HealthBar(healthBarWidth, charSize);

        bar0.setValue(party.findRetku(0).healthPercentage());
        bar1.setValue(party.findRetku(1).healthPercentage());
        bar2.setValue(party.findRetku(2).healthPercentage());

        Table charImages = new Table();

        charImages.add(retkuA).prefHeight(charSize).prefWidth(charSize);
        charImages.add(generateCharItems(party.findRetku(0))).center();
        charImages.row();
        charImages.add(bar0);
        charImages.row();
        charImages.add(retku1).prefHeight(charSize).prefWidth(charSize);
        charImages.add(generateCharItems(party.findRetku(0))).center();
        charImages.row();
        charImages.add(bar1);
        charImages.row();
        charImages.add(retkuC).prefHeight(charSize).prefWidth(charSize/5f*4f);
        charImages.add(generateCharItems(party.findRetku(0))).center();
        charImages.row();
        charImages.add(bar2);

        getContentTable().add(charImages).left().pad(2);
    }

    private Table generateCharItems(Retku retku) {
        Image itemA = new Image(Utils.loadTexture("non"));
        Image itemB = new Image(Utils.loadTexture("non"));
        Image itemC = new Image(Utils.loadTexture("non"));

        Table charInventories = new Table();

        charInventories.add(itemA).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        charInventories.add(itemB).prefWidth(itemSize).prefHeight(itemSize).pad(1);
        charInventories.add(itemC).prefWidth(itemSize).prefHeight(itemSize).pad(1);

        return charInventories;
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
                inventory.add().prefWidth(itemSize).prefHeight(itemSize);
            }
            tmp++;
        }
    }

    public void reloadInventory() {
        generateInventory();
    }

    private void generateItemButton(int i) {
        final Item item = party.getInventory().get(i);
        Image itemButton = new Image(item.getIcon());
        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked: " + item.getName() + " " + item.getDescription());
                ItemPopUp itemPopUp = new ItemPopUp(item.getName(), item, party,inn);
                itemPopUp.show(getStage());
            }
        });
        float scale = itemSize / itemButton.getWidth();
        float itemHeight = itemButton.getHeight() * scale;
        inventory.add(itemButton).prefWidth(itemSize).prefHeight(itemHeight).pad(1);
    }

    private void closeMe() {
        remove();
    }
}
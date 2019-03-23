package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    private float itemWidth;

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
        float popUpWidth = Main.WORLDPIXELWIDTH*9f/10f;
        itemWidth = Main.WORLDPIXELWIDTH/6f;

        inventory = new Table();
        generateInventory();

        String text = readLine("inn_desc");
        RetkueLabel desc = new RetkueLabel(text);

        getContentTable().add(desc).prefWidth(popUpWidth);

        getContentTable().row();

        getContentTable().add(inventory);

        getContentTable().row();
        button(readLine("return"), false);
    }

    private void generateInventory() {
        inventory.reset();
        int tmp = 0;
        for (int i = 0; i < party.getInventory().size(); i++) {
            if (tmp == 5) {
                tmp = 0;
                inventory.row();
            }
            generateItemButton(i);
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
        float scale = itemWidth / itemButton.getWidth();
        float itemHeight = itemButton.getHeight() * scale;
        inventory.add(itemButton).prefWidth(itemWidth).prefHeight(itemHeight).pad(1);
    }
}
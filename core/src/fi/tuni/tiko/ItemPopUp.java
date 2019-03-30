package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fi.tuni.tiko.Item.Location;

public class ItemPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";
    private Item item;
    private Party party;
    private InnPopUp inn;

    private TextButton equipA;
    private TextButton equipB;
    private TextButton equipC;
    private TextButton returnToInventory;

    public ItemPopUp(String title, Item i, Party party, InnPopUp inn) {
        super(title, skin, windowStyle);

        this.item = i;
        this.party = party;
        this.inn = inn;
        createMenu();
    }

    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;
        float itemWidth = Main.WORLDPIXELWIDTH/3f;

        Image itemImage = new Image (item.getPicture());
        float scale = itemWidth / itemImage.getWidth();
        float itemHeight = itemImage.getHeight() * scale;
        getContentTable().add(itemImage).prefWidth(itemWidth).prefHeight(itemHeight).left();

        Table statGroup = new Table();
        String stats = "ATT +" + item.getAttack();
        RetkueLabel attLabel = new RetkueLabel(stats);
        stats = "DEF +" + item.getDefense();
        RetkueLabel defLabel = new RetkueLabel(stats);

        statGroup.add(attLabel).pad(5);
        statGroup.row();
        statGroup.add(defLabel).pad(5);

        getContentTable().add(statGroup).left().top();

        getContentTable().row();

        RetkueLabel desc = new RetkueLabel(item.getDescription());
        getContentTable().add(desc).prefWidth(popUpWidth).colspan(2);

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

    private void moveToInventory() {
        removeItemFromRetku(item);
        party.addItem(item);
        inn.resetMe();
        closeMe();
    }

    private void removeItemFromRetku(Item item) {
        int locID = findRetkuID(item.getLocation());
        if (locID != -1) {
            party.findRetku(locID).removeItem(item);
        }
    }

    private void sellItem(Item item) {
        removeItemFromRetku(item);
        party.sellItem(item);
        inn.resetMe();
        closeMe();
    }

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

    private void closeMe() {
        remove();
    }
}
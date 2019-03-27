package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ItemPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";
    private Item item;
    private Party party;
    private InnPopUp inn;

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

        Group itemImage = item.getIcon();
        float scale = itemWidth / itemImage.getWidth();
        float itemHeight = itemImage.getHeight() * scale;
        getContentTable().add(itemImage).prefWidth(itemWidth).prefHeight(itemHeight).left();

        Table statGroup = new Table();
        String stats = "ATT +" + item.getAttack();
        RetkueLabel attLabel = new RetkueLabel(stats);
        stats = "DEF + " + item.getDefense();
        RetkueLabel defLabel = new RetkueLabel(stats);

        statGroup.add(attLabel).pad(5);
        statGroup.row();
        statGroup.add(defLabel).pad(5);

        getContentTable().add(statGroup).left().top();

        getContentTable().row();

        RetkueLabel desc = new RetkueLabel(item.getDescription());
        getContentTable().add(desc).prefWidth(popUpWidth).colspan(2);

        getContentTable().row();

        TextButton sell = new TextButton(readLine("sell") + " $" + item.getPrice()/2, getSkin());
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sellItem(item);
            }
        });
        getContentTable().add(sell).left();

        TextButton back = new TextButton(readLine("return"), getSkin());
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeMe();
            }
        });
        getContentTable().add(back).right();
    }

    private void sellItem(Item item) {
        System.out.println("sold");
        party.sellItem(item);
        inn.reloadInventory();
        closeMe();
    }

    private void closeMe() {
        remove();
    }
}
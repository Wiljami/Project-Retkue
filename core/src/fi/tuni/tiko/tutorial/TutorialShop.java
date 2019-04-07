package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.Item;
import fi.tuni.tiko.RetkueDialog;

public class TutorialShop extends RetkueDialog {
    private TutorialTownScene origin;

    private Group icon;

    public TutorialShop(final TutorialTownScene origin) {
        super("Shop", skin, "dialog");

        Item item = new Item(5, Item.Location.SHOP);

        icon = item.getIcon();

        this.origin = origin;

        Button buy = new TextButton("Buy", skin);

        buy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedBuy();
            }
        });

        Label text = new Label("A bucket. 100 gold pieces", skin);
        getContentTable().add(icon).pad(10);
        getContentTable().add(text);
        getContentTable().row();
        getContentTable().add(buy).colspan(2);
    }

    private void clickedBuy() {
        remove();
        origin.continueTutorial(109);
    }
}

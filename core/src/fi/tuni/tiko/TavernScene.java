package fi.tuni.tiko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.Gdx.files;

//TODO: Everything

class TavernScene extends Scene{
    public TavernScene(Main game) {
        super(game);
        createMenu();
        setupBackground("old_guy1.png");
    }

    private void confirmQuest() {
        CustomDialog dialog = new CustomDialog("Are you sure you want to do this?","") {
            public void result(Object obj) {
                System.out.println(obj);
            }
        };
        //TODO: Localization!
        dialog.show(getStage());
    }

    private void createMenu() {
        Button returnToOrigin = new TextButton("Return", getSkin());
        returnToOrigin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().returnToOrigin();
            }
        });

        Button quest = new ImageButton(Utils.loadButtonImage("inn button", 50, 50));
        quest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmQuest();
            }
        });

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Tavern", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(quest);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(returnToOrigin).colspan(3);
        getStage().addActor(table);
    }
}
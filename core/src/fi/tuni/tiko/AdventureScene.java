package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class AdventureScene extends Scene{
    public AdventureScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        Button returnToOrigin = new TextButton("Return", getSkin());
        returnToOrigin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().returnToOrigin();
            }
        });

        Button forest = new ImageButton(Utils.loadButtonImage("menu button", 50, 50));
        forest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openScene(Main.GameView.forest);
            }
        });


        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Adventure", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(forest);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(returnToOrigin).colspan(3);
        getStage().addActor(table);
    }
}
package fi.tuni.tiko;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * AdventureScene creates the view for the adventure picking screen
 *
 * @author Viljami Pietarila
 * @version 2019.0228
 */
class AdventureScene extends Scene{

    public AdventureScene(Main game) {
        super(game);
        createMenu();
    }

    private void promtToForest() {
        CustomDialog dialog = new CustomDialog("Do you want to\n proceed with this quest?","") {
            public void result(Object obj) {
                if (obj.equals(true)) {
                    remove();
                    getGame().openScene(Main.GameView.forest);
                }
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

        Button forest = new ImageButton(Utils.loadButtonImage("menu button", 50, 50));
        forest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                promtToForest();
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
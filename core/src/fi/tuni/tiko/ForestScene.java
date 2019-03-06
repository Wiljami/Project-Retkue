package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//TODO: Everything

class ForestScene extends Scene{

    public ForestScene(Main game) {
        super(game);
        createMenu();
        setupBackground("forest.png");
    }

    private void createMenu() {
        Button results = new TextButton("Results", getSkin());
        results.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ResultsPopUp resultsPopUp = new ResultsPopUp();
                resultsPopUp.show(getStage());
            }
        });

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Forest", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(200);
        table.row();
        table.add(results);
        table.row();
        table.add().prefHeight(200);
        table.row();
        getStage().addActor(table);
    }
}
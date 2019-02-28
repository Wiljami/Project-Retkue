package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

//TODO: Everything

class InnScene extends Scene{
    public InnScene(Main game) {
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

        Table table = new Table();
        if (debug) table.debug();
        table.setFillParent(true);
        table.add(new Label("Inn", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(400);
        table.row();
        table.add(returnToOrigin).colspan(3);
        getStage().addActor(table);
    }
}

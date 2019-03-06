package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class containing the options scene.
 *
 * @author Viljami Pietarila
 * @version 2019.02.19
 */

//TODO: This is not used. Remove this or do something about it.

public class OptionsScene extends Scene {

    //originScene holds pointer to the scene where we got here.
    private Scene originScene;

    public OptionsScene(Main game) {
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
        table.add(new Label("Options", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(400);
        table.row();
        table.add(returnToOrigin).colspan(3);
        getStage().addActor(table);
    }

    public void setOriginScene (Scene originScene) {
        this.originScene = originScene;
    }
}
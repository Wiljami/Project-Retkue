package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScene extends Scene {

    public MainMenuScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        Title title = new Title();

        Button start = new TextButton("Start", getSkin());
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openGame();
            }
        });

        Button options = new TextButton("Options", getSkin());
        options .addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openOptions();
            }
        });

        Table table = new Table();
        if (debug)table.debug();
        table.setFillParent(true);
        table.add(title).colspan(3);
        table.row();
        table.add().prefHeight(400);
        table.row();
        table.add(start).prefWidth(100);
        table.add().prefWidth(80);
        table.add(options).prefWidth(100);
        getStage().addActor(table);
    }

    @Override
    public void render(float delta) {
        sceneRender();
    }

    public void dispose() {
        super.dispose();
    }
}
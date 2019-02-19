package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScene extends Scene {
    public GameScene(Main game) {
        super(game);
        createMenu();
    }

    private void createMenu() {
        Button mainMenu = new TextButton("Return to Main Menu", getSkin());
        mainMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getGame().openMainMenu();
            }
        });
        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("Game", getSkin())).colspan(3);
        table.row();
        table.add().prefHeight(400);
        table.row();
        table.add(mainMenu);

        getStage().addActor(table);
    }
}

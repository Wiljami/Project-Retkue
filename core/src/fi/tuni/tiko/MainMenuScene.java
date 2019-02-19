package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenuScene extends Scene {

    public MainMenuScene(Main game) {
        super(game);
        //addTexts();
        createMenu();
    }

    private void addTexts() {
        String tempString = "Retkue";
        addText(new TextBox(tempString,
                getGame().WORLDPIXELWIDTH/2,
                (int)(getGame().WORLDPIXELHEIGHT/1.2),
                TextBox.FontType.comicHeadline));
    }

    private void createMenu() {
        Title title = new Title();

        Button start = new TextButton("Start", getSkin());
        Button options = new TextButton("Options", getSkin());

        Table table = new Table();
        //table.debug();
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
package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
        Label title = new Label("temp", getLabelComicHeadline());
        Label title2 = new Label("temp2", getLabelComicHeadline());
        Label title3 = new Label("temp3", getLabelComicHeadline());
        Label title4 = new Label("temp4", getLabelComicHeadline());

        Table table = new Table();
        table.debug();
        table.setFillParent(true);
        table.add(title);
        table.add(title4);
        table.add(title);
        table.row();
        table.add(title);
        table.add(title4);
        table.add(title);
        table.row();
        table.add(title);
        table.add(title4);
        table.add(title);
        table.row();
        table.add(title);
        table.add(title4);
        table.add(title);
        table.row();

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
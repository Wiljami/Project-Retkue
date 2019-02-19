package fi.tuni.tiko;

public class MainMenuScene extends Scene {

    public MainMenuScene(Main game) {
        super(game);
        addTexts();
    }

    public void addTexts() {
        String tempString = "Retkue";
        addText(new TextBox(tempString,
                getGame().WORLDPIXELWIDTH/2,
                (int)(getGame().WORLDPIXELHEIGHT/1.2),
                TextBox.FontType.comicHeadline));
    }

    @Override
    public void render(float delta) {
        sceneRender();
    }

    public void dispose() {
        super.dispose();
    }

}
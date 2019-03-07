package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class AdventurePopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    private static String title = readLine("adventure");

    public AdventurePopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        String text = readLine("adventure_desc");
        Label desc = new Label(text, skin);

        getContentTable().add(desc);
        getContentTable().row();

        button(readLine("back"), false);
        button(readLine("forward"), true);
    }

    public void result(Object obj) {
        if (obj.equals(true)) {
            remove();
            Quest quest = new Quest();
            game.getForestScene().setQuest(quest);
            quest.begin();
            game.openScene(Main.GameView.forest);
        }
    }
}
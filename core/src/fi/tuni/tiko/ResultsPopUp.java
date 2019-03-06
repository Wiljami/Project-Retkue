package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResultsPopUp extends RetkueDialog {

    private static String windowStyle = "dialog";

    private static String title = getBundle().get("results");

    public ResultsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        String text = getBundle().get("results_desc");
        Label desc = new Label(text, skin);
        getContentTable().add(desc);

        button(getBundle().get("ok"), true);
    }

    public void result(Object obj) {
        if (obj.equals(true)) {
            //remove();
            game.openScene(Main.GameView.gameScreen);
        }
    }
}
package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ResultsPopUp extends RetkueDialog {

    private static String windowStyle = "dialog";

    private static String title = "Results";
    private String text = "This is Results. Here we see how\nwe did on the adventure";

    public ResultsPopUp() {
        super(title, skin, windowStyle);
        createMenu();
        if (Main.debug) debug();
    }

    private void createMenu() {
        Label desc = new Label(text, skin);
        getContentTable().add(desc);

        button("OK", true);
    }

    public void result(Object obj) {
        if (obj.equals(true)) {
            //remove();
            game.openScene(Main.GameView.gameScreen);
        }
    }
}
package fi.tuni.tiko;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class QuestPopUp extends RetkueDialog {
    private static String windowStyle = "dialog";

    private static String title = "";

    private static TavernPopUp tavernPopUp;

    public QuestPopUp(TavernPopUp tavernPopUp) {
        super(title, skin, windowStyle);
        this.tavernPopUp = tavernPopUp;
        Image image = new Image(Utils.loadTexture("old_guy1.png"));

        getTitleLabel().setText(readLine("QUEST_001_TITLE"));

        getContentTable().add(image).prefWidth(image.getPrefWidth() / 5).prefHeight(image.getPrefHeight() / 5);
        getContentTable().row();

        String text = readLine("QUEST_001_TEXT");
        String accept = readLine("QUEST_001_ACCEPT");
        RetkueLabel desc = new RetkueLabel(text);
        getContentTable().add(desc).prefWidth(250);

        button(readLine("yes"), true); //sends "true" as the result
        button(readLine("no"), false);  //sends "false" as the result
    }



    public void result(Object obj) {
        if (obj.equals(true)) {
            System.out.println("ok");
            tavernPopUp.remove();
            button(readLine("ok"), true);
        }
    }
}
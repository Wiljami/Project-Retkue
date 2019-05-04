package fi.tuni.retkue.tutorial;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.RetkueLabel;
import fi.tuni.retkue.Utils;

public class TutorialPopUp extends RetkueDialog {
    private static String title = "";
    private TutorialScene scene;
    private int text_id;
    private int id;
    private Position position;

    public enum Position {TOP, LEFT, BOTTOM, MIDDLE}

    public TutorialPopUp(int text_id, int id, String image, Position position, TutorialScene scene) {
        super(title);
        this.text_id = text_id;
        this.id = id;
        this.scene = scene;
        this.position = position;
        createMenu();
    }

    private void createMenu() {
        String key = "TUTORIAL_TEXT_";
        key += Utils.convertToId(text_id);
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/5f;
        String text = readLine(key);
        RetkueLabel label = new RetkueLabel(text);
        getContentTable().add(label).prefWidth(popUpWidth);
        getContentTable().row();

        TextButton ok = new TextButton(readLine("ok"), getSkin());
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove();
                scene.continueTutorial(id);
            }
        });

        getContentTable().add(ok).center();
    }

    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        switch (position) {
            case TOP: setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                Math.round((stage.getHeight() - getHeight()) / 2) + 300f); break;
            case LEFT: setPosition(Math.round((stage.getWidth() - getWidth()) / 2 - 300f),
                    Math.round((stage.getHeight() - getHeight()) / 2)); break;
            case BOTTOM: setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                    Math.round((stage.getHeight() - getHeight()) / 2) - 300f); break;
            case MIDDLE: setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                    Math.round((stage.getHeight() - getHeight()) / 2)); break;
        }
        return this;
    }
}
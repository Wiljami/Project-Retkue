package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.RetkueDialog;

public class TutorialTavern extends RetkueDialog {
    private TutorialTownScene scene;
    private TutorialTavern me;
    private TutorialQuestPopUp tutorialQuestPopUp;

    public TutorialTavern(TutorialTownScene scene) {
        super("Tavern", skin, "dialog");
        this.scene = scene;
        me = this;
        createMenu();
    }

    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;


        setMovable(false);

        String text = readLine("tavern_desc");
        RetkueDialog.RetkueLabel desc = new RetkueDialog.RetkueLabel(text);

        TextButton label0 = new TextButton("Tutorial Quest", skin);
        label0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tutorialQuestPopUp = new TutorialQuestPopUp(me);
                tutorialQuestPopUp.show(getStage());
                scene.continueTutorial(6);
            }
        });

        TextButton back = new TextButton(readLine("return"), getSkin());

        getContentTable().add(desc).prefWidth(popUpWidth);

        getContentTable().row();
        getContentTable().add(label0).pad(5);

        getContentTable().row();
        getContentTable().add().pad(5);

        getContentTable().row();
        getContentTable().add().pad(5).padBottom(20);

        getContentTable().row();
        //getContentTable().add(back).center();

        scene.fadeIn();
        label0.toFront();
    }

    public TutorialTownScene getScene() {
        return scene;
    }

    public TutorialQuestPopUp getTutorialQuestPopUp() {
        return tutorialQuestPopUp;
    }
}
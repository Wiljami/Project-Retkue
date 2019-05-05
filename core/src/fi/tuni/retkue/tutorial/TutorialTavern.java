package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.RetkueDialog;
import fi.tuni.retkue.RetkueLabel;

/**
 * TutorialTavern holds the functionality and UI elements for the tavern parts of the tutorial
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class TutorialTavern extends RetkueDialog {
    /**
     * Reference to the TutorialTownScene
     */
    private TutorialTownScene scene;

    /**
     * Reference to itself
     */
    private TutorialTavern me;

    /**
     * TutorialQuestPopUp tutorialQuestPopUp
     */
    private TutorialQuestPopUp tutorialQuestPopUp;

    /**
     * Consturctor for TutorialTavern
     * @param scene the Scene that called this popUp
     */
    public TutorialTavern(TutorialTownScene scene) {
        super("Tavern");
        this.scene = scene;
        me = this;
        createMenu();
    }

    /**
     * createMenu generates the UI elements of the popup
     */
    private void createMenu() {
        float popUpWidth = Main.WORLDPIXELWIDTH*3f/4f;


        setMovable(false);

        String text = readLine("tavern_desc");
        RetkueLabel desc = new RetkueLabel(text);

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

    /**
     * getScene returns the scene
     * @return scene
     */
    public TutorialTownScene getScene() {
        return scene;
    }
}
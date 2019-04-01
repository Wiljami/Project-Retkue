package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.TownScene;

import fi.tuni.tiko.Utils;
import fi.tuni.tiko.tutorial.TutorialPopUp.Position;

import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.LEFT;
import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.TOP;

public class TownSceneTutorial extends TownScene implements TutorialScene {

    private Main game;
    private Image tavern;
    private Image mask;
    private TutorialTavern tutorialTavern;
    private TutorialController controller;

    public TownSceneTutorial(Main game, TutorialController controller) {
        super(game);
        this.game = game;
        this.controller = controller;
        mask = new Image(Utils.loadTexture("mask.png"));
        fadeOut();
        getStage().addActor(mask);
        continueTutorial(1);
    }

    private void tutorialPopUp(int text_id, int id, String image, Position location) {
        TutorialPopUp popUp = new TutorialPopUp(text_id, id, image, location, this);
        popUp.show(getStage());
    }

    @Override
    public void createMenu() {
    }

    @Override
    public void updateValues() {
    }

    @Override
    public void continueTutorial(int id) {
        System.out.println("id: " + id);
        switch (id) {
            case 1: tutorialPopUp(1 ,2, "old_guy1.png", TOP); break;
            case 2: phase2(); break;
            case 3: phase3(); break;
            case 4: phase4(); break;
            case 5: System.out.println("phase5"); break;
            case 6: tutorialPopUp(4, 7,"old_guy1_png", TOP); break;
            case 7: tutorialTavern.getTutorialQuestPopUp().tutorial(8); break;
            case 8: tutorialPopUp(5, 9,"old_guy1_png", TOP); break;
            case 9: tutorialTavern.getTutorialQuestPopUp().tutorial(10); break;
            case 10: tutorialPopUp(6, 11,"old_guy1_png", TOP); break;
            case 11: System.out.println("Phase11"); break;
            case 12: phase12(); break;
        }
    }

    private void phase2() {
        fadeIn();
        tutorialPopUp(2,3, "old_guy1.png", LEFT);
        tavern = new Image(Utils.loadTexture("tutorial/tutorial_tavern.png"));
        float wRatio = tavern.getWidth() / 1080;
        float hRatio = tavern.getHeight() / 1920;
        tavern.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        tavern.setY(215f);
        tavern.setX(229f);
        getStage().addActor(tavern);
    }

    private void phase3() {
        tavern.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueTutorial(4);
            }
        });
    }

    private void phase4() {
        tavern.setVisible(false);
        tutorialTavern = new TutorialTavern(this);
        tutorialTavern.show(getStage());
        tutorialPopUp(3, 5,"old_guy1_png", TOP);
    }

    private void phase12() {
        TextButton embark = new TextButton("Embark", getSkin());
        embark.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.tutorialPhase(2);
            }
        });
        getStage().addActor(embark);
        float x = Main.WORLDPIXELWIDTH/2 - embark.getWidth()/2;
        embark.setPosition(x,150f);
        tutorialPopUp(7, 13,"old_guy1_png", TOP);
    }

    public void fadeIn() {
        mask.toFront();
        mask.setColor(0,0,0, 0.4f);
    }

    public void fadeOut() {
        mask.setColor(0,0,0, 0.0f);
    }
}
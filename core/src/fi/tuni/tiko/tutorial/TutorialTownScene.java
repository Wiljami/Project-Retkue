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

public class TutorialTownScene extends TownScene implements TutorialScene {

    private Main game;
    private Image tavern;
    private Image inn;
    private Image shop;
    private Image mask;
    private TutorialTavern tutorialTavern;
    private TutorialController controller;

    public TutorialTownScene(Main game, TutorialController controller, int phase) {
        super(game);
        this.game = game;
        this.controller = controller;
        mask = new Image(Utils.loadTexture("mask.png"));
        fadeOut();
        getStage().addActor(mask);
        if (phase == 1) {
            continueTutorial(1);
        } else if (phase == 3) {
            continueTutorial(101);
        }
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
            case 13: fadeOut(); break;
            case 101: phase101(); break;
            case 102: phase102(); break;
            case 103: phase103(); break;
            case 104: System.out.println("Phase104"); break;
            case 105: tutorialPopUp(26,106, "old_guy1.png", TOP); break;
            case 106: phase106(); break;
            case 107: phase107(); break;
            case 108: System.out.println("Phase108"); break;
            case 109: phase109(); break;
            case 110: System.out.println("Phase110"); break;
            case 111: phase111(); break;
            case 112: tutorialPopUp(29,113, "old_guy1.png", TOP); break;
            case 113: tutorialPopUp(30,114, "old_guy1.png", TOP); break;
            case 114: tutorialPopUp(31,115, "old_guy1.png", TOP); break;
            case 115: tutorialPopUp(32,116, "old_guy1.png", TOP); break;
            case 116: tutorialPopUp(33,117, "old_guy1.png", TOP); break;
            case 117: tutorialPopUp(999,118, "old_guy1.png", TOP); break;
            case 118: controller.endTutorial();
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
                tavern.removeListener(this);
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

    private void phase101() {
        fadeIn();
        tutorialPopUp(24,102, "old_guy1.png", TOP);
        inn = new Image(Utils.loadTexture("tutorial/tutorial_inn.png"));
        float wRatio = inn.getWidth() / 1080;
        float hRatio = inn.getHeight() / 1920;
        inn.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        inn.setY(177f);
        getStage().addActor(inn);
    }

    private void phase102() {
        inn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueTutorial(103);
                inn.removeListener(this);
            }
        });
    }

    private void phase103() {
        inn.setVisible(false);
        TutorialInn tutorialInn = new TutorialInn(this);
        tutorialInn.show(getStage());
        tutorialPopUp(25,104, "old_guy1.png", TOP);
    }

    private void phase106() {
        fadeIn();
        shop = new Image(Utils.loadTexture("tutorial/tutorial_shop.png"));
        float wRatio = shop.getWidth() / 1080;
        float hRatio = shop.getHeight() / 1920;
        shop.setSize(Main.WORLDPIXELWIDTH*wRatio,Main.WORLDPIXELHEIGHT*hRatio);
        shop.setY(316f);
        shop.setX(87f);

        shop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueTutorial(107);
                shop.removeListener(this);
            }
        });

        getStage().addActor(shop);
    }

    private void phase107() {
        shop.setVisible(false);
        TutorialShop tutorialShop = new TutorialShop(this);
        tutorialShop.show(getStage());
        tutorialPopUp(27,108, "old_guy1.png", TOP);
    }

    private void phase109() {
        inn.setVisible(true);
        inn.toFront();
        tutorialPopUp(28,110, "old_guy1.png", TOP);
        inn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueTutorial(111);
                inn.removeListener(this);
            }
        });
    }

    private void phase111() {
        inn.setVisible(false);
        //TODO: Continue!
        continueTutorial(112);
    }

    public void fadeIn() {
        mask.toFront();
        mask.setColor(0,0,0, 0.4f);
    }

    public void fadeOut() {
        mask.setColor(0,0,0, 0.0f);
    }
}
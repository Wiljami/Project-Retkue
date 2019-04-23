package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.Gdx;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.Party;

public class TutorialController {

    Party tutorialParty;
    private Main main;
    public TutorialController (Main main) {
        this.main = main;
        this.tutorialParty = new Party(main);
        tutorialParty.newGame();
    }

    public void tutorialPhase(int phase) {
        switch (phase) {
            case 1: phase1(); break;
            case 2: phase2(); break;
            case 3: phase3(); break;
        }
    }

    private void phase1() {
        townPhase(1);
    }

    private void phase2() {
        TutorialForestScene tutorialForestScene = new TutorialForestScene(main, this);
        Gdx.input.setInputProcessor(tutorialForestScene.getStage());
        main.setScreen(tutorialForestScene);
    }

    private void phase3() {
        townPhase(3);
    }

    private void townPhase(int phase) {
        TutorialTownScene tutorialTownScene = new TutorialTownScene(main, this, phase);
        Gdx.input.setInputProcessor(tutorialTownScene.getStage());
        main.setScreen(tutorialTownScene);
    }

    public Party getTutorialParty() {
        return tutorialParty;
    }

    public void endTutorial() {
        main.setTutorial(false);
        main.startGame();
    }
}
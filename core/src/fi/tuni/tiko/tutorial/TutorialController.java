package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.Gdx;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.Party;

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
        }
    }

    private void phase1() {
        TownSceneTutorial townSceneTutorial = new TownSceneTutorial(main, this);
        Gdx.input.setInputProcessor(townSceneTutorial.getStage());
        main.setScreen(townSceneTutorial);
    }

    private void phase2() {
        ForestSceneTutorial forestSceneTutorial = new ForestSceneTutorial(main, this);
        Gdx.input.setInputProcessor(forestSceneTutorial.getStage());
        main.setScreen(forestSceneTutorial);
    }

    public Party getTutorialParty() {
        return tutorialParty;
    }

    public void endTutorial() {
        main.setTutorial(false);
        main.startGame();
    }
}
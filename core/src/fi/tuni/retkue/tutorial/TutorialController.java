package fi.tuni.retkue.tutorial;

import com.badlogic.gdx.Gdx;

import fi.tuni.retkue.Main;
import fi.tuni.retkue.Party;

/**
 * TutorialController class controls the tutorial functions and methods.
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class TutorialController {

    /**
     * tutorialParty is a tutorial version of the party
     */
    Party tutorialParty;

    /**
     * Reference to the main
     */
    private Main main;

    /**
     * Constructor for TutorialController
     * @param main reference to the main
     */
    public TutorialController (Main main) {
        this.main = main;
        this.tutorialParty = new Party(main);
        tutorialParty.newGame();
    }

    /**
     * run phases depending on the phase
     * @param phase current phase
     */
    public void tutorialPhase(int phase) {
        switch (phase) {
            case 1: phase1(); break;
            case 2: phase2(); break;
            case 3: phase3(); break;
        }
    }

    /**
     * tutorial phase1
     */
    private void phase1() {
        townPhase(1);
    }

    /**
     * tutorial phase2
     */
    private void phase2() {
        TutorialForestScene tutorialForestScene = new TutorialForestScene(main, this);
        Gdx.input.setInputProcessor(tutorialForestScene.getStage());
        main.setScreen(tutorialForestScene);
    }

    /**
     * tutorial phase3
     */
    private void phase3() {
        townPhase(3);
    }

    /**
     * initiate townPhase
     * @param phase the current phase of the tutorial
     */
    private void townPhase(int phase) {
        TutorialTownScene tutorialTownScene = new TutorialTownScene(main, this, phase);
        Gdx.input.setInputProcessor(tutorialTownScene.getStage());
        main.setScreen(tutorialTownScene);
    }

    /**
     * getter for the tutorial party
     * @return tutorialParty
     */
    public Party getTutorialParty() {
        return tutorialParty;
    }

    /**
     * endTutorial sets the tutorial false and starts the actual game
     */
    public void endTutorial() {
        main.setTutorial(false);
        main.startGame();
    }
}
package fi.tuni.tiko.tutorial;

import fi.tuni.tiko.Main;
import fi.tuni.tiko.TownScene;

import fi.tuni.tiko.tutorial.TutorialPopUp.Position;

import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.LEFT;
import static fi.tuni.tiko.tutorial.TutorialPopUp.Position.TOP;

public class TownSceneTutorial extends TownScene implements TutorialScene {

    public TownSceneTutorial(Main game) {
        super(game);
        continueTutorial(0);
    }

    private void tutorialPopUp(int id, String image, Position location) {
        TutorialPopUp popUp = new TutorialPopUp(id, image, location, this);
        popUp.show(getStage());
    }

    @Override
    public void continueTutorial(int id) {
        id++;
        switch (id) {
            case 1: tutorialPopUp(id, "old_guy1.png", TOP); break;
            case 2: tutorialPopUp(id, "old_guy1.png", LEFT); break;
        }
    }
}
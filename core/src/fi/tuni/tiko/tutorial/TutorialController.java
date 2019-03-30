package fi.tuni.tiko.tutorial;

import com.badlogic.gdx.Gdx;

import fi.tuni.tiko.Main;

public class TutorialController {

    private Main main;
    public TutorialController (Main main) {
        this.main = main;
    }

    public void startTutorial() {
        TownSceneTutorial townSceneTutorial = new TownSceneTutorial(main);
        Gdx.input.setInputProcessor(townSceneTutorial.getStage());
        main.setScreen(townSceneTutorial);
    }
}

package fi.tuni.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import fi.tuni.tiko.tutorial.TutorialController;
import fi.tuni.tiko.tutorial.TutorialPrompt;

/**
 * Main class for the 2019 spring mobile game project. Controls different scenes and their relations.
 *
 * @author Viljami Pietarila
 * @version 2019.0313
 */
public class Main extends Game {
    /**
     * SpriteBatch of the game.
     */
	SpriteBatch batch;

    /**
     * needed and we need the pixels for rendering text.
     */
	public final static int WORLDPIXELHEIGHT = 640;
    public final static int WORLDPIXELWIDTH = 360;

    /**
     * GameView is enum for identifying between different scenes in the game.
     */
    public enum GameView {
        mainMenu, town, forest
    }

    /**
     * debug boolean toggles debug features in the code
     */
    public static boolean debug = false;

    /**
     * party will hold the resources and the player information the player has gathered.
     */
    private static Party party;

    /**
     * townInfo holds the resources and variables regarding to the state of the town
     */
    private static TownInfo townInfo;

    /**
     * Locale of the game.
     */
    Locale locale = Locale.getDefault();

    /**
     * The current Scene in use.
     */
    private Scene currentScene;

    /**
     * Number of steps walked.
     */
    private static float stepCount;

    /**
     * Boolean for the stepsimulator. Used for testing on desktop.
     */
    private static boolean stepSim = false;

    /**
     * Save filename
     */
    private static String saveFileName = "RetkueSave";


    private boolean tutorial = false;

    /**
     * create()
     */
    @Override
	public void create () {
        //TODO: implement elegant exit on BackKey
        //TODO: This is needed as default behavior bugs out the graphics
        Gdx.input.setCatchBackKey(true);
        initiateGame();
        if(stepSim) stepSimulator();
        if(tutorial) startTutorial();
    }

	/**
     * Method for initiating game elements used in the game.
     */
	private void initiateGame() {
        batch = new SpriteBatch();
        party = new Party(this);
        //TODO: Create the load and save. Here we need to check if a party already exists and load it.
        townInfo = new TownInfo();
        if (!SaveGame.load(saveFileName, party, townInfo)) {
            party.newGame();
            townInfo.newGame();
            tutorial = true;
        }
        MainMenuScene mainMenuScene = new MainMenuScene(this);
        currentScene = mainMenuScene;
        openScene(GameView.mainMenu);
    }

    private void startTutorial() {
        TutorialController tutorialController = new TutorialController(this);
        TutorialPrompt tutorialPrompt = new TutorialPrompt(tutorialController);
        tutorialPrompt.show(currentScene.getStage());
    }

    /**
     * openScene is a method handling opening a new scene. It works using enum GameView.
     * @param gameView the scene we wish to navigate to
     */
	public void openScene(GameView gameView) {
        switch(gameView) {
            case mainMenu: MainMenuScene mainMenuScene = new MainMenuScene(this); currentScene = mainMenuScene; break;
            case town: TownScene townScene = new TownScene(this); currentScene = townScene; break;
            case forest: ForestScene forestScene = new ForestScene(this); currentScene = forestScene; break;
            default: throw new IllegalArgumentException ("openScene defaulted with " + gameView);
        }
        Gdx.input.setInputProcessor(currentScene.getStage());
        setScreen(currentScene);
    }

    /**
     * dispose(). dispose things we have created.
     */
	@Override
	public void dispose () {
		batch.dispose();
	}

    /**
     * Getter for the spriteBatch.
     * @return returns the spriteBatch batch
     */
    public SpriteBatch getBatch() {
		return batch;
	}

    /**
     * getter for party
     * @return party
     */
    public Party getParty() {
        return party;
    }

    /**
     * receiveSteps method is called by the android sensors. It receives the STEP_COUNTER float.
     *
     * STEP_COUNTER is the amount of steps the phone sensors have measured since rebooting the
     * phone.
     * @param s float stepCount
     */
    public static void receiveSteps(float s) {
        stepCount = s;
        if (party != null) {
            int newSteps = (int)(stepCount - Config.getStepStartPosition());
            Config.setStepStartPosition(stepCount);
            party.addSteps(newSteps);
        }
    }

    /**
     * This simulates the already existing steps within the STEP_COUNTER sensor
     */
    float testSteps = 100f;

    /**
     * stepSimulator gives us steps. For dev purposes. Adds a single step every second.
     */
    private void stepSimulator() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                testSteps++;
                receiveSteps(testSteps);
                stepSimulator();
            }
        }, 1000);
    }

    /**
     * Called by the DesktopLauncher to turn on the StepSimulator. For Dev purposes.
     */
    public static void useStepSimulator() {
        stepSim = true;
    }

    /**
     * getter for currentScene.
     * @return reference to the current Scene in use
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * getSaveFileName
     * @return saveFileName
     */
    public static String getSaveFileName() {
        return saveFileName;
    }

    /**
     * getTownInfo
     * @return townInfo
     */
    public static TownInfo getTownInfo() {
        return townInfo;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }

    public void startGame() {
        initiateGame();
    }

    public void saveGame() {
        SaveGame.save(getSaveFileName(), getParty(), getTownInfo());
    }
}
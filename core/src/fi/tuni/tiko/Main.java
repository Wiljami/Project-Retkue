package fi.tuni.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

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
     * mainMenuScene is the main menu screen of the game. It is an extension of Scene.
     */
	MainMenuScene mainMenuScene;
    /**
     * townScene is the main menu screen of the game. It is an extension of Scene.
     */
	TownScene townScene;
    /**
     * forestScene is the main menu screen of the game. It is an extension of Scene.
     */
	ForestScene forestScene;

    /**
     * needed and we need the pixels for rendering text.
     */
	public final static int WORLDPIXELHEIGHT = 640;
    public final static int WORLDPIXELWIDTH = 360;

    /**
     * GameView is enum for identifying between different scenes in the game.
     */
    public enum GameView {
        mainMenu, gameScreen, forest
    }

    private Music backgroundMusic;

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

    private static float stepCount;
    private static boolean stepSim = false;

    private static String saveFileName = "RetkueSave";

    /**
     * create()
     */
    @Override
	public void create () {
        //TODO: implement elegant exit on BackKey
        //TODO: This is needed as default behavior bugs out the graphics
        Gdx.input.setCatchBackKey(true);
        //backgroundMusic.play();
        initiateGame();
        openScene(GameView.mainMenu);
        if(stepSim) stepSimulator();
	}

	/**
     * Method for initiating game elements used in the game.
     */
	private void initiateGame() {
        batch = new SpriteBatch();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("retkuetheme.ogg"));
        backgroundMusic.setLooping(true);
        party = new Party(this);
        //TODO: Create the load and save. Here we need to check if a party already exists and load it.
        if (!SaveGame.load(saveFileName, party)) party.newGame();
        mainMenuScene = new MainMenuScene(this);
        townInfo = new TownInfo();
        townScene = new TownScene(this);
        forestScene = new ForestScene(this);
        currentScene = mainMenuScene;
    }

    /**
     * openScene is a method handling opening a new scene. It works using enum GameView.
     * @param gameView the scene we wish to navigate to
     */
	public void openScene(GameView gameView) {
        switch(gameView) {
            case mainMenu: currentScene = mainMenuScene; break;
            case gameScreen: currentScene = townScene; break;
            case forest: currentScene = forestScene; break;
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
        mainMenuScene.dispose();
        townScene.dispose();
        forestScene.dispose();
	}

    /**
     * Getter for the spriteBatch.
     * @return returns the spriteBatch batch
     */
    public SpriteBatch getBatch() {
		return batch;
	}

    /**
     * getter for forestScene
     * @return forestScene
     */
    public ForestScene getForestScene() {
        return forestScene;
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

    public static String getSaveFileName() {
        return saveFileName;
    }

    public static TownInfo getTownInfo() {
        return townInfo;
    }
}
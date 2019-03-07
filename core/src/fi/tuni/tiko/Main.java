package fi.tuni.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

/**
 * Main class for the 2019 spring mobile game project. Controls different scenes and their relations.
 *
 * @author Viljami Pietarila
 * @version 2019.0306
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
     * gameScene is the main menu screen of the game. It is an extension of Scene.
     */
	GameScene gameScene;
    /**
     * forestScene is the main menu screen of the game. It is an extension of Scene.
     */
	ForestScene forestScene;

    /**
     * needed and we need the pixels for rendering text.
     */
	public final int WORLDPIXELHEIGHT = 640;
    public final int WORLDPIXELWIDTH = 360;

    /**
     * GameView is enum for identifying between different scenes in the game.
     */
    public enum GameView {
        mainMenu, gameScreen, forest
    }

    /**
     * debug boolean toggles debug features in the code
     */
    public static boolean debug = false;

    /**
     * Locale of the game.
     */
    Locale locale = Locale.getDefault();

    @Override
	public void create () {
        //TODO: implement elegant exit on BackKey
        //TODO: This is needed as default behavior bugs out the graphics
        Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
        initiateScenes();
        openScene(GameView.mainMenu);
	}

    /**
     * Method for initiating all the scenes used in the game.
     */
	private void initiateScenes() {
        mainMenuScene = new MainMenuScene(this);
        gameScene = new GameScene(this);
        forestScene = new ForestScene(this);
    }

    /**
     * openScene is a method handling opening a new scene. It works using enum GameView.
     * @param gameView the scene we wish to navigate to
     */
	public void openScene(GameView gameView) {
        Scene scene;
        switch(gameView) {
            case mainMenu: scene = mainMenuScene; break;
            case gameScreen: scene = gameScene; break;
            case forest: scene = forestScene; break;
            default: throw new IllegalArgumentException ("openScene defaulted with " + gameView);
        }
        Gdx.input.setInputProcessor(scene.getStage());
        setScreen(scene);
    }

    /**
     * TODO: Figure out if we need this here or not.
     * Idea is that it's here so we can add stuff if we wish to render more stuff.
     */
	@Override
	public void render () {
        super.render();
	}

    /**
     * dispose(). dispose things we have created.
     */
	@Override
	public void dispose () {
		batch.dispose();
        mainMenuScene.dispose();
        gameScene.dispose();
        forestScene.dispose();
	}

    /**
     * Getter for the spriteBatch.
     * @return returns the spriteBatch batch
     */
    public SpriteBatch getBatch() {
		return batch;
	}

    public ForestScene getForestScene() {
        return forestScene;
    }
}
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
     * TODO: Comment this.
     */
	SpriteBatch batch;
	MainMenuScene mainMenuScene;
	OptionsScene optionScene;
	GameScene gameScene;
	InnScene innScene;
	ShopScene shopScene;
	TavernScene tavernScene;
	AdventureScene adventureScene;
	ForestScene forestScene;
	ResultsScene resultsScene;

    /**
     * TODO: How do we want to do this? Since we don't use physics in this game, the meters are not
     * needed and we need the pixels for rendering text.
     */
	public final int WORLDPIXELHEIGHT = 640;
    public final int WORLDPIXELWIDTH = 360;

    /**
     * TODO: Do we need this?
     */
    public final int WORLDHEIGHT = 4;
    public final int WORLDWIDTH = 3;

    /**
     * GameView is enum for identifying between different scenes in the game.
     */
    public enum GameView {
        mainMenu, gameScreen, menu, inn, shop, tavern, adventure, forest, results
    }

    /**
     * debug boolean toggles debug features in the code
     */
    public static boolean debug = false;
    //TODO: Currently unused
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
        optionScene = new OptionsScene(this);
        gameScene = new GameScene(this);
        innScene = new InnScene(this);
        shopScene = new ShopScene(this);
        tavernScene = new TavernScene(this);
        adventureScene = new AdventureScene(this);
        forestScene = new ForestScene(this);
        resultsScene = new ResultsScene(this);
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
            case menu: scene = optionScene; break;
            case inn: scene = innScene; break;
            case shop: scene = shopScene; break;
            case tavern: scene = tavernScene; break;
            case adventure: scene = adventureScene; break;
            case forest: scene = forestScene; break;
            case results: scene = resultsScene; break;
            default: throw new IllegalArgumentException ("openScene defaulted with " + gameView);
        }
        Gdx.input.setInputProcessor(scene.getStage());
        setScreen(scene);
    }


    private GameView origin;
    /**
     * As openScene, but it records the scene we come from to GameView variable origin.
     * @param gameView the view we wish to navigate to
     * @param origin the scene where we come from
     */
	public void openScene(GameView gameView, GameView origin) {
        this.origin = origin;
        openScene(gameView);
    }

    /**
     * Calls openScene using origin. Used for returning to the previous scene.
     */
    public void returnToOrigin() {
        openScene(origin);
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
        optionScene.dispose();
        gameScene.dispose();
        innScene.dispose();
        shopScene.dispose();
        tavernScene.dispose();
        adventureScene.dispose();
        forestScene.dispose();
        resultsScene.dispose();
	}

    /**
     * Getter for the spriteBatch.
     * @return returns the spriteBatch batch
     */
    public SpriteBatch getBatch() {
		return batch;
	}
}
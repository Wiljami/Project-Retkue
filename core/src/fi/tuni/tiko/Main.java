package fi.tuni.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

public class Main extends Game {
	SpriteBatch batch;
	MainMenuScene mainMenuScene;

	public final int WORLDPIXELHEIGHT = 640;
    public final int WORLDPIXELWIDTH = 360;

    public final int WORLDHEIGHT = 4;
    public final int WORLDWIDTH = 3;

    public boolean debug = false;
    Locale locale = Locale.getDefault();

    @Override
	public void create () {
		batch = new SpriteBatch();
        mainMenuScene = new MainMenuScene(this);
        Gdx.input.setInputProcessor(mainMenuScene.getStage());
        setScreen(mainMenuScene);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        mainMenuScene.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}

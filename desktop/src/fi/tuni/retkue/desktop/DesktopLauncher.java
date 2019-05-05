package fi.tuni.retkue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fi.tuni.retkue.Main;

/**
 * DesktopLauncher class is run when the game is started when ran from desktop
 */
public class DesktopLauncher {
    /**
     * main constructor
     * @param arg command line array
     */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.title = "Sysilehto";
		config.height = 640;
		config.width = 360;
		Main.useStepSimulator();
		new LwjglApplication(new Main(), config);
	}
}
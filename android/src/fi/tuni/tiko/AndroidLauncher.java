package fi.tuni.tiko;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fi.tuni.tiko.Main;

/**
 * TODO: this documentation
 */
public class AndroidLauncher extends AndroidApplication implements SensorEventListener {

    private SensorManager sensorManager;
    boolean activityRunning;
    private float count;

    /**
     * onCreate is called when the app is created. We create a STEP_COUNTER sensor as well
     * TODO: Better documentation here?
     * @param savedInstanceState ??
     */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		initialize(new Main(), config);
	}

    /**
     * We start the STEP_COUNTER sensor on resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * onSensorChanged is called when a sensor changed. We take the amount of steps from here.
     *
     * STEP_COUNTER value is taken from the sensors and sent to Main.receiveSteps
     * @param event sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            count = event.values[0];
            Main.receiveSteps(count);
        }
    }

    /**
     * onAccuracyChanged
     * TODO: Comment this, what is this even?
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

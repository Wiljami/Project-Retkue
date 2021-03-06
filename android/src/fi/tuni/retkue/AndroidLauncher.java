package fi.tuni.retkue;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * AndroidLauncher class is run when ran on an android device
 *
 * @author Viljami Pietarila
 * @version 2019.0505
 */
public class AndroidLauncher extends AndroidApplication implements SensorEventListener {
    /**
     * SensorManager sensorManager
     */
    private SensorManager sensorManager;

    /**
     * boolean activityRunning
     */
    boolean activityRunning;

    /**
     * count of steps
     */
    private float count;

    /**
     * onCreate is called when the app is created. We create a STEP_COUNTER sensor as well
     * @param savedInstanceState savedInstanceState
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
            Toast.makeText(this, "STEP_COUNTER sensor not available!", Toast.LENGTH_LONG).show();
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
     * @param sensor sensor
     * @param accuracy accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
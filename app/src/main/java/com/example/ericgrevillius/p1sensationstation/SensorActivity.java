package com.example.ericgrevillius.p1sensationstation;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.util.Date;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvTitle;
    private TextView tvVendor;
    private TextView tvId;
    private TextView tvVersion;
    private TextView tvPowerConsumption;
    private TextView tvAccuracy;
    private TextView tvResolution;
    private TextView tvMaxRange;
    private TextView tvMinDelay;
    private TextView tvReadings;
    private TextView tvTimestamp;
    private SensorManager sensorManager;
    private int sensorType;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Intent intent = getIntent();
        sensorType = intent.getIntExtra("sensorType",-1);
        initializeTextViews();
    }

    private void initializeTextViews() {
        tvTitle = findViewById(R.id.sensor_name);
        tvVendor = findViewById(R.id.sensor_vendor);
        tvId = findViewById(R.id.sensor_id);
        tvVersion = findViewById(R.id.sensor_version);
        tvPowerConsumption = findViewById(R.id.senor_power_consumption);
        tvAccuracy = findViewById(R.id.sensor_accuracy);
        tvResolution = findViewById(R.id.sensor_resolution);
        tvMaxRange = findViewById(R.id.sensor_max_range);
        tvMinDelay = findViewById(R.id.sensor_min_delay);
        tvReadings = findViewById(R.id.sensor_readings);
        tvTimestamp = findViewById(R.id.sensor_timestamp);
    }

    public void setTvTitle(String title) {
        this.tvTitle.setText("Name:\n" + title);
    }

    public void setTvVendor(String vendor) {
        this.tvVendor.setText("Vendor:\n" + vendor);
    }

    public void setTvId(String Id) {
        this.tvId.setText("Id:\n" + Id);
    }

    public void setTvVersion(String version) {
        this.tvVersion.setText("Version:\n" + version);
    }

    public void setTvPowerConsumption(String powerConsumption) {
        this.tvPowerConsumption.setText("PWR consumption:\n" + powerConsumption);
    }

    public void setTvAccuracy(String accuracy) {
        this.tvAccuracy.setText("Accuracy:\n" + accuracy);
    }

    public void setTvResolution(String resolution) {
        this.tvResolution.setText("Resolution:\n" + resolution);
    }

    public void setTvMaxRange(String maxRange) {
        this.tvMaxRange.setText("Maximum range:\n" + maxRange);
    }

    public void setTvMinDelay(String minDelay) {
        this.tvMinDelay.setText("Minimum delay:\n"+ minDelay);
    }

    public void setTvReadings(String readings) {
        this.tvReadings.setText("Readings:\n" + readings);
    }

    public void setTvTimestamp(String timestamp) {
        this.tvTimestamp.setText("Timestamp:\n" + timestamp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorType != -1){
            sensor = sensorManager.getDefaultSensor(sensorType);
            boolean register = sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
            if (register){
                Toast.makeText(this, "Listener registered", Toast.LENGTH_LONG).show();
                setTvTitle(sensor.getName());
                setTvVendor(sensor.getVendor());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    setTvId("" + sensor.getId());
                } else {
                    setTvId("Not available");
                }
                setTvVersion("" + sensor.getVersion());
                setTvPowerConsumption("" + sensor.getPower());
                setTvResolution("" + sensor.getResolution());
                setTvMaxRange("" + sensor.getMaximumRange());
                setTvMinDelay("" + sensor.getMinDelay());
            } else {
                Toast.makeText(this, "Listener not registered", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Sensor not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        Toast.makeText(this, "Listener unregistered", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == sensor.getType()){
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < sensorEvent.values.length; i++){
                values.append(sensorEvent.values[i] + "\n");
            }
            setTvReadings(values.toString());
            //TODO: make timestamps look nicer.
            setTvTimestamp("" + sensorEvent.timestamp);
            setTvAccuracy("" + sensorEvent.accuracy);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

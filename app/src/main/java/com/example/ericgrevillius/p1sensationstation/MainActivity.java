package com.example.ericgrevillius.p1sensationstation;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SensorManager sensorManager;
    private List<Sensor> sensorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sensorTitles = new ArrayList<>();
        for (Sensor s : sensorList){
            sensorTitles.add(s.getName());
        }
        ArrayAdapter<String> sensorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,sensorTitles);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(sensorAdapter);
        listView.setOnItemClickListener(new ItemListener());
    }

    private class ItemListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, SensorActivity.class);
            intent.putExtra("sensorType", sensorList.get(i).getType());
            startActivity(intent);
        }
    }
}

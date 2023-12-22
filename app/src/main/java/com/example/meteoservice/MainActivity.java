package com.example.meteoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.temp);

        registerReceiver(receiver, new IntentFilter("MeteoService"), RECEIVER_EXPORTED);

        Intent intent = new Intent(this, MeteoService.class);
        startService(intent);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("RESULT", intent.getStringExtra("INFO"));

            String str = intent.getStringExtra("INFO");
            try {
                JSONObject start = new JSONObject(str);
                JSONObject current = start.getJSONObject("current");
                double temp = current.getDouble("temp_c");
                textView.setText("" + temp);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = new Intent(this, MeteoService.class);
        stopService(intent);
    }
}
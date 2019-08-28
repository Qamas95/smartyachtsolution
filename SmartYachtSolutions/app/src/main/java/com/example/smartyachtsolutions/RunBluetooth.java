package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RunBluetooth extends AppCompatActivity {

    public boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.isEnabled();

    }

    Button turnONBt;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_bluetooth);
        turnONBt = findViewById(R.id.turnONBt);

        turnONBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            }
        });


/*
       if(bluetoothAdapter.isEnabled()){
            Toast.makeText(this, "Turned ON Bluetooth", Toast.LENGTH_LONG).show();
            final Intent i = new Intent(this, Connect.class);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(i);
                        finish();
                    }
                }
            };

            timer.start();
        }
       */
   onResume();

    }

    public void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled())
            Toast.makeText(this, "Turn ON Bluetooth", Toast.LENGTH_LONG).show();
        else {
            Intent i = new Intent(this, Connect.class);
            startActivity(i);
        }


    }
}

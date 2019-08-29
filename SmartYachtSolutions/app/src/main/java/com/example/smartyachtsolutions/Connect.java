package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;


public class Connect extends AppCompatActivity {

    private ListView lstvw;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        if (bAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            ArrayList list = new ArrayList();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String devicename = device.getName();
                    //String macAddress = device.getAddress();
                    list.add("Name: " + devicename);
                }
                lstvw = (ListView) findViewById(R.id.deviceList);
                aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                lstvw.setAdapter(aAdapter);


            }
        }

            onResume();


    }

    public void onResume() {
        super.onResume();
        if (!bAdapter.isEnabled())
            Toast.makeText(this, "Turn ON Bluetooth", Toast.LENGTH_LONG).show();
        else {
                Toast.makeText(this, "Connect with Smart Yacht Solution device", Toast.LENGTH_LONG).show();

            }
        }


    }



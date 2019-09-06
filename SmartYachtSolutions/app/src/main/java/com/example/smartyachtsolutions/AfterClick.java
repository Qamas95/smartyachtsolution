package com.example.smartyachtsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import java.util.UUID;

public class AfterClick extends AppCompatActivity {

    private ListView lstvw;
    private ArrayAdapter<BluetoothDevice> aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    Button turnLightOn, turnLightOff, turnHeaterOn, turnHeaterOff,AnchorOn,AnchorOff;
    public static final String BT_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    public OutputStream mmOutStream;
    public BluetoothDevice mmDevice;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_click);
        Serwer serw = new Serwer();
        serw.started();

           // SYSServer serw = new SYSServer();






        //  Server serv = new Server(this);
         // new Thread(serv).start();


         turnLightOn = findViewById(R.id.light_on);
         turnLightOff = findViewById(R.id.light_off);
         turnHeaterOn = findViewById(R.id.heater_on);
         turnHeaterOff = findViewById(R.id.heater_off);
         AnchorOn = findViewById(R.id.AnchorOn);
         AnchorOff = findViewById(R.id.AnchorOff);



/*
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        Iterator<BluetoothDevice> iterator = pairedDevices.iterator();
        BluetoothDevice device = iterator.next();
        BluetoothSocket socket = null;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.connect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream istream = null;
        try {
            istream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream ostream = null;
        try {
            ostream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        InputStream istream = null;


        turnLightOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serw.write("0".getBytes());
                //  mmOutStream.write("1".getBytes());

            }
        });

        turnLightOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mmOutStream.write("1".getBytes());
                } catch (IOException e) {

                }

            }
        });


        turnHeaterOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mmOutStream.write("10".getBytes());
                } catch (IOException e) {

                }

            }
        });

        turnHeaterOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mmOutStream.write("01".getBytes());
                } catch (IOException e) {

                }

            }
        });


        AnchorOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mmOutStream.write("100".getBytes());
                } catch (IOException e) {

                }

            }
        });

        AnchorOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mmOutStream.write("011".getBytes());
                } catch (IOException e) {

                }

            }
        });






    }





/*
    public void connectdv() throws IOException, InterruptedException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        Iterator<BluetoothDevice> iterator = pairedDevices.iterator();
        BluetoothDevice device = iterator.next();
        BluetoothSocket socket = null;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.connect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream istream = null;
        try {
            istream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream ostream = null;
        try {
            ostream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send
        // ostream.write("0".getBytes());


        // receive
        Thread.sleep(1000);
        int avail = istream.available();
        Log.w(this.getClass().getSimpleName(), "Received " + avail + " bytes");
        BufferedReader br = new BufferedReader(new InputStreamReader(istream));
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w(this.getClass().getSimpleName(), "Received: " + s);





    }

*/






}

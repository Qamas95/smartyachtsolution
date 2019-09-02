package com.example.smartyachtsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class AfterClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_click);


        try {
            connectdv();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }

    public class Reminder {

        Timer timer;

        public Reminder(int seconds) {
            timer = new Timer();
            timer.schedule(new RemindTask(), seconds*1000);
        }

        class RemindTask extends TimerTask {
            public void run() {
                System.out.format("Time's up!%n");
                timer.cancel(); //Terminate the timer thread
            }
        }
    }



    public void connectdv() throws IOException, InterruptedException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
        Iterator<BluetoothDevice> iterator = pairedDevices.iterator();
        BluetoothDevice device = iterator.next();
        BluetoothSocket socket = null;
        new Reminder(2);
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








}

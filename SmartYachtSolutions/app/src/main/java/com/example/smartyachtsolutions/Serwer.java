package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Serwer extends Thread {

    private static final String TAG = "Smart Yacht Solution";
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
    Iterator<BluetoothDevice> iterator = pairedDevices.iterator();
    BluetoothDevice device = iterator.next();
    BluetoothSocket socket = null;
    public OutputStream mmOutStream;

    public void started() {
        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (
                IOException e) {
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

    }


    void write(byte[] data) {

        try {
            mmOutStream = socket.getOutputStream();
            mmOutStream.write(data);
        } catch (IOException e) {

        }

    }
}





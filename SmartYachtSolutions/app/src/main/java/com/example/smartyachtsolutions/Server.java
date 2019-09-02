package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Server extends Thread {


    private BluetoothAdapter btAdapter;
    private String socketString = "a random string";
    private BluetoothServerSocket btServerSocket;
    private BluetoothSocket btConnectedSocket;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
    Iterator<BluetoothDevice> iterator = pairedDevices.iterator();
    BluetoothDevice device = iterator.next();
    BluetoothSocket socket = null;

    private final String TAG = "Server";
    private AfterClick parent;
    /*package-protected*/ static final String ACTION = "Bluetooth socket is connected";
    private boolean connected;

    public Server(AfterClick parent) {
        this.parent = parent;
        connected= false;
    }





    @Override
    public void run() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();


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

/*
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
        */


        try {
            Log.i(TAG, "getting socket from adapter");
            btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord(socketString, uuid);
            listen();

        }
        catch (IOException ex) {
            Log.e(TAG, "error while initializing");
        }
    }

    private void listen() {
        Log.i(TAG, "listening");
        btConnectedSocket = null;
        while (!connected) {
            try {
                btConnectedSocket = btServerSocket.accept();
            }
            catch (IOException ex) {
                Log.e(TAG,"connection failed");
                connectionFailed();
            }

            if (btConnectedSocket != null) {
                broadcast();
                closeServerSocket();
            }
            else {
                Log.i(TAG,  "socket is null");
                connectionFailed();
            }
        }

    }

    private void broadcast() {
        try {
            Log.i(TAG, "connected? "+btConnectedSocket.isConnected());
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra("state", btConnectedSocket.isConnected());
            parent.sendBroadcast(intent);
            connected = true;
        }
        catch (RuntimeException runTimeEx) {

        }

        closeServerSocket();
    }


    private void connectionFailed () {

    }

    public void closeServerSocket() {
        try {
            btServerSocket.close();
        }
        catch (IOException ex) {
            Log.e(TAG+":cancel", "error while closing server socket");
        }
    }














}
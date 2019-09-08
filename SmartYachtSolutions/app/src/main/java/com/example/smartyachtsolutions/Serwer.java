package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
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
    public String nconnected;






    private void connectionFailed() {
        // Send a failure message back to the Activity
        Bundle bundle = new Bundle();
        bundle.putString("nconnected", nconnected);
        // Update UI title
        // Start the service over to restart listening mode
        Serwer.this.start();
    }






    public void closeServerSocket() {
        try {
            socket.close();
        }
        catch (IOException ex) {
           // Log.e(TAG+":cancel", "error while closing server socket");
            //no adding logs yet.
        }
    }


    public void started() {
        adapter.cancelDiscovery();
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

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            uuid);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(
                            uuid);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            adapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (Serwer.this) {
            }

        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

}






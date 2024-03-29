package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
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
    public  InputStream mmInStream;
    public boolean nconnected;

    /*
    private static String data;
    public static String getData() {return data;}
    public static void setData(String data) {Serwer.data = data;}
*/

   public int var1;


    public int getVar1() {
        if (nconnected == true) {
            var1 = 1;
        }
        return var1;
    }
    //Im back from holidays aye xd 

/*
    private void connectionFailed() {
        // Send a failure message back to the Activity
        Bundle bundle = new Bundle();
        bundle.putString("nconnected", nconnected);
        // Update UI title
        // Start the service over to restart listening mode
            Serwer.this.start();
    }
    */




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
            Log.i(TAG, "Creating rfcomm socket");
            socket = device.createRfcommSocketToServiceRecord(uuid);
            Log.i(TAG, "Rfcomm socket created");
        } catch (
                IOException e) {
            e.printStackTrace();
            Log.i(TAG, "log when it's not created");
        }
        try {
            Log.i(TAG, "trying socket connect");
            socket.connect();
            nconnected = true;
            Log.i(TAG, "socket connected");
        } catch (IOException e) {
            Log.i(TAG, "if socket wasn't connected");
            try {
                Log.i(TAG, "try close socket");
                socket.close();
                Log.i(TAG, "closed socket");
            } catch (IOException e2) {
                Log.i(TAG, "log when socket not closed but tried to");
                e.printStackTrace();
            }
            Log.i(TAG, "send data about not connected socket");
           // connectionFailed();
            //setData("notConnect");
          //  return;
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

    void read(byte[] rdata){
        try {
            mmInStream = socket.getInputStream();
            mmInStream.read(rdata);
        } catch (IOException e){

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
                //connectionFailed();
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






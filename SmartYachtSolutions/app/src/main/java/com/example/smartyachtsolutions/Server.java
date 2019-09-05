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



/*
package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SYSServer {

    private static final String TAG = "Smart Yacht Solution";
    private Handler handler;

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                    Message readMsg = handler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                // Share the sent message with the UI activity.
                Message writtenMsg = handler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
            }
        }



        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }
}
 */
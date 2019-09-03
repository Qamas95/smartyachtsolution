package com.example.smartyachtsolutions;

import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


public class Connect extends AppCompatActivity {


    private ListView lstvw;
    private ArrayAdapter<BluetoothDevice> aAdapter;
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
                    String macAddress = device.getAddress();
                    list.add("Name: " + devicename);
                }
                lstvw = (ListView) findViewById(R.id.deviceList);
                aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                lstvw.setAdapter(aAdapter);


            }
        }

       /* lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //need to add here function that will run rfcomm transfer and try to connect to raspberry

                try {
                    connectdv();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(getApplicationContext(), "You clicked something", Toast.LENGTH_SHORT).show();
            }
        });
*/


        lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //need to add here function that will run rfcomm transfer and try to connect to raspberry
               Intent z=new Intent(view.getContext(),AfterClick.class);
               startActivity(z);

            }

        });


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

}











    /*

    private BluetoothServerSocket mmServerSocket;

    public void ServerBluetooth()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket tmp = null;
        try
        {
            UUID uuid=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Usluga witajaca", uuid);
        }
        catch (IOException e)
        {

        }
        mmServerSocket = tmp;
    }



    public void run_bt()
    {
        Log.d("INFO","Uruchamiam serwer");
        BluetoothSocket socket = null;
        while (true)
        {
            try
            {
                Log.d("INFO","Czekam na polaczczenie od clienta");
                socket = mmServerSocket.accept();
                Log.d("INFO","Mam clienta!");
            } catch (IOException e)
            {

                break;
            }
            if (socket != null)
            {

                try
                {
                    mmServerSocket.close();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }

*/


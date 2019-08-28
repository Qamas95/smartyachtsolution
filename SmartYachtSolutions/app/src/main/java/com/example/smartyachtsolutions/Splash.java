package com.example.smartyachtsolutions;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {




    private ImageView imagesplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imagesplash = (ImageView) findViewById(R.id.splash_image);
        Animation my_animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        imagesplash.startAnimation(my_animation);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Device does not support to Bluetooth", Toast.LENGTH_LONG).show();
            System.exit(1);
        }

        /*
        Intent discoverintent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverintent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);
        startActivity(discoverintent);*/
/*
        if(bluetoothAdapter.isEnabled()) {
           final Intent intent = new Intent(this, Connect.class);
            this.startActivity(intent);
        }

        if(!bluetoothAdapter.isEnabled()) {
           final Intent intent = new Intent(this, RunBluetooth.class);
            this.startActivity(intent);
        }
*/
         if (!bluetoothAdapter.isEnabled()) {
            final Intent i = new Intent(this, RunBluetooth.class);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(i);
                        finish();
                    }
                }
            };
            timer.start();
       } else {
             final Intent z = new Intent(this, Connect.class);
             Thread timer = new Thread() {
                 public void run() {
                     try {
                         sleep(3500);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     } finally {
                         startActivity(z);
                         finish();
                     }
                 }
             };
             timer.start();
         }

    }
}





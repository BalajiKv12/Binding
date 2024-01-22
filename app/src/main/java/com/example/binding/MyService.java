package com.example.binding;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MyService extends Service {
    // Binder given to clients (notice class declaration below)
    private final IBinder mBinder = new MyBinder();
    // Random number generator
    private final Random mGenerator = new Random();
    public MyService() {
    }


    /**
     * Class used for the client Binder. The Binder object is responsible for returning an instance
     * of "MyService" to the client.
     */
    public class MyBinder extends Binder {
        MyService getService() {
            // Return this instance of MyService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    /** method for clients to get a random number from 0 - 100 */
    public int getRandomNumber(int a,int b) {
        return a+b;
    }
}
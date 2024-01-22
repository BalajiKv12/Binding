package com.example.binding;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyService mService;
    Boolean mIsBound;
    EditText n1, n2, result;
    Button sum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1 = findViewById(R.id.n1);
        n2 = findViewById(R.id.n2);
        result = findViewById(R.id.result);
        sum = findViewById(R.id.sum);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBound) {
            unbindService(serviceConnection);
            Log.d("MyServiceExample", "ServiceConnection:Disconnected.");
            mIsBound = false;
        }
    }


    private void startService() {
        /*Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);*/

        bindService();
    }

    private void bindService() {
        Intent serviceBindIntent = new Intent(this, MyService.class);
        bindService(serviceBindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            Log.d("MyServiceExample", "ServiceConnection: connected to service.");
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            MyService.MyBinder binder = (MyService.MyBinder) iBinder;
            mService = binder.getService();
            mIsBound = true;

            getRandomNumberFromService(); // return a random number from the service
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.d("MyServiceExample", "ServiceConnection: disconnected from service.");
            mIsBound = false;
        }
    };

    private void getRandomNumberFromService() {
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(n1.getText().toString());
                int b = Integer.parseInt(n2.getText().toString());
                Log.d("MyServiceExample", "getRandomNumberFromService: Random number from service: " + mService.getRandomNumber(a, b));
                int c = mService.getRandomNumber(a, b);
                String s = Integer.toString(c);
                result.setText(s);
            }
        });

    }
}
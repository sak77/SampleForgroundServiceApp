package com.test.sampleforgroundserviceapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = findViewById(R.id.btnStartService);
        Button btnStop = findViewById(R.id.btnStopService);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                Intent startIntent = new Intent(MainActivity.this, MyForegroundService.class);
                //Action tells the onStart method to run the service in foreground
                startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                //startService(startIntent);
                /*
                startForegroundService is similar to startService. But with the implicit promise that
                the service will call startForeground() once it is started.

                Also, unlike startService(), it can be called from the app even if the app is not
                in the foreground.
                 */
                startForegroundService(startIntent);
                break;
            case R.id.btnStopService:
                Intent stopIntent = new Intent(MainActivity.this, MyForegroundService.class);
                stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(stopIntent);
                break;
            default:
                break;
        }
    }
}

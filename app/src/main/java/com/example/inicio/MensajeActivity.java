package com.example.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MensajeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        onNewIntent2(getIntent());
    }

    public void onNewIntent2(Intent intent){
        Bundle extras = intent.getExtras();
        String msg = extras.getString("cuerpo");
        Log.d("Cuerpo=>",msg);

        if(extras != null){
            if(extras.containsKey("NotificationMessage"))
            {
                //setContentView(R.layout.viewmain);
                // extract the extra-data in the Notification

            }
        }
    }
}
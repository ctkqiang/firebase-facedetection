package com.johnmelodyme.facialrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

/**
 * TODO : Update Metadata
 * @AUTHOR : JOHN MELODY MELISSA
 * @INSPIRED :
 * @TYPE: MACHINE LEARNING
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private TextView DETAIL;


    private void START_UP_DECLARATION() {
        DETAIL = findViewById(R.id.a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        START_UP_DECLARATION();
        FirebaseApp.initializeApp(MainActivity.this);
        Log.d(TAG, "FIREBASE-FACE-DETECTOR INITIATING...");
    }
}

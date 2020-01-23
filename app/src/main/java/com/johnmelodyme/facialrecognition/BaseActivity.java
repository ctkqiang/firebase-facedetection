package com.johnmelodyme.facialrecognition;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class BaseActivity extends AppCompatActivity {
    public static final int WRITE_STORAGE = 0b1100100;
    public static final int CAMERA = 0b1100110;
    public static final int SELECT_PHOTO = 0b1100111;
    public static final int TAKE_PHOTO = 0b1101000;
    public static final String ACTION_BAR_TITLE = "Facial Recognition";
    public File PHOTO_FILE;
}

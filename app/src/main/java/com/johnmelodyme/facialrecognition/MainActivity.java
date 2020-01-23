package com.johnmelodyme.facialrecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.johnmelodyme.facialrecognition.Screen.Screenshot;
import com.johnmelodyme.facialrecognition.VisionHelper.GraphicOverlay;
import com.johnmelodyme.facialrecognition.VisionHelper.ReactOverlay;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * TODO : Update Metadata
 *
 * @AUTHOR : JOHN MELODY MELISSA
 * @INSPIRED : Sin Dee, My Babe
 * @TYPE: MACHINE LEARNING || Facial Recognition
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Button DETECT_FACE_BTN;
    private GraphicOverlay GRAPHICAL_OVERLAY;
    private CameraView CAMERAVIEW;


    private void START_UP_DECLARATION() {
        DETECT_FACE_BTN = findViewById(R.id.detect_btn);
        GRAPHICAL_OVERLAY = findViewById(R.id.GRAPHIC_OVERLAY);
        CAMERAVIEW = findViewById(R.id.cameraView);
    }

    @Override
    protected void onPause(){
        super.onPause();
        CAMERAVIEW.stop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        CAMERAVIEW.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        START_UP_DECLARATION();
        FirebaseApp.initializeApp(MainActivity.this);
        Log.w(TAG, "FIREBASE-FACE-DETECTOR INITIATING...");
        // High-accuracy landmark detection and face classification
        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        // Real-time contour detection of multiple faces
        FirebaseVisionFaceDetectorOptions realTimeOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                        .build();
//

        DETECT_FACE_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CAMERAVIEW.start();
                CAMERAVIEW.captureImage();
                GRAPHICAL_OVERLAY.clear();

                Bitmap b;
                b = Screenshot.takescreenshotOfRootView(CAMERAVIEW);
            }
        });

        CAMERAVIEW.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, CAMERAVIEW.getWidth(), CAMERAVIEW.getHeight(), false);
                CAMERAVIEW.stop();

                processFaceDetection(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void processFaceDetection(Bitmap bitmap) {

        FirebaseVisionImage firebaseVisionImage;
        firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions;
        firebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions
                .Builder()
                .build();
        final FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        getFaceResults(firebaseVisionFaces);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String ERR;
                ERR = e.getMessage();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText(ERR)
                        .show();

            }
        });
    }

    private void getFaceResults(List<FirebaseVisionFace> firebaseVisionFaces) {
        int counter = 0;

        for (FirebaseVisionFace firebaseVisionFace : firebaseVisionFaces){
            Rect r = firebaseVisionFace.getBoundingBox();
            ReactOverlay ro = new ReactOverlay(GRAPHICAL_OVERLAY, r);

            GRAPHICAL_OVERLAY.add(ro);

            counter = counter + 1;
        }
    }
}

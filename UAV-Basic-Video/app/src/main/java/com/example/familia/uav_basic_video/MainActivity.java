package com.example.familia.uav_basic_video;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.android.Utils;
import org.opencv.imgproc.Imgproc;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {

    private String Cameras[];
    private CameraManager manager;
    private CameraDevice.StateCallback camera_state;

    private Handler handler;


    private static final String TAG = "MAIN-ACTIVITY";

    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_WRITE = 1;

    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        try{
            System.loadLibrary("opencv_java3");
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause()
    {
        //Do on hold logic here
        super.onPause();
    }


    @Override
    protected void onDestroy(Bundle savedInstanceState)
    {
        super.onDestroy();
    }

    private int[] permissionsResults()
    {

        return null;
    }

    private void askPermissions()
    {

        try{

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}


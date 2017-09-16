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

    private ImageView v1, v2;
    private Button button;
    
    private static final String TAG = "MAIN-ACTIVITY";
    private static final String FRAGMENT_DIALOG = "dialog";
    
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_WRITE = 1;

    private View mLayout;
    private File f;                                            // File used to store captures
    private HandlerThread backThread;
    
    private Semaphore OpenCloseCameraLock = new Semaphore(1); //Semaphore to handle capture requests
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(); 
        
        try{
            System.loadLibrary("opencv_java3");
            
            //These are yet to be added to the XML File!!!!
            //Will give an Error when trying to run
            v1 = (ImageView)findViewById(R.id.v1);
            v2 = (ImageView)findViewById(R.id.v2);
            
            button = (Button)findViewById(R.id.button);
            button.setOnClickListener(this);
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onClick(View v)
    {
        //do protocol on click here
        return;
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
        if(FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        } else{
                FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                                                 REQUEST_CAMERA_PERMISSION);
            }
     
    }
    
    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(reqCode == REQUEST_CAMERA_PERMISSION)
        {
            if(grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                ErrorDialog.newInstance("CAMERA PERMISSION REQUIRED").show(getChildFragmentManager(), FRAGMENT_DIALOG);
            }
        }else{
                super.onRequestPermissionsresult(reqCode, permissions, grantResults);
           }
    }

    private Camera getCamera(String[] cams)
    {

        
    }
    
    private void openCamera(int width, int height)
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            askPermissions();
        
        //Setup camera here
        Acivity act = getActivity();
        CameraManager man = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        
        try {/* Will not work until background thread is implemented
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            manager.openCamera(mCameraId, mStateCallback, mBackgroundHandler);*/
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }
    
    private void closeCamera()
    {
        try{
        
        } catch(InterruptedException e)
            {
                throw new RuntimeException("Interrupted while attempting camera lock!", e);
            }  finally{
                    //Should release lock of thread here
                }
        
    }

    private void startBackgroundThread()
    {
        backThread = new HandlerThread("CameraBackground");
        backThread.start();
        handler = new Handler(backThread.getLooper());
    }
    
    private void stopBackgroundThread()
    {
        backThread.quitSafely();
        
        try{
            backThread.join();
            backThread = null;
            handler = null;
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
}


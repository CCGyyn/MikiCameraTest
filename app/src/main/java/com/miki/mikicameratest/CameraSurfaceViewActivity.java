package com.miki.mikicameratest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * @author cai_gp
 */
public class CameraSurfaceViewActivity extends AppCompatActivity{

    // 6.0
    /**
     * 声明相机
     */
    private Camera mCamera;
    private CameraManager cameraManager;
    private Handler childHandler, mainHandler;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mSession;
    private ImageReader mImageReader;
    /**
     * 创建拍照需要的CaptureRequest.Builder
     */
    private CaptureRequest.Builder captureRequestBuilder;
    private MyCamera cameraSurfaceView;
    private Button take;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_surface_view);
        cameraSurfaceView = (MyCamera) findViewById(R.id.camera_surfaceView);
        take = (Button) findViewById(R.id.take);
    }

    @Override
    public synchronized ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
        return null;
    }

    public static void actionStart(Context context, String data) {
        Intent intent = new Intent(context, CameraSurfaceViewActivity.class);
        intent.putExtra("autoFocusingCount", data);
        context.startActivity(intent);
    }
}

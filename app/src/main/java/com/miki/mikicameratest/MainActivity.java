package com.miki.mikicameratest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author cai_gp
 */
public class MainActivity extends AppCompatActivity {
    /**
     * 相机自动对焦测试次数
     */
    private EditText autoFocusingCount;

    private Button autoFocusingButton;

    private Context mContext;
    /**
     * 自动对焦默认一万次
     */
    private final int autoCount = 10000;
    /**
     *  相机权限
     */
    private final int REQUEST_CAMERA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        autoFocusingCount = (EditText) findViewById(R.id.auto_focusing_count);
        autoFocusingButton = (Button) findViewById(R.id.auto_focusing_button);
        // 默认数字键盘
        autoFocusingCount.setRawInputType(Configuration.KEYBOARD_QWERTY);

        mContext = this;

        autoFocusingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoFocusingCount.getText().toString().isEmpty()) {
                    autoFocusingCount.setError("输入不能为空");
                    return;
                }
                if(ContextCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED) {
                    CameraSurfaceViewActivity.actionStart(mContext, autoFocusingCount.getText().toString());
                } else {
                    ActivityCompat.requestPermissions(
                            (Activity) mContext,
                            new String[] {
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            REQUEST_CAMERA
                    );
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraSurfaceViewActivity.actionStart(mContext, autoFocusingCount.getText().toString());
                }
                break;
            default:
                break;
        }
    }
}

package com.example.glsurfaceviewtest;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CameraGLSurfaceView glSurfaceView = null;
    ImageButton shutterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView = (CameraGLSurfaceView)findViewById(R.id.camera_textureview);
        shutterBtn = (ImageButton)findViewById(R.id.btn_shutter);
        initViewParams();
        //拍照
        shutterBtn.setOnClickListener(new BtnListeners());
    }

    private void initViewParams(){
        ViewGroup.LayoutParams params;
        ViewGroup.LayoutParams p2;
        params = glSurfaceView.getLayoutParams();
        Point p = getScreenMetrics(this);
        params.width = p.x; //view宽
        params.height = p.y; //view高
        //设置GLSurfaceView的宽和高
        glSurfaceView.setLayoutParams(params);
        //设置ImageButton的大小
        p2 = shutterBtn.getLayoutParams();
        p2.width = 100;
        p2.height = 100;
        shutterBtn.setLayoutParams(p2);
    }

    private Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    //拍照
    private class BtnListeners implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_shutter:
                    CameraInterface.getInstance().doTakePicture();
                    break;
                default:break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更改视图在树中的z顺序，因此它位于其他同级视图之上。
        glSurfaceView.bringToFront();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

}

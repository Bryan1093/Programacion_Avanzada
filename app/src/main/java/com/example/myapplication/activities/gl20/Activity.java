package com.example.myapplication.activities.gl20;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.renderes.gl10.RenderPlanoIluminacionText;

public class Activity extends android.app.Activity {

    private GLSurfaceView view;
    private GLSurfaceView.Renderer renderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        renderer = new RenderPlanoIluminacionText(this);
        view.setRenderer(renderer);
        setContentView(view);

        //en clase
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
        super.onPause();

    }

    @Override
    protected void onResume() {

        Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
        super.onResume();
    }
}
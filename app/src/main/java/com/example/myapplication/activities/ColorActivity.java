package com.example.myapplication.activities;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.renderes.CuboPushPopV1;
import com.example.myapplication.renderes.RenderEsferaAmarilla;
import com.example.myapplication.renderes.RenderSemaforo;

public class ColorActivity extends Activity{

    private GLSurfaceView view;
    private GLSurfaceView.Renderer renderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        view = new GLSurfaceView(this);
        //renderer = new ColorRenderer();
        //Este linea de codigo,es para los coloresvie
        view.setEGLContextClientVersion(1);
        //renderer = new ColorRenderer(new float[]{150/255f,179/255f,73/255f,1f});
        //esta linea de codigo es para dibujar
        //renderer = new RendererPushPop();
        renderer = new RenderSemaforo();
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
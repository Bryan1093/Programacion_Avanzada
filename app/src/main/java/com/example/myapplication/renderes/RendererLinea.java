package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.Linea;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class RendererLinea implements GLSurfaceView.Renderer {

    private Linea linea;
    private float rotacion;
    private float traslacion;
    private float traslacionSpeed = 0.8f;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.4f,0.3f,0.7f,1.0f);
        linea = new Linea();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width , int height) {
        gl10.glViewport(0,0,width, height);
        gl10.glFrustumf(-8,8,-8,8,1,30);
        //Para usar una matriz de proyeccion
        gl10.glMatrixMode(gl10.GL_PROJECTION);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        //ActualizarPosicion

        traslacion += traslacionSpeed;

        gl.glTranslatef(0.0f,0.0f,-3.0f);
        //gl.glTranslatef(traslacion, 0.0f, -3.0f);
        linea.dibujar(gl);

        gl.glRotatef(180,0,1,0);
        //gl.glTranslatef(0.0f, 0.0f, 0.0f);
        //Linea.dibujar(gl);


        rotacion += 0.1f;
        traslacion += 0.1f;
    }
}

package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.Figuras;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class RendererFiguras implements GLSurfaceView.Renderer {
    private Figuras figuras;
    private float rotacion;
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(1.0f,1.0f,1.0f,1.0f);
        figuras = new Figuras();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        gl10.glViewport(0,0,ancho,alto);
        gl10.glFrustumf(-5,5,-5,5,1,30);
        gl10.glMatrixMode(gl10.GL_PROJECTION);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        //gl.glRotatef(rotacion,0,1,0);
        gl.glTranslatef(0,0,-3);
        figuras.dibujar(gl);
    }
}
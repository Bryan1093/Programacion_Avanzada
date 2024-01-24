package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.Plano;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderPlano implements GLSurfaceView.Renderer {
    private Plano xAxis, yAxis;
    private float rotacion;
    private Plano rectangulo,rectangulo1;

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.8f,0.4f,0.6f,1.0f);
        // Ajusta las longitudes según sea necesario
        xAxis = new Plano(5.0f, 0.2f);
        yAxis = new Plano(0.2f, 5.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        gl10.glViewport(0, 0, ancho, alto);
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glFrustumf(-10, 10, -10, 10, 1, 30);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        {
            // Dibuja el sistema de coordenadas
            gl.glLoadIdentity();
            gl.glTranslatef(-2, -2, 0);
            gl.glScalef(0.3f, 0.8f, 0.3f);
            xAxis.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glLoadIdentity();
            gl.glTranslatef(-2, -2, 0);
            gl.glRotatef(90, 1, 0, 0);
            gl.glScalef(0.3f, 0.8f, 0.3f);
            yAxis.dibujar(gl);
        }
        gl.glPopMatrix();
    }

}

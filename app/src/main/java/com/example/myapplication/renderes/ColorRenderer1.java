package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.Punto;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




public class ColorRenderer1 implements GLSurfaceView.Renderer {

    private Punto punto;
    private float rotacion;
    private float traslacion;
    private float traslacionSpeed = 0.8f;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.5f,0.2f,0.7f,1.0f);
        punto = new Punto();
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

        // Verifica y maneja el rebote
        if (traslacion > 75.0f || traslacion < -75.0f) {
            traslacionSpeed *= -1.0f; // Cambia la dirección
        }

        gl.glTranslatef(traslacion, 0.0f, -10.0f);
        punto.dibujar(gl);

        rotacion += 0.1f;
        /*gl.glTranslatef(traslacion,0.0f,-10.0f);
        //gl.glTranslatef((float)Math.sin(traslacion),0.0f,-10.0f);
        //gl.glRotatef(rotacion,0,1,0);
        punto.dibujar(gl);*/
    }
}

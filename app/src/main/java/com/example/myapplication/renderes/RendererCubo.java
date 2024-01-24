package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.myapplication.modelos.Cubo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class RendererCubo implements GLSurfaceView.Renderer {
    private Cubo cubo;
    private float rotacionCubo1 = 0;
    private float rotacionCubo2 = 0;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST); // Habilitar Z-buffering
        gl10.glDepthFunc(GL10.GL_LEQUAL);  // Establecer la función de comparación para el Z-buffer
        cubo = new Cubo();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        float relacionAspecto = (float) ancho / (float) alto;
        gl10.glViewport(0, 0, ancho, alto);
        gl10.glFrustumf(-relacionAspecto, relacionAspecto, -1, 1, 1, 30);
        gl10.glMatrixMode(gl10.GL_PROJECTION);

        //parametros de la camara
        GLU.gluLookAt(gl10,
                0.0f,2.0f,10.f,
                0.0f, 0.0f, 0.0f,
                0,1,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Posición del primer cubo (cubo estacionario)
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        gl.glRotatef(rotacionCubo1, 0, 1, 0);
        cubo.dibujar(gl);

        // Posición y rotación del segundo cubo (gira alrededor del primero, en sentido contrario)
        gl.glLoadIdentity();
        gl.glTranslatef(8.0f * (float) Math.sin(rotacionCubo2), 0.0f, -10.0f + 5.0f * (float) Math.cos(rotacionCubo2));
        gl.glRotatef(rotacionCubo2, 0, 2, 2); // Cambio del signo para girar en sentido contrario alrededor del eje Y
        gl.glScalef(2.0f, 2.0f, 2.0f); // Ajusta la escala si es necesario
        cubo.dibujar(gl);

        rotacionCubo1 -= 0.8f; // Rotación del primer cubo (estacionario)
        rotacionCubo2 += 0.02f; // Ajusta la velocidad de rotación del segundo cubo
    }

    // Función para determinar si el segundo cubo debe ser dibujado
    private boolean debeDibujarSegundoCubo(float rotation) {
        // Puedes ajustar la lógica de dibujo según tus necesidades
        return Math.abs(rotation % 360) < 180;
    }
}

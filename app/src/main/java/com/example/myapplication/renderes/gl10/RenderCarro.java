package com.example.myapplication.renderes.gl10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.myapplication.modelos.gl10.CuboPushPop;
import com.example.myapplication.modelos.gl10.Esfera;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderCarro implements GLSurfaceView.Renderer {
    private Esfera llanta1,llanta2,llanta3,llanta4;
    private CuboPushPop cubo1,rectangulo;
    private float rotacion = 0;
    private float anguloRotacion1 = 0;
    private float translacion = 0;
    private float translacionDelta = 0.2f; // Incremento de translación por fotograma

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        gl10.glClearColor(0.524f, 0.7059f, 0.79f, 1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glEnable(GL10.GL_BLEND);
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        llanta1 = new Esfera(10, 10, 0.5f, 1.0f, new float[]{0.7f, 0.5f, 0.3f, 0.5f, 0.6f, 0.4f, 0, 1});
        llanta2 = new Esfera(0, 0, 0.5f, 1.0f, new float[]{1, 0, 0, 1, 1, 1, 0, 1});
        llanta3 = new Esfera(0, 0, 0.5f, 1.0f, new float[]{0.7f, 0.5f, 0.3f, 0.5f, 0.6f, 0.4f, 0, 1});
        llanta4 = new Esfera(0, 0, 0.5f, 1.0f, new float[]{1, 0, 0, 1, 1, 1, 0, 1});
        cubo1 = new CuboPushPop();
        rectangulo = new CuboPushPop();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        // Configuración cuando cambia el tamaño de la superficie de renderizado
        float relacionAspecto = (float) ancho / (float) alto;

        gl10.glViewport(0, 0, ancho, alto);
        gl10.glFrustumf(-relacionAspecto, relacionAspecto, -1, 1, 1, 30);
        gl10.glMatrixMode(gl10.GL_PROJECTION);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl,
                0.0f, 12.0f, -15.0f,   // Posición de la cámara
                0.0f, 0.0f, 0.0f,     // Punto de mira
                0, 1, 0);              // Orientación de la cámara (eje Y arriba)

        // Aplicar rotación a todas las figuras alrededor del eje Y
        gl.glRotatef(rotacion, 0.0f, 1.0f, 0.0f);

        // Figura en el cuadrante inferior izquierdo
        gl.glPushMatrix();
        {
            gl.glTranslatef(1.0f, 0.0f, 0.0f);
            gl.glScalef(3.0f, 2.0f, 2.0f);
            cubo1.dibujar(gl);
        }
        gl.glPopMatrix();

        // Figura en el cuadrante inferior derecho
        gl.glPushMatrix();
        {
            gl.glTranslatef(3.0f, -3.0f, 0.0f);
            gl.glScalef(5.0f, 1.0f, 2.0f);
            rectangulo.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 1
        gl.glPushMatrix();
        {
            gl.glTranslatef(5.0f, -9.0f, 0.0f);
            gl.glScalef(2f, 2f, 2f);
            llanta1.dibujar(gl);
        }
        gl.glPopMatrix();

        /*// LLANTA 2
        gl.glPushMatrix();
        {
            gl.glTranslatef(1.0f, 1.0f, -3.0f);
            gl.glScalef(1.3f, 1.3f, 1.3f);
            //esfera.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 3
        gl.glPushMatrix();
        {
            gl.glTranslatef(-1.0f, 1.0f, -3.0f);
            gl.glScalef(1.6f, 0.5f, 1.2f);
            //elipsoide.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 4
        gl.glPushMatrix();
        {
            gl.glTranslatef(1.0f, 1.0f, -3.0f);
            gl.glScalef(1.3f, 1.3f, 1.3f);
            //esfera.dibujar(gl);
        }
        gl.glPopMatrix();*/

        /*// Plano de abajo
        gl.glPushMatrix();{
            gl.glTranslatef(-1, 0, -2);
            gl.glScalef(1.0f, 0.01f, 2.0f);
            rectangulo1.dibujar(gl);
        }gl.glPopMatrix();

        // plano de arriba
        gl.glPushMatrix();{
            gl.glTranslatef(0, 0, -2);
            gl.glScalef(0.01f, 2.0f, 2.0f);
            rectangulo2.dibujar(gl);
        }gl.glPopMatrix();

        // plano que corta arriba
        gl.glPushMatrix();{
            gl.glTranslatef(-1, 0, -2);
            gl.glScalef(1.0f, 2.0f, 0.01f);
            rectangulo3.dibujar(gl);
        }gl.glPopMatrix();*/


        // Actualizar el valor de rotación para el próximo fotograma
        rotacion += 1.0f;
        translacion += translacionDelta;
        anguloRotacion1 -= 0.02f;
    }
}

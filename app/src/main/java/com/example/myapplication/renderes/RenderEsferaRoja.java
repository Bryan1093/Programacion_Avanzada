package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.myapplication.modelos.Cubo;
import com.example.myapplication.modelos.CuboLuz;
import com.example.myapplication.modelos.EsferaLuz;
import com.example.myapplication.modelos.PlanoIluminacion;
import com.example.myapplication.utilidades.Funciones;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


    public class RenderEsferaRoja implements GLSurfaceView.Renderer {
    private float rotacion = 0;
    private EsferaLuz esferaLuz;
    private float traslacion =-5.0f;

    private static final int LUZ_O = GL10.GL_LIGHT0;
    private float[] posLuz0 = {0.5f, 0.5f, 0.5f,1.0f};
    private float[] colorLuz0 = {1.0f, 0.0f, 0.0f,1.0f};

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST); // Habilitar Z-buffering
        gl.glDepthFunc(GL10.GL_LEQUAL);  // Establecer la función de comparación para el Z-buffer

        esferaLuz = new EsferaLuz(30, 30, 1.5f, 1.0f,new float[]{0, 0, 0, 1, 0, 0, 0, 1});


        gl.glLightfv(LUZ_O, gl.GL_POSITION, Funciones.generarBuffer(posLuz0));
        gl.glLightfv(LUZ_O,gl.GL_AMBIENT,Funciones.generarBuffer(colorLuz0));
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(LUZ_O);

        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(LUZ_O);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        // Configuración cuando cambia el tamaño de la superficie de renderizado
        float relacionAspecto = (float) ancho / (float) alto;

        gl10.glViewport(0, 0, ancho, alto);

        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();
        GLU.gluPerspective(gl10, 80.0f, relacionAspecto, 1, 150);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl,
                0.0f, 10.0f, -10.0f,   // Posición de la cámara
                -1.0f, 0.0f, 0.0f,     // Punto de mira
                0 , 15, 0);              // Orientación de la cámara (eje Y arriba)

        // Aplicar rotación a todas las figuras alrededor del eje Y
        gl.glRotatef(rotacion, 0.0f, 1.0f, 0.0f);

        gl.glLoadIdentity();
        posLuz0 = new float[]{0.0f,traslacion,-3.0f,1.0f};
        gl.glLightfv(LUZ_O, gl.GL_POSITION, Funciones.generarBuffer(posLuz0));
        gl.glLightfv(LUZ_O,gl.GL_AMBIENT,Funciones.generarBuffer(colorLuz0));

        gl.glTranslatef(0.0f, 0.0f ,-3.0f );
        gl.glScalef( 0.5f, 0.5f, 0.5f);
        gl.glRotatef(rotacion, 1, 1, 0);
        esferaLuz.dibujar(gl);

        rotacion += 0.8f; // Rotación del primer cubo (estacionario)
        traslacion += 0.01f; // Ajusta la velocidad de rotación del segundo cubo
    }

}
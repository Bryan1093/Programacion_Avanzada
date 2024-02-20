package com.example.myapplication.renderes.gl10;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.gl10.EsferaIlu;
import com.example.myapplication.modelos.gl10.Rectangulo;
import com.example.myapplication.utilidades.Funciones;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderSemaforo implements GLSurfaceView.Renderer {

    private EsferaIlu esfera;
    private Rectangulo rectangulo;
    private float rotacionCubo1 = 0;
    private float anguloRotacion1 = 0;
    private static final int LUZ_O = GL10.GL_LIGHT0;
    private static final int LUZ_1 = GL10.GL_LIGHT1;

    private int estadoSemaforo = 0; // 0: Verde, 1: Naranja, 2: Rojo
    private float traslation = 0;
    private float[] posLuzVerde = {0.0f, -1.0f, -3.0f, 1.0f};
    private float[] colorLuzVerde = {0.0f, 1.0f, 0.0f, 1.0f};

    private float[] posLuzNaranja = {0.0f, -1.0f, -7.0f, 1.0f};
    private float[] colorLuzNaranja = {1.0f, 0.5f, 0.0f, 1.0f};

    private float[] posLuzRoja = {0.0f, -1.0f, -9.0f, 1.0f};
    private float[] colorLuzRoja = {1.0f, 0.0f, 0.0f, 1.0f};

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        esfera = new EsferaIlu(30, 30, 1.5f, 1.0f);
        rectangulo = new Rectangulo();


        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(LUZ_O);
        gl.glEnable(LUZ_1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float relacionAspecto = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glFrustumf(-relacionAspecto, relacionAspecto, -1, 1, 1, 80);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glDisable(GL10.GL_LIGHTING);

        gl.glPushMatrix();{
        // Tapa Derecha
        gl.glPushMatrix();
        gl.glTranslatef(1.5f, 0, -5);
        gl.glScalef(0.1f, 3.0f, 0.1f);
        // Configura el material para que sea blanco
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
        rectangulo.dibujar(gl);
        gl.glPopMatrix();

        // Tapa Izquierda
        gl.glPushMatrix();
        gl.glTranslatef(-1.5f, 0, -5);
        gl.glScalef(0.1f, 3.0f, 0.1f);
        // Configura el material para que sea blanco
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
        rectangulo.dibujar(gl);
        gl.glPopMatrix();

        // Tapa Arriba
        gl.glPushMatrix();
        gl.glTranslatef(-0.8f, -3, -5);
        gl.glScalef(0.9f, 0.1f, 0.1f);
        // Configura el material para que sea blanco
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
        rectangulo.dibujar(gl);
        gl.glPopMatrix();

        // Tapa Abajo
        gl.glPushMatrix();
        gl.glTranslatef(-0.8f, 3, -5);
        gl.glScalef(0.9f, 0.1f, 0.1f);
        // Configura el material para que sea blanco
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
        rectangulo.dibujar(gl);
        gl.glPopMatrix();

        }gl.glPopMatrix();

        gl.glEnable(GL10.GL_LIGHTING);

        switch (estadoSemaforo) {
            case 0: // Verde
                gl.glLightfv(LUZ_O, GL10.GL_POSITION, Funciones.generarBuffer(posLuzVerde));
                gl.glLightfv(LUZ_O, GL10.GL_AMBIENT, Funciones.generarBuffer(colorLuzVerde));
                gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, -1.5f, -4.0f);
                gl.glScalef(0.3f, 0.3f, 0.3f);
                esfera.dibujar(gl);
                break;

            case 1: // Naranja
                gl.glLightfv(LUZ_O, GL10.GL_POSITION, Funciones.generarBuffer(posLuzNaranja));
                gl.glLightfv(LUZ_O, GL10.GL_AMBIENT, Funciones.generarBuffer(colorLuzNaranja));
                gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, -4.0f);
                gl.glScalef(0.3f, 0.3f, 0.3f);
                esfera.dibujar(gl);
                break;

            case 2: // Rojo
                gl.glLightfv(LUZ_O, GL10.GL_POSITION, Funciones.generarBuffer(posLuzRoja));
                gl.glLightfv(LUZ_O, GL10.GL_AMBIENT, Funciones.generarBuffer(colorLuzRoja));
                gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, Funciones.generarBuffer(new float[]{1.0f, 1.0f, 1.0f, 1.0f}));
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 1.5f, -4.0f);
                gl.glScalef(0.3f, 0.3f, 0.3f);
                esfera.dibujar(gl);
                break;
        }

        rotacionCubo1 += 0.2f;
        anguloRotacion1 += 0.3f;

        // Cambia el estado del semáforo después de un tiempo (ajusta según sea necesario)
        if (rotacionCubo1 > 50) {
            rotacionCubo1 = 0;
            anguloRotacion1 = 0;
            estadoSemaforo = (estadoSemaforo + 1) % 3;
        }
    }
}

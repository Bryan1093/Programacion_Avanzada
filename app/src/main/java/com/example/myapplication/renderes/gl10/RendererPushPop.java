package com.example.myapplication.renderes.gl10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.myapplication.modelos.gl10.CuboPushPop;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RendererPushPop implements GLSurfaceView.Renderer {
    private CuboPushPop cubo1, cubo2, cubo3, cubo4;
    private float translacion = 0;
    private float anguloRotacion = 0;
    private float translacionDelta = 0.5f; // Incremento de translación por fotograma
    private static final float MAX_TRANSLATION = 10.0f; // Límite superior de translación
    private static final float MIN_TRANSLATION = -10.0f; // Límite inferior de translación

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Configuración inicial cuando se crea la superficie de renderizado
        gl10.glClearColor(0.9f, 0.9f, 0.9f, 1.0f); // Establecer el color de fondo
        gl10.glEnable(gl10.GL_DEPTH_BUFFER_BIT); // Habilitar el búfer de profundidad
        cubo1 = new CuboPushPop(); // Crear instancias de cubos
        cubo2 = new CuboPushPop();
        cubo3 = new CuboPushPop();
        cubo4 = new CuboPushPop();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        // Configuración cuando cambia el tamaño de la superficie de renderizado
        float relacionAspecto = (float) ancho / (float) alto;

        gl10.glViewport(0, 0, ancho, alto);
        gl10.glFrustumf(-relacionAspecto, relacionAspecto, -1, 1, 1, 30);
        gl10.glMatrixMode(gl10.GL_PROJECTION);

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl10,
                0.0f, 5.0f, -12.0f,   // Posición de la cámara
                0.0f, 0.0f, 0.0f,     // Punto de mira
                0, 1, 0);              // Orientación de la cámara (eje Y arriba)
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Limpiar el búfer de color y el búfer de profundidad
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

        // Configurar la matriz de vista (cámara) y restablecer la identidad
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Aplicar rotación a la matriz de vista (cámara) alrededor del eje Y
        gl.glPushMatrix();
        gl.glRotatef(anguloRotacion, 0, 1, 0);

        // Dibujar el primer cubo sin aplicar rotación individual
        cubo1.dibujar(gl);
        gl.glPopMatrix(); // Restaurar la matriz de vista al estado anterior

        // Trasladar y escalar el segundo cubo
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 3.0f, 1.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        cubo2.dibujar(gl);
        gl.glPopMatrix(); // Restaurar la matriz de vista al estado anterior

        // Lógica para cambiar la dirección de translación cuando alcanza los límites
        if (translacion >= MAX_TRANSLATION || translacion <= MIN_TRANSLATION) {
            translacionDelta *= -1; // Cambiar la dirección
        }

        // Trasladar y escalar el cuarto cubo (movimiento arriba y abajo)
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, translacion, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, 0.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        cubo4.dibujar(gl);
        gl.glPopMatrix(); // Restaurar la matriz de vista al estado anterior

        // Restablecer la matriz de vista a la identidad y trasladar y escalar el tercer cubo
        gl.glLoadIdentity();
        gl.glTranslatef(-2.0f, 3.0f, 1.f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        cubo3.dibujar(gl);

        // Actualizar el valor de translación para el próximo fotograma
        translacion += translacionDelta;
        anguloRotacion += 1.0f;
    }

}
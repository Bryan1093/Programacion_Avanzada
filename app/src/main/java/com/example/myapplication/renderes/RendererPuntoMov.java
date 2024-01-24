package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.PuntoMov;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




public class RendererPuntoMov implements GLSurfaceView.Renderer {

    private PuntoMov puntoMov;
    private float rotacion;
    private float traslacion;
    private float traslacionSpeed = 0.8f;

    private boolean hayMovimiento;
    private int direccionMovimiento;
    private int numPuntos;
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        puntoMov = new PuntoMov(6, false, 0); //Ajustar a como se necesite
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0, 0, width, height);
        gl10.glFrustumf(-8, 8, -8, 8, 1, 30);
        // Para usar una matriz de proyección
        gl10.glMatrixMode(gl10.GL_PROJECTION);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        dibujarMovimientoCuadrado(gl);

        rotacion += 0.1f;
    }

    private void dibujarMovimientoCuadrado(GL10 gl) {
        if (hayMovimiento) {
            if (direccionMovimiento == 0) {
                // Movimiento en sentido horario (derecha, abajo, izquierda, arriba)
                gl.glMatrixMode(gl.GL_MODELVIEW);
                gl.glLoadIdentity();

                // Derecha
                gl.glTranslatef(traslacion, 0.0f, -10.0f);
                puntoMov.dibujar(gl);

                // Abajo
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, -traslacion, -10.0f);
                puntoMov.dibujar(gl);

                // Izquierda
                gl.glLoadIdentity();
                gl.glTranslatef(-traslacion, 0.0f, -10.0f);
                puntoMov.dibujar(gl);

                // Arriba
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, traslacion, -10.0f);
                puntoMov.dibujar(gl);

                traslacion += traslacionSpeed;

                // Verifica y maneja el rebote
                if (traslacion > 75.0f || traslacion < -75.0f) {
                    traslacionSpeed *= -1.0f; // Cambia la dirección
                }
            } else if (direccionMovimiento == 1) {
                // Movimiento en sentido anti horario (izquierda, abajo, derecha, arriba)

                gl.glMatrixMode(gl.GL_MODELVIEW);
                gl.glLoadIdentity();

                // Izquierda
                gl.glTranslatef(-traslacion, 0.0f, -10.0f);
                puntoMov.dibujar(gl);

                // Abajo
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, -traslacion, -10.0f);
                puntoMov.dibujar(gl);

                // Derecha
                gl.glLoadIdentity();
                gl.glTranslatef(traslacion, 0.0f, -10.0f);
                puntoMov.dibujar(gl);

                // Arriba
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, traslacion, -10.0f);
                puntoMov.dibujar(gl);

                traslacion += traslacionSpeed;

                // Verifica y maneja el rebote
                if (traslacion > 75.0f || traslacion < -75.0f) {
                    traslacionSpeed *= -1.0f; // Cambia la dirección
                }
            } else {
                // Si hayMovimiento es false, simplemente dibuja el cuadrado sin aplicar movimiento
                gl.glMatrixMode(gl.GL_MODELVIEW);
                gl.glLoadIdentity();
                puntoMov.dibujar(gl);
            }
        }
    }
}

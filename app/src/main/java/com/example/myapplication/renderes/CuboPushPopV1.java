package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import com.example.myapplication.modelos.Cubo;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CuboPushPopV1 implements GLSurfaceView.Renderer {
    private Cubo cubo1, cubo2, cubo3, cubo4;
    private float translacion = 1;
    private float anguloGiro1 = 0;
    private float translacionDelta = 0.1f; // Incremento de translación por fotograma
    private static final float MAX_TRANSLATION = 10.0f; // Límite superior de translación
    private static final float MIN_TRANSLATION = -10.0f; // Límite inferior de translación

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Configuración inicial cuando se crea la superficie de renderizado
        gl10.glClearColor(0.5f, 0.6f, 0.3f, 1.0f); // Establecer el color de fondo
        gl10.glEnable(gl10.GL_DEPTH_BUFFER_BIT); // Habilitar el búfer de profundidad
        cubo1 = new Cubo(); // Crear instancias de cubos
        cubo2 = new Cubo();
        cubo3 = new Cubo();
        cubo4 = new Cubo();
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

        // Cálculo de la posición de la cámara en un círculo alrededor de la escena
        float camX = (float)Math.sin(anguloGiro1) * 3.0f;
        float camZ = (float)Math.cos(anguloGiro1) * 3.0f;

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl,
                camX, 1.0f, camZ,   // Posición de la cámara
                0.0f, 1.0f, 0.0f,   // Punto de mira
                0, 1, 0);            // Orientación de la cámara (eje Y arriba)

        // Dibujar el primer cubo sin aplicar rotación individual
        gl.glPushMatrix();
        
        cubo1.dibujar(gl);

        gl.glPopMatrix(); // Restaurar la matriz de vista al estado anterior

        //cubo1: Este cubo se dibuja en la posición original (0,0,0)
        // del sistema de coordenadas. No se le aplica ninguna
        // transformación de traslación ni de escalamiento,
        // por lo que se dibuja en su tamaño y posición original.

        // Trasladar y escalar el segundo cubo
        gl.glPushMatrix();

        gl.glTranslatef(0.0f, 3.0f, 1.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        cubo2.dibujar(gl);

        gl.glPopMatrix(); // Restaurar la matriz de vista al estado anterior

        //cubo2: Este cubo se traslada 3 unidades en el eje y (hacia arriba)
        // y 1 unidad en el eje z (hacia dentro de la pantalla).
        // Luego, se le aplica un escalamiento de 0.5 en todos los ejes,
        // lo que hace que sea la mitad de su tamaño original.
        // Como resultado, este cubo se dibuja más pequeño
        // y se sitúa sobre cubo1.

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

        //cubo4: Este cubo se traslada en el eje y de acuerdo al valor
        // de translacion, lo que hace que se mueva hacia arriba
        // y hacia abajo entre MAX_TRANSLATION y MIN_TRANSLATION.
        // No se le aplica ninguna traslación en los ejes x ni z,
        // por lo que se mueve verticalmente en línea recta.
        // Luego, se le aplica un escalamiento de 0.5 en todos los ejes,
        // lo que hace que sea la mitad de su tamaño original.

        // Restablecer la matriz de vista a la identidad y trasladar y escalar el tercer cubo
        gl.glLoadIdentity();
        gl.glTranslatef(-2.0f, 5.0f, 1.f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        cubo3.dibujar(gl);

        //cubo3: Este cubo se traslada -2 unidades en el eje x
        // (hacia la izquierda), 3 unidades en el eje y (hacia arriba)
        // y 1 unidad en el eje z (hacia dentro de la pantalla).
        // Luego, se le aplica un escalamiento de 0.5 en todos los ejes,
        // lo que hace que sea la mitad de su tamaño original.
        // Como resultado, este cubo se dibuja más pequeño
        // y se sitúa a la izquierda de cubo1 y cubo2, y al
        // mismo nivel que cubo2

        // Actualizar el valor de translación para el próximo fotograma
        translacion += translacionDelta;
        anguloGiro1 += 0.02f;
    }
}

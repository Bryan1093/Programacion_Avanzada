package com.example.myapplication.renderes.gl10;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.gl10.TrianguloIndice;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class RendererTriangulo implements GLSurfaceView.Renderer{
    private TrianguloIndice triangulo;
    private int tipoPrimitiva;
    private float translacion;
    private float translacionspeed=0.3f;

    public RendererTriangulo(int valor) {
        triangulo = new TrianguloIndice();
        setTipoPrimitiva(valor);
    }
    private void setTipoPrimitiva(int valor) {
        switch (valor) {
            case 1:
                tipoPrimitiva = GL10.GL_TRIANGLES;
                break;
            case 2:
                tipoPrimitiva = GL10.GL_TRIANGLE_STRIP;
                break;
            case 3:
                tipoPrimitiva = GL10.GL_TRIANGLE_FAN;
                break;
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glClearColor(0.3f,0.5f,0.2f,1.0f);
        triangulo = new TrianguloIndice();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        gl10.glViewport(0,0,ancho,alto);
        gl10.glFrustumf(-8,8,-8,8,1,30);
        gl10.glMatrixMode(gl10.GL_PROJECTION);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        translacion += translacionspeed;
        if (translacion > 10.0f || translacion < -10.0f) {
            translacionspeed *= -1.0f; // Cambia la dirección
        }

        //gl.glTranslatef(0.0f¡,0.0f,-3.0f); //aqui es donde no se mueve
        gl.glTranslatef(translacion,0.0f,-3.0f); // donde se modifica para que se mueva
        triangulo.dibujar(gl, tipoPrimitiva);
    }
}

package com.example.myapplication.renderes.gl20;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.gl20.Cubo20;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CuboPushPopV20 implements GLSurfaceView.Renderer {
    private Cubo20 cubo;

    private Context contexto;

    public CuboPushPopV20(Context contexto) {
        this.contexto = contexto;
    }
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Configuración inicial cuando se crea la superficie de renderizado
        GLES20.glClearColor(0f, 1f, 0f, 1.0f); // Establecer el color de fondo
        GLES20.glEnable(GLES20.GL_DEPTH_TEST); // Habilitar el búfer de profundidad
        cubo = new Cubo20(contexto); // Crear instancias de cubos

    }
    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        float relacionAspecto = (float) ancho / (float) alto;
        GLES20.glViewport(0,0,ancho,alto);
    }
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        cubo.dibujar(gl);
    }
}

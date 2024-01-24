package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;

public class RenderCombinado implements GLSurfaceView.Renderer {

    private RenderEsferaVerde renderEsferaVerde;
    private RenderEsferaAmarilla renderEsferaAmarilla;
    private RenderEsferaRoja renderEsferaRoja;

    public RenderCombinado() {
        renderEsferaVerde = new RenderEsferaVerde(); // Configura el color verde
        renderEsferaAmarilla = new RenderEsferaAmarilla(); // Configura el color amarillo
        renderEsferaRoja = new RenderEsferaRoja(); // Configura el color rojo
    }

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig eglConfig) {
        renderEsferaVerde.onSurfaceCreated(gl, eglConfig);
        renderEsferaAmarilla.onSurfaceCreated(gl, eglConfig);
        renderEsferaRoja.onSurfaceCreated(gl, eglConfig);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        renderEsferaVerde.onSurfaceChanged(gl10, ancho, alto);
        renderEsferaAmarilla.onSurfaceChanged(gl10, ancho, alto);
        renderEsferaRoja.onSurfaceChanged(gl10, ancho, alto);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

        // Dibuja las esferas individuales
        renderEsferaVerde.onDrawFrame(gl);
        renderEsferaAmarilla.onDrawFrame(gl);
        renderEsferaRoja.onDrawFrame(gl);
    }
}

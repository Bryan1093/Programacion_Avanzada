package com.example.myapplication.renderes.gl10;

import android.opengl.GLES10;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RendererCirculo implements GLSurfaceView.Renderer {

    private class Figura {

        private float x, y;
        private float r, g, b;

        Figura(float x, float y, float r, float g, float b) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        void dibujarCirculo(float radioX, float radioY, int numTriangulos) {
            GLES10.glColor4f(r, g, b, 1.0f);

            float[] vertices = new float[3 * (numTriangulos + 2)];
            int vertexIndex = 0;

            vertices[vertexIndex++] = x;
            vertices[vertexIndex++] = y;

            for (int i = 0; i <= numTriangulos; i++) {
                float angulo = (float) (2.0 * Math.PI * i / numTriangulos);
                float xTriangulo = x + radioX * (float) Math.cos(angulo);
                float yTriangulo = y + radioY * (float) Math.sin(angulo);

                vertices[vertexIndex++] = xTriangulo;
                vertices[vertexIndex++] = yTriangulo;
            }

            FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
            GLES10.glVertexPointer(2, GLES10.GL_FLOAT, 0, vertexBuffer);

            GLES10.glDrawArrays(GLES10.GL_TRIANGLE_FAN, 0, numTriangulos + 2);

            GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        }

        void dibujarCuadrado(float lado) {
            GLES10.glColor4f(r, g, b, 1.0f);

            float[] vertices = {
                    x, y,
                    x + lado, y,
                    x, y + lado,
                    x + lado, y + lado
            };

            FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
            GLES10.glVertexPointer(2, GLES10.GL_FLOAT, 0, vertexBuffer);

            GLES10.glDrawArrays(GLES10.GL_TRIANGLE_STRIP, 0, 4);

            GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        }
    }

    private Figura circulo1, circulo2, circulo3, circulo4, circulo5, elipse;
    private Figura cuadrado1, cuadrado2, cuadrado3, cuadrado4, cuadrado5;

    public RendererCirculo() {
        circulo1 = new Figura(250, 150, 1.0f, 0.0f, 0.0f);
        circulo2 = new Figura(400, 100, 0.0f, 1.0f, 0.0f);
        circulo3 = new Figura(550, 150, 0.0f, 0.0f, 0.0f);
        circulo4 = new Figura(400, 180, 1.0f, 0.0f, 0.0f);
        circulo5 = new Figura(400, 300, 0.0f, 0.0f, 1.0f);

        elipse = new Figura(400, 450, 1.0f, 1.0f, 0.0f); // Elipse amarilla

        cuadrado1 = new Figura(550, 250, 1.0f, 0.0f, 0.0f);
        cuadrado2 = new Figura(600, 250, 0.0f, 1.0f, 0.0f);
        cuadrado3 = new Figura(550, 300, 0.0f, 0.0f, 1.0f);
        cuadrado4 = new Figura(600, 300, 1.0f, 1.0f, 0.0f); // Amarillo
        cuadrado5 = new Figura(420, 228, 1.0f, 1.0f, 1.0f); // Amarillo
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES10.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Fondo blanco
        GLES10.glMatrixMode(GLES10.GL_PROJECTION);
        GLES10.glLoadIdentity();

        int width = 800;
        int height = 600;

        // Ajusta la matriz de proyección para mapear (0, 0) a (width, height)
        GLES10.glOrthof(0, width, height, 0, -1, 1);

        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT);

        // Dibujar círculos
        circulo1.dibujarCirculo(30, 20, 30);
        circulo2.dibujarCirculo(30, 20, 30);
        circulo3.dibujarCirculo(30, 20, 30);

        circulo4.dibujarCirculo(90, 50, 30);

        circulo5.dibujarCirculo(90, 50, 30);
        elipse.dibujarCirculo(200.0f, 50, 30);

        // Deshabilitar la iluminación
        GLES10.glDisable(GLES10.GL_LIGHTING);
        GLES10.glDisable(GLES10.GL_LIGHT0);

        // Dibujar cuadrados
        cuadrado1.dibujarCuadrado(35);
        cuadrado2.dibujarCuadrado(35);
        cuadrado3.dibujarCuadrado(35);
        cuadrado4.dibujarCuadrado(35);
        cuadrado5.dibujarCuadrado(80);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES10.glViewport(0, 0, width, height);
    }
}

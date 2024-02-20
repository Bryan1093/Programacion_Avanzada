package com.example.myapplication.modelos.gl10;

import android.opengl.GLES10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Circulo {

    private float x, y, radioX, radioY;
    private float r, g, b; // Componentes de color
    private int numTriangulos = 30; // Ajusta según la calidad del círculo

    public Circulo(float x, float y, float radio, float r, float g, float b) {
        this.x = x;
        this.y = y;
        this.radioX = radio;
        this.radioY = radio;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Circulo(float x, float y, float radioX, float radioY, float r, float g, float b) {
        this.x = x;
        this.y = y;
        this.radioX = radioX;
        this.radioY = radioY;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void dibujarCirculo() {
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
}


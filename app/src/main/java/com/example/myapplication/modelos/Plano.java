package com.example.myapplication.modelos;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Plano {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE = (COMPVERT + COMPCOLO) * BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;

    public Plano(float xLength, float yLength) {
        // Define los puntos independientes
        float[] vertices = {
                -xLength / 2, yLength / 2, 0.0f, 0.0f, 1.0f, 1.0f,
                xLength / 2, yLength / 2, 0.0f, 0.0f, 1.0f, 1.0f,
                -xLength / 2, -yLength / 2, 0.0f, 1.0f, 0.0f, 1.0f,
                xLength / 2, -yLength / 2, 0.0f, 1.0f, 0.0f, 1.0f,
        };

        // Asigna los índices para formar dos triángulos
        byte[] indices = {
                0, 1, 2,
                1, 3, 2
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);

        // Buffer de índices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);
    }

    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CCW);

        // Define los vértices de posición
        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, STRIDE, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Dibuja los triángulos
        bufferIndices.position(0);
        gl.glColor4f(0.5f, 0.5f, 0.5f, 1.0f); // Puedes ajustar el color según tu preferencia
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}


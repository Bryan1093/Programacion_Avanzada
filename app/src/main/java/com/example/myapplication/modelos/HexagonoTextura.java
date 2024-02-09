package com.example.myapplication.modelos;

import com.example.myapplication.utilidades.Funciones;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class HexagonoTextura {
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferTexturas;
    private ByteBuffer bufferIndice;
    private final static int byteFlotante = 4;
    private final static int comPorVertices = 2;

    public HexagonoTextura() {
        float[] vertices = {
                0.0f, 0.0f, // Centro
                0.5f, 0.0f, // Punto 1
                0.3f, 0.4f, // Punto 2
                -0.3f, 0.4f, // Punto 3
                -0.5f, 0.0f, // Punto 4
                -0.3f, -0.4f, // Punto 5
                0.3f, -0.4f // Punto 6
        };

        float[] textura = {
                0.5f, 0.5f, // Centro
                1.0f, 0.5f, // Punto 1
                0.8f, 1.0f, // Punto 2
                0.2f, 1.0f, // Punto 3
                0.0f, 0.5f, // Punto 4
                0.2f, 0.0f, // Punto 5
                0.8f, 0.0f // Punto 6
        };

        byte[] indices = {
                0, 1, 2,
                0, 2, 3,
                0, 3, 4,
                0, 4, 5,
                0, 5, 6,
                0, 6, 1
        };

        bufferVertices = Funciones.generarBuffer(vertices);
        bufferTexturas = Funciones.generarBuffer(textura);

        bufferIndice = ByteBuffer.allocateDirect(indices.length);
        bufferIndice.order(ByteOrder.nativeOrder());
        bufferIndice.put(indices);
        bufferIndice.position(0);
    }

    public void dibujar(GL10 gl) {
        gl.glVertexPointer(comPorVertices, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

        gl.glTexCoordPointer(2, gl.GL_FLOAT, 0, bufferTexturas);
        gl.glEnableClientState(gl.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawElements(gl.GL_TRIANGLES, 18, gl.GL_UNSIGNED_BYTE, bufferIndice);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
    }
}
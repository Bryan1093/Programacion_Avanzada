package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class PuntoMov {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferColores;


    public PuntoMov(int numPuntos, boolean hayMovimiento, int direccionMovimiento) {

        float[] vertices = new float[numPuntos * COMPVERT];
        float[] colores = new float[numPuntos * COMPCOLO];

        // Llena los arrays de vértices y colores con valores por defecto
        for (int i = 0; i < numPuntos; i++) {
            if (hayMovimiento) {
                // Si hay movimiento, utiliza coordenadas en el rango [-10, 10]
                vertices[i * COMPVERT] = (float) (Math.random() * 20 - 10); // Coordenada X
                vertices[i * COMPVERT + 1] = (float) (Math.random() * 20 - 10); // Coordenada Y
            } else {
                // Si no hay movimiento, utiliza coordenadas fijas en el rango [-5, 5]
                vertices[i * COMPVERT] = (float) (Math.random() * 10 - 5); // Coordenada X
                vertices[i * COMPVERT + 1] = (float) (Math.random() * 10 - 5); // Coordenada Y
            }

            // Colores aleatorios
            colores[i * COMPCOLO] = (float) Math.random(); // Componente Rojo
            colores[i * COMPCOLO + 1] = (float) Math.random(); // Componente Verde
            colores[i * COMPCOLO + 2] = (float) Math.random(); // Componente Azul
            colores[i * COMPCOLO + 3] = 1.0f; // Componente Alfa
        }

        // Para manejar la cantidad de vértices que estamos usando
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);

        buffer = ByteBuffer.allocateDirect(colores.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferColores = buffer.asFloatBuffer();
        bufferColores.put(colores);
    }

    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CCW);
        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

        bufferColores.position(0);
        gl.glColorPointer(COMPCOLO, gl.GL_FLOAT, 0, bufferColores);
        gl.glEnableClientState(gl.GL_COLOR_ARRAY);

        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS, 0, bufferVertices.capacity() / COMPVERT);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
        bufferColores.position(0);
    }
}


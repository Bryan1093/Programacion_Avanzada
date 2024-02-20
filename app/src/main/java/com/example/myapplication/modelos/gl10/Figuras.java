package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Figuras {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE = (COMPVERT + COMPCOLO) * BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;

    public Figuras() {
        inicializarBuffers();
    }

    private void inicializarBuffers() {
        float[] figuras = {
                // X     Y        R      G     B    A
                // Puntos para el romboide
                -2.0f, 4.0f,     1.0f, 127/255f, 39/255f, 1.0f, //0
                -4.0f, 2.0f,    1.0f, 127/255f, 39/255f, 1.0f, //1
                -8.0f, 2.0f,   1.0f, 127/255f, 39/255f, 1.0f, //2
                -6.0f, 4.0f,    1.0f, 127/255f, 39/255f, 1.0f, //3

                // Puntos para dibujar la L azul
                3.0f, 4.0f,    0.0f, 0.f, 1.0f, 1.0f, //4
                3.0f, 3.0f,     0.0f, 0.f, 1.0f, 1.0f, //5
                2.0f, 3.0f,     0.0f, 0.f, 1.0f, 1.0f, //6
                2.0f, 4.0f,     0.0f, 0.f, 1.0f, 1.0f, //7
                5.0f, 3.0f,     0.0f, 0.f, 1.0f, 1.0f, //8
                5.0f, 2.0f,      0.0f, 0.f, 1.0f, 1.0f, //9
                2.0f, 2.0f,    0.0f, 0.f, 1.0f, 1.0f,  //10

                // Puntos para el rectángulo verde y blanco
                -3.0f, -1.0f,   0.0f, 1.0f,0.0f, 1.0f, //11
                -3.0f, -4.0f,   0.0f, 1.0f,0.0f, 1.0f, //12
                -8.0f , -4.0f,  0.0f, 1.0f,0.0f, 1.0f, //13
                -8.0f, -1.0f,   0.0f, 1.0f,0.0f, 1.0f,  //14
                -3.5f, -1.5f,   1.0f, 1.0f,1.0f, 1.0f, //15
                -3.5f, -3.5f,   1.0f, 1.0f,1.0f, 1.0f, //16
                -7.5f, -3.5f,   1.0f, 1.0f,1.0f, 1.0f, // 17
                -7.5f, -1.5f,   1.0f, 1.0f,1.0f, 1.0f, //18

                // Puntos para el pentágono
                4.0f, -1.0f,   1.0f, 1.0f,0.0f, 1.0f, //19
                6.0f, -2.0f,  1.0f, 1.0f,0.0f, 1.0f, //20
                5.0f, -4.0f,  1.0f, 1.0f,0.0f, 1.0f,// 21
                3.0f, -4.0f, 1.0f, 1.0f,0.0f, 1.0f,//22
                2.0f, -2.0f, 1.0f, 1.0f,0.0f, 1.0f,// 23
                4.0f, -2.5f, 1.0f, 1.0f,0.0f, 1.0f,// 24
        };

        byte[] indices = {
                // Indices para el romboide
                0,1,2,
                0,2,3,
                // Indices para la L
                4,5,6,
                4,7,6,
                8,9,10,
                8,10,6,
                // Indices para el rectángulo de dos colores
                11,12,13,
                11,13,14,
                15,16,17,
                15,17,18,
                // Indices para el pentágono
                19,20,24,
                20,21,24,
                24,21,22,
                24,22,23,
                19,24,23
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(figuras.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(figuras);
        bufferVertices.position(0);

        // Buffer de índices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);
    }

    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CCW);

        // Definir vertices de posición
        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, STRIDE, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Definir vertices de color
        bufferVertices.position(COMPVERT);
        gl.glColorPointer(COMPCOLO, gl.GL_FLOAT, STRIDE, bufferVertices);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //Defeinir vertices de romboide
        bufferIndices.position(0);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);
        //Defeinir vertices de la L
        bufferIndices.position(6);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);
        bufferIndices.position(12);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);
        //Defeinir vertices de rectangulo externo
        bufferIndices.position(18);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);
        //Defeinir vertices de rectangulo interno
        bufferIndices.position(24);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6, gl.GL_UNSIGNED_BYTE, bufferIndices);
        //Defeinir vertices del poligono de 5 lados
        bufferIndices.position(30);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(33);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(36);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(39);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(42);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}

package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Rectangulo_vertices {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE =(COMPVERT + COMPCOLO)*BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;


    public Rectangulo_vertices() {
        //para definir los puntos independientes
        float[] vertices = {
                //x      y      R      S      V      A
                5.0f,   3.0f,  1.0f,  0.0f,  0.0f,  1.0f,
                5.0f,  -3.0f,  0.0f,  1.0f,  0.0f,  1.0f,
                -5.0f, -3.0f,  0.0f,  0.0f,  1.0f,  1.0f,
                -5.0f,  3.0f,  1.0f,  1.0f,  0.0f,  1.0f,
        };
        //aqui es para unir o asignar los indices
        byte[] indices = {
                //aqui esta en sentido antihorario
                0,3,2,
                0,2,1
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length*BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);

        // bufferIndices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);
    }
    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CCW);

        //Definir vertices de posicion
        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, STRIDE, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);


        bufferIndices.position(0);
        gl.glColor4f(0.0f,0.0f,0.0f,1.0f);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(3);
        gl.glColor4f(0.0f,0.0f,0.0f,1.0f);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3, gl.GL_UNSIGNED_BYTE, bufferIndices);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}

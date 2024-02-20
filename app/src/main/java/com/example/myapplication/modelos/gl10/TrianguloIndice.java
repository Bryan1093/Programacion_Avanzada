package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TrianguloIndice {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferColores;
    private ByteBuffer bufferIndices;


    public TrianguloIndice() {
        float[] verticesHexagono = {
                3.0f , 3.0f , //0
                5.0f , 0.0f,  //1
                3.0f, -3.0f, //2
                -3.0f , -3.0f, //3
                -5.0f , 0.0f , //4
                -3.0f , 3.0f, //5
                0.0f, 0.0f //6
        };

        byte[] indices = {
                0,1,6 , // primer triangulo
                1,2,6 , // segundo triangulo
                0,6,5 , // tercer triangulo
                6,2,3 ,// cuarto triangulo
                5,6,4, // quinto triangulo
                6,3,4 // sexto triangulo
        };

        float[] colores = {
                0.0f,0.8f,0.8f,1.0f,
                0.7f,0.8f,0.1f,1.0f,
                0.0f,0.8f,0.8f,1.0f,
                0.9f,0.1f,0.3f,1.0f,
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(verticesHexagono.length*BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(verticesHexagono);
        bufferVertices.position(0);

        buffer = ByteBuffer.allocateDirect(colores.length*BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferColores = buffer.asFloatBuffer();
        bufferColores.put(colores);
        bufferColores.position(0);

        // bufferIndices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);
    }
    public void dibujar(GL10 gl, int valor ){
        gl.glFrontFace(gl.GL_CW);

        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT,gl.GL_FLOAT,0,bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

         /* bufferColores.position(0);
        gl.glColorPointer(COMPCOLO, gl.GL_FLOAT,0,bufferColores);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY); */

        bufferIndices.position(0);

        gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
        gl.glDrawElements(valor, 3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        bufferIndices.position(3);
        gl.glColor4f(0.0f,1.0f,0.0f,1.0f);
        gl.glDrawElements(valor, 3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        bufferIndices.position(6);
        gl.glColor4f(0.0f,0.0f,1.0f,1.0f);
        gl.glDrawElements(valor, 3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        bufferIndices.position(9);
        gl.glColor4f(0.5f,0.7f,0.4f,1.0f);
        gl.glDrawElements(valor, 3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        bufferIndices.position(12);
        gl.glColor4f(0.7f,1.0f,0.40f,1.0f);
        gl.glDrawElements(valor ,3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        bufferIndices.position(15);
        gl.glColor4f(1.0f,0.30f,0.4f,1.0f);
        gl.glDrawElements(valor, 3, gl.GL_UNSIGNED_BYTE,bufferIndices);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}

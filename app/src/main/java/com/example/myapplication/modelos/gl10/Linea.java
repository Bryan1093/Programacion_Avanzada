package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Linea {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferColores;

    public Linea(){
        float[] vertices = {
                3.0f, 5.0f,
                3.0f, 3.0f,
                3.0f, 2.0f,
                2.0f, 3.0f,
                1.0f, 5.0f,
                1.0f, 6.0f,
                2.0f, 5.0f,
                3.0f, 3.0f

        };

        float[] colores = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                0.6f, 0.8f, 0.6f, 1.0f,
                0.3f, 0.4f, 0.3f, 1.0f,
                0.2f, 0.7f, 0.8f, 1.0f
        };
        //Para manejar la cantidad de vertices que estamos usando
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length*BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder()    );
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);
        //bufferVertices.position(0);

        buffer = ByteBuffer.allocateDirect(colores.length*BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder()    );
        bufferColores = buffer.asFloatBuffer();
        bufferColores.put(colores);
        //bufferColores.position(0);
    }

    public void dibujar(GL10 gl){
        gl.glFrontFace(gl.GL_CCW);
        bufferVertices.position(1);
        gl.glVertexPointer(COMPVERT,gl.GL_FLOAT,0,bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

        bufferColores.position(0);
        gl.glColorPointer(COMPCOLO,gl.GL_FLOAT,0,bufferColores);
        gl.glEnableClientState(gl.GL_COLOR_ARRAY);

        gl.glPointSize(50);
        gl.glLineWidth(18.0f);
        bufferVertices.position(1);
        gl.glDrawArrays(gl.GL_LINE_LOOP,0,6);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}

package com.example.myapplication.modelos;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Punto {
    private static final int COMPVERT = 2;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferColores;

    public Punto(){
        float[] vertices = {
                3.0f, 3.0f,
                3.0f, -3.0f,
                -3.0f, -3.0f,
                -3.0f, 3.0f
        };

        float[] colores = {
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,

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
        bufferVertices.position(0);
        //mandamos a usar los vertices que estan dentro del buffer de vertices
        gl.glVertexPointer(COMPVERT,gl.GL_FLOAT,0,bufferVertices);
        //Para habilitar el uso de los vertices
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);


        gl.glColorPointer(COMPCOLO,gl.GL_FLOAT,0,bufferColores);
        gl.glEnableClientState(gl.GL_COLOR_ARRAY);

        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS,0,4);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
        bufferColores.position(0);

        /*gl.glColor4f(0.8f,0.2f,0.7f,1.0f);
        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS,0,4);

        gl.glColor4f(0.7f,0.9f,0.1f,1.0f);*/
        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS,0,3);

        gl.glColor4f(1.0f,0.8f,0.5f,1.0f);
        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS,1,2);

        gl.glColor4f(0.5f,1.0f,0.4f,1.0f);
        gl.glPointSize(50);
        gl.glDrawArrays(gl.GL_POINTS,1,1);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }
}

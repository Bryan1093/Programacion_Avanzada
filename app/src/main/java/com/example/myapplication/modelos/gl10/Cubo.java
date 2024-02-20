package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cubo {
    private static final int COMPVERT = 3;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE = (COMPVERT + COMPCOLO) * BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;
    private FloatBuffer bufferColores;


    public Cubo() {


        float[] vertices = {
                // X     Y     Z     P    R      G     B    A
                   1,    1,    1,  //0
                   1,   -1,    1,  //1
                  -1,   -1,    1,  //2
                  -1,    1,    1,  //3
                   1,    1,   -1,  //4
                   1,   -1,   -1,  //5
                  -1,   -1,   -1,  //6
                  -1,    1,   -1   //7


        };

        float[] colores = {
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,

        };

        byte[] indices = {
                //caras frontales sentido horario
                0,1,2,
                0,2,3,
                0,3,7,
                0,7,4,
                0,4,5,
                0,5,1,
                //caras posteriores
                6,5,1,
                6,1,2,
                6,2,3,
                6,3,7,
                6,7,4,
                6,4,5
        };



        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);


        // bufferIndices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);

        //bufferColores

        buffer = ByteBuffer.allocateDirect(colores.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferColores = buffer.asFloatBuffer();
        bufferColores.put(colores);
        bufferColores.position(0);

    }

    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CCW);

        //Definir vertices de posicion
        bufferVertices.position(0);
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //Definir vertices de color
        bufferColores.position(COMPVERT);
        gl.glColorPointer(COMPCOLO,gl.GL_FLOAT,0,bufferColores);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        bufferIndices.position(0);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 18, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(18);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 18, gl.GL_UNSIGNED_BYTE, bufferIndices);


        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glFrontFace(gl.GL_CCW);
    }

}
package com.example.myapplication.modelos.gl10;

import com.example.myapplication.utilidades.Funciones;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CuboLuz {
    private static final int COMPVERT = 3;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE = (COMPVERT + COMPCOLO) * BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;
    private FloatBuffer bufferColores;
    private FloatBuffer bufferNormales;


    public CuboLuz() {


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

        float[] normales = {
                // X     Y     Z     P    R      G     B    A
                0,    0,    1,  //0
                0,    0,    1,  //1
                0,    0,    1,  //2
                0,    0,    1,  //3
                0,    0,    1,  //4
                0,    0,    1, //5
                0,    0,    1,  //6
                0,    0,    1,  //7


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


        bufferVertices = Funciones.generarBuffer(vertices);
        bufferColores = Funciones.generarBuffer(colores);
        bufferNormales = Funciones.generarBuffer(normales);

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
        gl.glVertexPointer(COMPVERT, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //Definir vertices de color
        bufferColores.position(COMPVERT);
        gl.glColorPointer(COMPCOLO,gl.GL_FLOAT,0,bufferColores);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);


        gl.glNormalPointer(gl.GL_FLOAT,0,bufferNormales);
        gl.glEnableClientState(gl.GL_NORMAL_ARRAY);

        bufferIndices.position(0);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3*6, gl.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(18);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 3*6, gl.GL_UNSIGNED_BYTE, bufferIndices);


        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glDisableClientState(gl.GL_COLOR_ARRAY);
        gl.glDisableClientState(gl.GL_NORMAL_ARRAY);

        gl.glFrontFace(gl.GL_CCW);
    }

}
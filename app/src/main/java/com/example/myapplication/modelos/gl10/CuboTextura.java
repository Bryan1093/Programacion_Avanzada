package com.example.myapplication.modelos.gl10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.example.myapplication.utilidades.Funciones;

public class CuboTextura {
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferColores;
    private FloatBuffer bufferNormales;
    private FloatBuffer bufferTexturas;
    private ByteBuffer bufferIndice;
    private final static int byteFlotante = 4;
    private final static int comPorVertices = 3;
    private final static int comPorColor = 4;
    private final static int comPorTextura = 2;
    private final static int comPorNormal = 3;

    public CuboTextura(){
        float[] vertices ={
                1.0f,-1.0f,-1.0f,
                1.0f,-1.0f,1.0f,
                -1.0f,-1.0f,1.0f,
                -1.0f,-1.0f,-1.0f,
                1.0f,1.0f,-1.0f,
                1.0f,1.0f,1.0f,
                -1.0f,1.0f,1.0f,
                -1.0f,1.0f,-1.0f
        };

        float[] colores ={
                1.0f,0.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,
                1.0f,0.0f,0.0f,1.0f,
                0.0f,1.0f,0.0f,1.0f,
                0.0f,0.0f,1.0f,1.0f,
                1.0f,1.0f,0.0f,1.0f,

        };
        byte[] indices = {
                0,1,2,
                0,2,3,
                4,5,6,
                4,6,7,
                0,4,7,
                0,7,3,
                1,5,6,
                1,6,2,
                0,1,5,
                0,5,4,
                3,2,6,
                3,6,7,
        };
        float[] normales ={
                0,1,0,
                0,1,0,
                0,1,0,
                0,1,0,
                0,-1,0,
                0,-1,0,
                0,-1,0,
                0,-1,0
        };

        float[] texturas ={
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };

        bufferVertices = Funciones.generarBuffer(vertices);
        bufferColores = Funciones.generarBuffer(colores);
        bufferNormales = Funciones.generarBuffer(normales);
        bufferTexturas = Funciones.generarBuffer(texturas);

        bufferIndice = ByteBuffer.allocateDirect(indices.length);
        bufferIndice.order(ByteOrder.nativeOrder());
        bufferIndice.put(indices);
        bufferIndice.position(0);
    }

    public void dibujar(GL10 gl){
        gl.glFrontFace(GL10.GL_CW);
        gl.glVertexPointer(comPorVertices, GL10.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        bufferColores.position(0);
        gl.glColorPointer(comPorColor, GL10.GL_FLOAT, 0, bufferColores);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        bufferTexturas.position(0);
        gl.glTexCoordPointer(comPorTextura, GL10.GL_FLOAT, 0, bufferTexturas);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, bufferIndice);

        bufferNormales.position(0);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, bufferNormales);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}

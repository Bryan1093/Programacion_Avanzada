package com.example.myapplication.modelos.gl10;

import com.example.myapplication.utilidades.Funciones;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
public class PlanoTexturizado {
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferTexturas;
    private ByteBuffer bufferIndice;
    private FloatBuffer bufferNormales;
    private final static int COMPVERT = 3;
    //private final static int COMPCOLO = 4;
    //private final static int COMPNORM = 4;
    private final static int COMPTEXT = 2;

    public PlanoTexturizado(){
        float[] vertices ={
                1.0f,-1.0f,1.0f,
                -1.0f,-1.0f,1.0f,
                -1.0f,-1.0f,-1.0f,
                1.0f,-1.0f,-1.0f
        };

        float[] texturas ={
                1.0f,0.0f,
                0.0f,0.0f,
                0.0f,1.0f,
                1.0f,1.0f
        };

        float[] normales ={
                6.0f ,1.0f ,1.7f,
                1.0f ,3.0f ,1.0f,
                1.0f, 1.8f, 1.0f,
                8.0f,3.2f,1.0f

        };

        byte[] indices = {
                0,1,2,
                0,2,3,
        };

        bufferVertices = Funciones.generarBuffer(vertices);
        bufferTexturas = Funciones.generarBuffer(texturas);
        bufferNormales = Funciones.generarBuffer(normales);

        bufferIndice = ByteBuffer.allocateDirect(indices.length);
        bufferIndice.order(ByteOrder.nativeOrder());
        bufferIndice.put(indices);
        bufferIndice.position(0);
    }

    public void dibujar(GL10 gl){
        gl.glFrontFace(gl.GL_CW);

        gl.glVertexPointer(COMPVERT,gl.GL_FLOAT,0,bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

        gl.glTexCoordPointer(COMPTEXT, gl.GL_FLOAT,0,bufferTexturas);
        gl.glEnableClientState(gl.GL_TEXTURE_COORD_ARRAY);

        gl.glNormalPointer(gl.GL_FLOAT,0,bufferNormales);
        gl.glEnableClientState(gl.GL_NORMAL_ARRAY);

        bufferIndice.position(0);
        gl.glDrawElements(gl.GL_TRIANGLE_FAN,3*2,gl.GL_UNSIGNED_BYTE, bufferIndice);

        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glDisableClientState(gl.GL_COLOR_ARRAY);
        gl.glDisableClientState(gl.GL_NORMAL_ARRAY);
        gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
    }
}

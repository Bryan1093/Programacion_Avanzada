package com.example.myapplication.modelos;

import com.example.myapplication.utilidades.Funciones;

import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class EsferaIlu {
    private FloatBuffer bufferVertices;
    private FloatBuffer bufferNormales;
    private final static int comPorVertices = 3;

    private int franjas, cortes;

    public EsferaIlu(int franjas, int cortes, float radio, float ejePolar) {
        this.franjas = franjas;
        this.cortes = cortes;

        float[] vertices = new float[3 * ((cortes * 2 + 2) * franjas)]; // *2 porque son dos triangulos para cada cuadrado y +2 de los vertices duplicados para los triangulos degenerados
        float[] normales = new float[3 * ((cortes * 2 + 2) * franjas)];

        int iVertice = 0;
        int iNormal = 0;

        // Bucle para construir las franjas de la esfera
        for (int i = 0; i < franjas; i++) {
            float phi0 = (float) Math.PI * ((i + 0) * (1.0f / (franjas)) - 0.5f);
            float cosPhi0 = (float) Math.cos(phi0);
            float sinPhi0 = (float) Math.sin(phi0);

            float phi1 = (float) Math.PI * ((i + 1) * (1.0f / (franjas)) - 0.5f);
            float cosPhi1 = (float) Math.cos(phi1);
            float sinPhi1 = (float) Math.sin(phi1);

            // Bucle para construir los cortes de la esfera
            for (int j = 0; j < cortes; j++) {
                float theta = (float) (-2.0f * Math.PI * j * (1.0 / (cortes - 1)));
                float cosTheta = (float) Math.cos(theta);
                float sinTheta = (float) Math.sin(theta);

                // Dibujar la esfera en duplas, pares de puntos
                vertices[iVertice + 0] = radio * cosPhi0 * cosTheta;          // x
                vertices[iVertice + 1] = radio * (sinPhi0 * ejePolar);    // y
                vertices[iVertice + 2] = radio * (cosPhi0 * sinTheta);        // z

                vertices[iVertice + 3] = radio * cosPhi1 * cosTheta;          // x'
                vertices[iVertice + 4] = radio * (sinPhi1 * ejePolar);    // y'
                vertices[iVertice + 5] = radio * (cosPhi1 * sinTheta);        // z'

                // Calcular las normales
                float nx0 = cosPhi0 * cosTheta;
                float ny0 = sinPhi0;
                float nz0 = cosPhi0 * sinTheta;

                float nx1 = cosPhi1 * cosTheta;
                float ny1 = sinPhi1;
                float nz1 = cosPhi1 * sinTheta;

                normales[iNormal + 0] = (nx0 + nx1) * 0.5f;
                normales[iNormal + 1] = (ny0 + ny1) * 0.5f;
                normales[iNormal + 2] = (nz0 + nz1) * 0.5f;

                iNormal += 3;
                iVertice += 6;
            }
        }

        bufferVertices = Funciones.generarBuffer(vertices);
        bufferNormales = Funciones.generarBuffer(normales);
    }

    public void dibujar(GL10 gl) {
        gl.glFrontFace(gl.GL_CW);

        bufferVertices.position(0);
        gl.glVertexPointer(comPorVertices, gl.GL_FLOAT, 0, bufferVertices);
        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);

        bufferNormales.position(0);
        gl.glNormalPointer(gl.GL_FLOAT, 0, bufferNormales);
        gl.glEnableClientState(gl.GL_NORMAL_ARRAY);

        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 0, franjas * cortes * 2);

        gl.glFrontFace(gl.GL_CCW);
        gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
        gl.glDisableClientState(gl.GL_NORMAL_ARRAY);
    }
}
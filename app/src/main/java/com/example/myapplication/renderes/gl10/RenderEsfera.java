package com.example.myapplication.renderes.gl10;

import android.opengl.GLSurfaceView;

import com.example.myapplication.modelos.gl10.Esfera;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




public class RenderEsfera implements GLSurfaceView.Renderer {
    private float vIncremento = 0f;
    private Esfera esfera, esferaTierra,esferaLuna,esferaCometa;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.524f, 0.7059f, 0.79f, 1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        esfera = new Esfera(30, 30, 1.5f, 1.0f, new float[]{1, 0, 0, 1, 1, 1, 0, 1});
        esferaTierra = new Esfera(10, 30, 1.5f, 1.0f, new float[]{0, 0, 1, 1, 0, 1, 0, 1});
        esferaLuna = new Esfera(30, 30, 1.5f, 1.0f, new float[]{1f, 1f, 1f, 1, 1, 1, 1, 1});
        esferaCometa = new Esfera(30, 30, 1.5f, 1.0f, new float[]{0f, 0f, 0f, 1, 0, 0, 0, 1});
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glFrustumf(-1.0f, 1.0f, bottom, top, 1.5f, 30f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(0, 0, -4f);
        //PRIMERA ESFERA
        gl.glPushMatrix();
        {
            gl.glPushMatrix();
            {
                gl.glRotatef(vIncremento / 4, 0.5f, 0.5f, 1f);
                esfera.dibujar(gl);
            }
            gl.glPopMatrix();

            //SEGUNDA ESFERA
            gl.glPushMatrix();
            {
                gl.glRotatef(vIncremento / 2, 0.5f, 0.5f, 1f);
                gl.glTranslatef(0, 2.2f, 0);
                gl.glScalef(0.2f, 0.2f, 0.2f);
                gl.glRotatef(vIncremento * 2, 0, 1, 1);
                esferaTierra.dibujar(gl);

                //TERCERA ESFERA
                gl.glPushMatrix();
                {
                    gl.glRotatef(vIncremento * 6, 0.5f, 0.5f, 1f);
                    gl.glTranslatef(0, 2.2f, 0);
                    gl.glScalef(0.2f, 0.2f, 0.2f);
                    esferaLuna.dibujar(gl);

                    //CUARTA ESFERA-------------
                    gl.glPushMatrix();
                    {
                        gl.glRotatef(vIncremento * 6, 0.2f, 0.9f, 0.2f);
                        gl.glTranslatef(2f, -2f, 0);
                        gl.glScalef(0.5f, 0.5f, 0.5f);
                        esferaCometa.dibujar(gl);
                    }
                    gl.glPopMatrix();

                }
                gl.glPopMatrix();

            }
            gl.glPopMatrix();


        }
        gl.glPopMatrix();

        vIncremento += 0.2f;
    }
}

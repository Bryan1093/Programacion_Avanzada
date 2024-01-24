package com.example.myapplication.renderes;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.example.myapplication.modelos.Cilindro;
import com.example.myapplication.modelos.Cono;
import com.example.myapplication.modelos.CuboPushPop;
import com.example.myapplication.modelos.Esfera;
import com.example.myapplication.modelos.Rectangulo;
import com.example.myapplication.modelos.Rio;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderEscena implements GLSurfaceView.Renderer {
    private Esfera elipsoide;
    private Esfera sol,copaArb1,copaArb2,copaArb3,copaArb4,copaArb5,copaArb6,copaArb7,
    nube1,nube2,nube3,llanta1, llanta2,llanta3,llanta4;
    private CuboPushPop cubo1,rectangulo;
    private Rectangulo piso,paredAtras,paredFren,paredDer,techo;
    private Cilindro tronco,tronco2,tronco3,tronco4,tronco5,tronco6,tronco7;
    private Cono montana,montana2,picoPeq,picoGr;
    private float rotacion = 0;
    private float anguloRotacion1 = 0;
    private float translacion = 0;
    private float translacionDelta = 0.7f;
    private Rio rio;

    //------------------------------------------------------------------------------------
    double[][] coloresCilindros = {
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco2
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco3
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco4
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco5
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco6
            {0.4863, 0.3216, 0.3216, 1.0}, //Tronco7
    };
    double[][] coloresConos = {
            {0.4078, 0.3961, 0.3961, 1.0}, // montana
            {0.4078, 0.3961, 0.3961, 1.0}, // montana2
            {1, 1, 1, 1.0}, // picoPeq
            {1, 1, 1, 1.0}, // picoGr

    };

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        gl10.glClearColor(0.251f, 0.251f, 0.251f, 1.0f);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glEnable(GL10.GL_BLEND);
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        rio = new Rio();
        piso = new Rectangulo();
        paredAtras = new Rectangulo();
        paredFren = new Rectangulo();
        paredDer = new Rectangulo();
        techo = new Rectangulo();
        sol = new Esfera(30, 30, 0.5f, 1.0f, new float[]{1, 0, 0, 1, 1, 1, 0, 1});
        copaArb1 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb2 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb3 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb4 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb5 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb6 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        copaArb7 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{0.2235f, 0.6039f, 0.0863f, 1, 0, 1, 0, 1});
        nube1 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{1, 1, 1, 1, 1, 1, 1, 1});
        nube2 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{1, 1, 1, 1, 1, 1, 1, 1});
        nube3 = new Esfera(30, 30, 0.5f, 1.0f, new float[]{1, 1, 1, 1, 1, 1, 1, 1});
        cubo1 = new CuboPushPop();
        rectangulo = new CuboPushPop();
        llanta1 = new Esfera(10, 10, 0.5f, 1.0f, new float[]{0.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0, 1});
        llanta2 = new Esfera(10, 10, 0.5f, 1.0f, new float[]{0.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0, 1});
        llanta3 = new Esfera(10, 10, 0.5f, 1.0f, new float[]{0.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0, 1});
        llanta4 = new Esfera(10, 10, 0.5f, 1.0f, new float[]{0.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0, 1});

        // Inicializar objeto Cilindro
        float cilindroRadius = 1.0f;
        float cilindroHeight = 2.0f;
        int cilindroNumSlices = 30;

        tronco = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);
        tronco2 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[1]);
        tronco3 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[2]);
        tronco4 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[3]);
        tronco5 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[4]);
        tronco6 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[5]);
        tronco7 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[6]);


        // Inicializar objetos Cono
        float conoRadius = 1.0f;
        float conoHeight = 2.0f;
        int conoSegmentos = 30;

        montana = new Cono(conoRadius, conoHeight, conoSegmentos, coloresConos[0]);
        montana2 = new Cono(conoRadius, conoHeight, conoSegmentos, coloresConos[1]);
        picoPeq = new Cono(conoRadius, conoHeight, conoSegmentos, coloresConos[2]);
        picoGr = new Cono(conoRadius, conoHeight, conoSegmentos, coloresConos[3]);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int ancho, int alto) {
        // Configuración cuando cambia el tamaño de la superficie de renderizado
        float relacionAspecto = (float) ancho / (float) alto;

        gl10.glViewport(0, 0, ancho, alto);

        gl10.glMatrixMode(GL10.GL_PROJECTION);
        gl10.glLoadIdentity();
        GLU.gluPerspective(gl10, 80.0f, relacionAspecto, 1, 150);

        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Configuración de la cámara utilizando gluLookAt
        GLU.gluLookAt(gl,
                0.0f, 15.0f, -60.0f,   // Posición de la cámara
                -1.0f, 0.0f, 0.0f,     // Punto de mira
                0 , 15, 0);              // Orientación de la cámara (eje Y arriba)

        // Aplicar rotación a todas las figuras alrededor del eje Y
        gl.glRotatef(rotacion, 0.0f, 1.0f, 0.0f);

        //PAREDES

        gl.glPushMatrix();{
            gl.glTranslatef(-1, 0, -2);
            gl.glScalef(8.0f, 0.04f, 10.0f);
            piso.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(-1, 10, 8);
            gl.glScalef(8.0f, 10.0f, 0.04f);
            paredAtras.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(-9, 10, -2);
            gl.glScalef(0.04f, 10.0f, 10.0f);
            paredDer.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(23, 10, -2);
            gl.glScalef(0.04f, 10.0f, 10.0f);
            paredFren.dibujar(gl);
        }gl.glPopMatrix();

        // y---altura
        // z--adelnte o atras
        // x--laterales

        // FIGURAS ------------------------------------------------------------------------------------

         gl.glPushMatrix();
        {
            gl.glTranslatef(19, 0, 3.0f);
            gl.glScalef(3.0f, 5.0f, 3.0f);
            montana.dibujar(gl);
        }
        gl.glPopMatrix();

       gl.glPushMatrix();
        {
            gl.glTranslatef(15, 0, 3.0f);
            gl.glScalef(2.5f, 3.5f, 2.5f);
            montana2.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(15, 5, 3.0f);
            gl.glScalef(0.7f, 1.2f, 0.7f);
            picoPeq.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(19, 6.6f, 3.0f);
            gl.glScalef(1.1f, 1.7f, 1.1f);
            picoGr.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(17, 12, 4.0f);
            gl.glScalef(2.5f, 2.5f, 2.5f);
            sol.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-2, 12, 3.0f);
            gl.glScalef(4f, 2.5f, 2.5f);
            nube1.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(4, 12, -6.0f);
            gl.glScalef(4, 2.5f, 2.5f);
            nube2.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(15, 12, -3.0f);
            gl.glScalef(4, 2.5f, 2.5f);
            nube3.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(-1, 0.1f, -3);
            gl.glScalef(8.0f, 0.04f, 2.0f);
            rio.dibujar(gl);
        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-4, 2, -8);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-4, 5, -8);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb1.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 2, -8);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco2.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 5, -8);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb2.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(21, 2, -8);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco3.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(21, 5, -8);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb3.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(17, 2, -8);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco4.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(17, 5, -8);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb4.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 2, 6);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco5.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 5, 6);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb5.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-6, 2, 6);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco6.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(-6, 5, 6);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb6.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(6, 2, 6);
            gl.glScalef(0.5f, 1.8f, 0.5f);
            tronco7.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(6, 5, 6);
            gl.glScalef(2.5f, 4f, 2.5f);
            copaArb7.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(2.5f, 2.4f, -10.0f);
            gl.glScalef(1f, 0.8f, 0.8f);
            cubo1.dibujar(gl);
        }
        gl.glPopMatrix();

        // Figura en el cuadrante inferior derecho
        gl.glPushMatrix();
        {
            gl.glTranslatef(3.5f, 1.4f, -10.0f);
            gl.glScalef(3f, 0.5f, 0.8f);
            rectangulo.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 1
        gl.glPushMatrix();
        {
            gl.glTranslatef(2f, 0.4f, -10.8f);
            gl.glScalef(0.8f, 0.8f, 0.2f);
            llanta1.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 2
        gl.glPushMatrix();
        {
            gl.glTranslatef(2f, 0.4f, -9.2f);
            gl.glScalef(0.8f, 0.8f, 0.2f);
            llanta2.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 3
        gl.glPushMatrix();
        {
            gl.glTranslatef(5f, 0.4f, -10.8f);
            gl.glScalef(0.8f, 0.8f, 0.2f);
            llanta3.dibujar(gl);
        }
        gl.glPopMatrix();

        // LLANTA 4
        gl.glPushMatrix();
        {
            gl.glTranslatef(5f, 0.4f, -9.2f);
            gl.glScalef(0.8f, 0.8f, 0.2f);
            llanta4.dibujar(gl);
        }
        gl.glPopMatrix();


        // Actualizar el valor de rotación para el próximo fotograma
        //rotacion += 1.0f;
        translacion += translacionDelta;
        anguloRotacion1 -= 0.02f;
    }
}
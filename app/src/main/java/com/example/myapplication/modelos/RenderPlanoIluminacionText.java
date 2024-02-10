package com.example.myapplication.modelos;

import static android.opengl.GLES10.GL_LIGHT0;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.myapplication.R;
import com.example.myapplication.utilidades.Funciones;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderPlanoIluminacionText implements GLSurfaceView.Renderer {
    private float vIncremento = 0f;
    private final static int LUZ0 = GL_LIGHT0;
    private final static int LUZ1 = GL_LIGHT0;
    private PlanoIluminacion planoIluminacion;
    private PlanoIluminacion planoIluminacion2;
    private PlanoIluminacion planoIluminacion3;
    private EsferaLuz2 esferaLuz;
    private float translacion = 0;
    private float rotacion = 0;

    private int[] arrayTexturas = new int[1];//Identificadores de texturas
    private Context context;

    public RenderPlanoIluminacionText(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.07059f, 0.07059f, 0.07059f, 1.0f);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        planoIluminacion = new PlanoIluminacion();
        planoIluminacion2 = new PlanoIluminacion();
        planoIluminacion3 = new PlanoIluminacion();
        esferaLuz = new EsferaLuz2(30,30,1,1);

        gl.glGenTextures(1, arrayTexturas, 0);
        gl.glEnable(gl.GL_TEXTURE_2D);

        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.esfera);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_NEAREST);
        bitmap.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glFrustumf(-1.0f, 1.0f, bottom, top, 1.5f, 30f);
        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);

        GLU.gluLookAt(gl,
                0, 0, 5, //Posicion de la camara
                0, 0, 0, //a donde mira la camara
                0, 1, 0 //que eje se considera arriba para la camara
        );

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        vIncremento += 0.05F;

        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        float[] posicion = {
                (float) (Math.cos(vIncremento)), -0.8f, 0f, 1.0f
        };
        float[] posicion2 = {
                0, (float) (Math.cos(vIncremento)), 0f, 0.7f
        };
        float[] posicion3 = {
                0.9f, 0f, -2.0f, 1f
        };
        float[] color = {
                1.0f, 0.2f, 0.2f, 1.0f
        };
        float[] colorAmbient = {
                0.2f, 0.2f, 0.2f, 1.0f
        };
        float[] colorDifuso = {
                0.3f, 0.8f, 0.3f, 1.0f
        };

        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAmbient));
        gl.glLightfv(LUZ0, gl.GL_POSITION, Funciones.generarBuffer(posicion));
//        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(new float[]{0f, .5f, 0f, 1f}));
//        gl.glLightfv(LUZ0,gl.GL_POSITION, Funciones.generarBuffer(new float[]{0f,0f,0f,1f}));


//        gl.glLightfv(LUZ0,gl.GL_AMBIENT, Funciones.generarBuffer(colorAmbient));
//        gl.glLightfv(LUZ0,gl.GL_DIFFUSE, Funciones.generarBuffer(color));
//        gl.glLightfv(LUZ0,gl.GL_SPECULAR, Funciones.generarBuffer(colorDifuso));
        gl.glEnable(LUZ0);


        //AFECTA A TODA LA ESCENA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        gl.glScalef(1.5f, 1.5f, 1.5f);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        gl.glPushMatrix();//Plano1
        {
            planoIluminacion.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();//PLANO2 ATRAS
        {
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(color));
            gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicion2));

            gl.glRotatef(90, 1, 0, 0);
            planoIluminacion2.dibujar(gl);

        }
        gl.glPopMatrix();

        gl.glPushMatrix();//PLANO 3 DERECHA
        {
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorDifuso));
            gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicion3));

            gl.glRotatef(90, 0, 0, 1);
            planoIluminacion3.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
        gl.glEnableClientState(gl.GL_TEXTURE_COORD_ARRAY);//Estado de las coordenadas de textura

        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);
            gl.glTranslatef(0.0f, 0.0f ,-3.0f );
            gl.glScalef( 0.5f, 0.5f, 0.5f);
            esferaLuz.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        rotacion += 0.8f;
        translacion += 0.01f;

    }
}

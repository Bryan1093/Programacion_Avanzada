package com.example.myapplication.renderes.gl10;

import static android.opengl.GLES10.GL_LIGHT0;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.myapplication.R;
import com.example.myapplication.modelos.gl10.Esferatextura;
import com.example.myapplication.modelos.gl10.ObjModel;
import com.example.myapplication.modelos.gl10.PlanoTextura;
import com.example.myapplication.modelos.gl10.PlanoIluminacion;
import com.example.myapplication.modelos.gl10.PlanoTexturizado;
import com.example.myapplication.utilidades.Funciones;

import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderFinalBlend implements GLSurfaceView.Renderer {
    private float vIncremento = 0f;
    private float rotacion =0f;
    private float translacion = 0;
    private final static int LUZ0 = GL_LIGHT0;
    private final static int LUZ1 = GL_LIGHT0;
    private PlanoTextura plano;
    private Esferatextura balon;
    private PlanoIluminacion planoIluminacion;
    private PlanoIluminacion planoIluminacion2;
    private PlanoIluminacion planoIluminacion3;
    private PlanoTexturizado planetas;
    private ObjModel dona;


    private float incrementodensidadnieblibna = 0.001f;
    private float densidadnieblibna = 0.0f;

    private float[] posLuz0 = {0.0f, -1.0f, -3.0f, 1.0f};
    private float[] colorBlanco = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] colorRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] colorVerde = {0.0f, 1.0f, 0.0f, 1.0f};
    private float[] colorAzul = {0.0f, 0.0f, 1.0f, 1.0f};
    private float[] colorNegro = {0.0f, 0.0f, 0.0f, 1.0f};
    private float[] colorLuz0 = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] colorLuzModel = {0.9f, 0.9f, 0.9f, 1.0f};

    //Materiales
    private float[] materialBlanco = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] materialRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] materialVerde = {0.0f, 1.0f, 0.0f, 1.0f};
    private float[] materialAzul = {0.0f, 0.0f, 1.0f, 1.0f};
    private float[] materialNegro = {0.0f, 0.0f, 0.0f, 1.0f};
    private float[] materialAmarillo = {1.0f, 1.0f, 0.0f, 1.0f};


    private float[] posLu1 = {0.0f, -1.0f, -5.0f, 1.0f};
    private float[] colorLuz1 = {0.0f, 1.0f, 1.0f, 1.0f};

    private float[] spotDirection;
    private float[] spotExponent;
    private float[] spotCutoff;
    private int[] arrayTexturas = new int[2];//Identificadores de texturas
    private Context context;
    private BitmapFactory.Options options;
    private float incremento = -0.1f, distancia = -2.0f;

    public RenderFinalBlend(Context context) {
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glEnable(gl.GL_LIGHTING);
        //gl.glEnable(gl.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        dona = new ObjModel("donaBlender.obj", new float[]{0.902f, 0.0706f, 0.7922f, 1}, this.context);

        gl.glEnable(gl.GL_TEXTURE_2D);

        planoIluminacion = new PlanoIluminacion();
        planoIluminacion2 = new PlanoIluminacion();
        planoIluminacion3 = new PlanoIluminacion();


        gl.glEnable(gl.GL_NORMALIZE);
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posLu1));


        gl.glGenTextures(2, arrayTexturas, 0);

        Bitmap bitmap2;
        balon = new Esferatextura(30,30,1,1);

        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.balon);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap2, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap2.recycle();

        Bitmap bitmap;
        plano = new PlanoTextura();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.albion);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[1]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap.recycle();
/*
        //nieblita
        gl.glFogfv(gl.GL_FOG_COLOR, FloatBuffer.wrap(colorBlanco));
        gl.glFogf(gl.GL_FOG_MODE,gl.GL_EXP);
        gl.glFogf(gl.GL_FOG_DENSITY, 0.0f);
        //gl.glFogf(gl.GL_FOG_START,0.8f);
        gl.glFogf(gl.GL_FOG_END,1.0f);
        gl.glEnable(gl.GL_FOG);
 */
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(LUZ0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-1.0f, 1, bottom, top, 1f, 50.0f);
        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);

        GLU.gluLookAt(gl,
                0, 0, 2, //Posicion de la camara
                0, 0, 0, //a donde mira la camara
                0, 1, 0 //que eje se considera arriba para la camara
        );

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        vIncremento += 0.05f;

/*
        posLuz0 = new float[]{0,1.0f,-3.0f,1.0f};
        gl.glLightfv(LUZ0,gl.GL_POSITION, Funciones.generarBuffer(posLuz0));
        gl.glLightfv(LUZ0, gl.GL_DIFFUSE, FloatBuffer.wrap(colorRojo));

        spotDirection = new float[]{.0f,-1.0f,0.0f,1.0f};
        spotCutoff = new float[]{15.0f};
        spotExponent = new float[]{20.0f};
        gl.glLightfv(LUZ0,gl.GL_SPOT_DIRECTION, FloatBuffer.wrap(spotDirection));
        gl.glLightfv(LUZ0,gl.GL_SPOT_CUTOFF, FloatBuffer.wrap(spotCutoff));
        gl.glLightfv(LUZ0,gl.GL_SPOT_EXPONENT, FloatBuffer.wrap(spotExponent));

        posLu1 = new float[]{0,1.0f,-3.0f,1.0f};
        gl.glLightfv(LUZ1,gl.GL_POSITION, Funciones.generarBuffer(posLu1));
        gl.glLightfv(LUZ1, gl.GL_DIFFUSE, FloatBuffer.wrap(colorVerde));

        spotDirection = new float[]{.0f,-1.0f,-1.0f,1.0f};
        spotCutoff = new float[]{10.0f};
        spotExponent = new float[]{10.0f};
        gl.glLightfv(LUZ0,gl.GL_SPOT_DIRECTION, FloatBuffer.wrap(spotDirection));
        gl.glLightfv(LUZ0,gl.GL_SPOT_CUTOFF, FloatBuffer.wrap(spotCutoff));
        gl.glLightfv(LUZ0,gl.GL_SPOT_EXPONENT, FloatBuffer.wrap(spotExponent));




        if(distancia < -120.0f || distancia > -2.0f)
            incremento *= -1;

        distancia += incremento;

        if(densidadnieblibna < 0.0f || densidadnieblibna > 4.0f)
            incrementodensidadnieblibna *= -1;
        densidadnieblibna += incrementodensidadnieblibna;

        gl.glFogf(gl.GL_FOG_DENSITY, densidadnieblibna);
 */



        float[] posicion = {//luz para la posicion del plano inferior
                (float) (Math.cos(vIncremento)), -0.8f, 0f, 1.0f
        };
        float[] posicion2 = {//luz para la posicion del plano de atras
                0, (float) (Math.cos(vIncremento)), 0f, 1.0f
        };
        //luz para la posicion del plano de la derecha
        float[] posicion3 = {0.9f, (float) (Math.cos(vIncremento)), 0, 1f
        };

        float[] posicionTierra = {
                (float) (Math.cos(vIncremento)), 0.0f, 1f, 0.1f
        };
        float[] color = { //cammbiar el color del plano Izquierdo
                1.0f, 1.2f, 1.2f, 1.0f
        };
        float[] colorAmbient = {//cammbiar el color del plano inferior
                0.2f, 0.2f, 0.2f, 1.0f
        };
        float[] colorDifuso = {//cammbiar el color del plano Derecho
                0.3f, 0.8f, 0.3f, 1.0f
        };

        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAmbient));
          gl.glLightfv(LUZ0, gl.GL_POSITION, Funciones.generarBuffer(posicion));
/*
        //TODA LA ESCENA--------------------------------------------------
        gl.glRotatef(vIncremento * 2, 0f, 1, 0);
        //----------------------------------------------------------------

 */

        gl.glEnable(LUZ0);

        //AFECTA A TODA LA ESCENA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        gl.glScalef(1.0f, 1.0f, 1.0f);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        gl.glPushMatrix();//Plano Abajo
        gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT,materialBlanco,0); //ambiental todo
        //   gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_DIFFUSE,materialVerde,0);
         //  gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_SPECULAR,materialVerde,0);
        //   gl.glMaterialf(gl.GL_FRONT_AND_BACK, gl.GL_SHININESS, (float) (60*Math.sin(vIncremento)));
        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAzul));
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicion));
        planoIluminacion.dibujar(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();//PLANO2 ATRAS
        //Aqui es para hacer un plano normal

        //Luz plano
        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorRojo));
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicion2));
            gl.glRotatef(90, 1, 0, 0);
            //Neblina en plano

            //gl.glEnable(gl.GL_BLEND);
            //gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);

        planoIluminacion2.dibujar(gl);

                //Aqui es para hacer un planoTextura
                gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]); // Textura para la cara 1
                gl.glRotatef(270, 1, 0, 0); // Orientar correctamente
                //plano.dibujar(gl); // Dibujar la cara

        gl.glPopMatrix();

        gl.glPushMatrix();//PLANO 3 DERECHA
        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorDifuso));
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicion3));
        gl.glRotatef(90, 0, 0, 1);
        //    gl.glEnable(gl.GL_BLEND);
        //    gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
        planoIluminacion3.dibujar(gl);
        gl.glPopMatrix();

        //BALON:
        gl.glPushMatrix();

        gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);
        gl.glTranslatef(0f, 0f, 0);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorBlanco));
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicionTierra));
        gl.glRotatef(rotacion,0,1,0);
        //gl.glDisable(gl.GL_BLEND);
        //gl.glEnable(gl.GL_BLEND);
        //gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
        //astro.dibujar(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 0f, 0);
            gl.glScalef(0.3f,0.3f,0.2f);
            gl.glRotatef(rotacion,1,0,0);
            //gl.glDisable(gl.GL_BLEND);
            //gl.glEnable(gl.GL_BLEND);
            //gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
            dona.dibujar(gl);
        }
        gl.glPopMatrix();

        rotacion += 0.9f;
        translacion += 0.01f;
    }
}
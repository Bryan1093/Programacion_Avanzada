package com.example.myapplication.renderes.gl10;

import static android.opengl.GLES10.GL_LIGHT0;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.example.myapplication.R;
import com.example.myapplication.modelos.gl10.Esferatextura;
import com.example.myapplication.modelos.gl10.PlanoIluminacion;
import com.example.myapplication.modelos.gl10.PlanoTexturizado;
import com.example.myapplication.utilidades.Funciones;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RenderPlanoIluminacionText implements GLSurfaceView.Renderer {
    private float vIncremento = 0f;
    private final static int LUZ0 = GL_LIGHT0;
    private final static int LUZ1 = GL_LIGHT0;
    private PlanoIluminacion planoIluminacion;
    private PlanoTexturizado planoIluminacion2;
    private PlanoIluminacion planoIluminacion3;
    private Esferatextura esferaLuz;
    private float translacion = 0;
    private float rotacion = 0;
    private float incrementoDensidad= 0.1f;
    private float densidad = 0.0f;

    private float[] posLuz0 = {0.0f,-1.0f,-3.0f,1.0f};
    private float[] colorBlanco = {1.0f,1.0f,1.0f,1.0f};
    private float[] colorRojo = {1.0f,0.0f,0.0f,1.0f};
    private float[] colorVerde = {0.0f,1.0f,0.0f,1.0f};
    private float[] colorAzul = {0.0f,0.0f,1.0f,1.0f};
    private float[] colorNegro = {0.0f,0.0f,0.0f,1.0f};
    private float[] colorLuz0 = {1.0f,0.0f,0.0f,1.0f};
    private float[] colorLuzModel = {0.9f,0.9f,0.9f,1.0f};

    //Materiales
    private float[] materialBlanco = {1.0f,1.0f,1.0f,1.0f};
    private float[] materialRojo = {1.0f,0.0f,0.0f,1.0f};
    private float[] materialVerde = {0.0f,1.0f,0.0f,1.0f};
    private float[] materialAzul = {0.0f,0.0f,1.0f,1.0f};
    private float[] materialNegro = {0.0f,0.0f,0.0f,1.0f};
    private float[] materialAmarillo = {1.0f,1.0f,0.0f,1.0f};


    private float[] posLu1 = {0.0f,-1.0f,-5.0f,1.0f};
    private float[] colorLuz1 = {0.0f,1.0f,1.0f,1.0f};

    private float [] spotDirection;
    private float [] spotExponent;
    private float [] spotCutoff;
    private int[] arrayTexturas = new int[1];//Identificadores de texturas
    private Context context;
    private BitmapFactory.Options options;
    private  float incremento = -0.1f, distancia = -2.0f;

    public RenderPlanoIluminacionText(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.07059f, 0.07059f, 0.07059f, 1.0f);
        gl.glEnable(gl.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        planoIluminacion2 = new PlanoTexturizado();
        esferaLuz = new Esferatextura(30,30,1,1);

        gl.glEnable(gl.GL_NORMALIZE);

        gl.glLightfv(LUZ1,gl.GL_POSITION,Funciones.generarBuffer(posLu1));

        options = new BitmapFactory.Options();
        options.inScaled = false;

        gl.glGenTextures(1, arrayTexturas, 0);
        gl.glEnable(gl.GL_TEXTURE_2D);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        generarMipMap(gl,R.drawable.i512,0);
        generarMipMap(gl,R.drawable.i256,1);
        generarMipMap(gl,R.drawable.i128,2);
        generarMipMap(gl,R.drawable.i64,3);
        generarMipMap(gl,R.drawable.i32,4);
        generarMipMap(gl,R.drawable.i16,5);
        generarMipMap(gl,R.drawable.i8,6);
        generarMipMap(gl,R.drawable.i4,7);
        generarMipMap(gl,R.drawable.i2,8);
        generarMipMap(gl,R.drawable.i1,9);
        gl.glActiveTexture(gl.GL_TEXTURE_2D);
        
        gl.glFogfv(gl.GL_FOG_COLOR, FloatBuffer.wrap(colorBlanco));
        gl.glFogf(gl.GL_FOG_MODE, gl.GL_EXP);

        gl.glFogf(gl.GL_FOG_START, 0.8f);
        gl.glFogf(gl.GL_FOG_END, 4.0f);
        gl.glEnable(gl.GL_FOG);
    }

    private void generarMipMap(GL10 gl, int idRecurso, int nivel){

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeResource(context.getResources(), idRecurso, options);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, nivel, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR_MIPMAP_NEAREST);
        bitmap.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glFrustumf(-1.0f, 1.0f, bottom, top, 1.5f, 150f);
        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);

        /*GLU.gluLookAt(gl,
                0, 0, 5, //Posicion de la camara
                0, 0, 0, //a donde mira la camara
                0, 1, 0 //que eje se considera arriba para la camara
        );*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        vIncremento += 0.05F;

        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);

        gl.glEnable(gl.GL_LIGHTING);
        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorLuzModel));
        gl.glEnable(LUZ0);
        gl.glEnable(LUZ1);

        gl.glLoadIdentity();

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


        if(densidad < -0.8f || densidad > 4.5f)
            incrementoDensidad *= 1;

        densidad += incrementoDensidad;

        gl.glFogf(gl.GL_FOG_DENSITY, rotacion);

        gl.glLoadIdentity();
        gl.glDisable(gl.GL_BLEND);

        gl.glTranslatef(0.0f,0.0f, -4.5f);
        gl.glRotatef(rotacion,0,1,0);
        esferaLuz.dibujar(gl);


        gl.glTranslatef(0.0f,0.0f,-0.6f);
        gl.glRotatef(90,1,0,0);

        gl.glEnable(gl.GL_BLEND);
        gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
        //gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        planoIluminacion2.dibujar(gl);

        rotacion += 0.8f;
        translacion += 0.01f;

    }
}
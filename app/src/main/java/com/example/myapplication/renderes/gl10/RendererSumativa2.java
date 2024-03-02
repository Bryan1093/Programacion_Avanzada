package com.example.myapplication.renderes.gl10;

import static android.opengl.GLES10.GL_LIGHT0;
import static javax.microedition.khronos.opengles.GL10.GL_LIGHT1;
import static javax.microedition.khronos.opengles.GL10.GL_LIGHT2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.myapplication.R;
import com.example.myapplication.modelos.gl10.Cilindro;
import com.example.myapplication.modelos.gl10.Cono;
import com.example.myapplication.modelos.gl10.EsferaLuz;
import com.example.myapplication.modelos.gl10.Esferatextura;
import com.example.myapplication.modelos.gl10.ObjModel;
import com.example.myapplication.modelos.gl10.PlanoTextura;
import com.example.myapplication.utilidades.Funciones;

import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RendererSumativa2 implements GLSurfaceView.Renderer {
    private float vIncremento = 0f;
    private float rotacion =0f;
    private float translacion = 0;
    private final static int LUZ0 = GL_LIGHT0;
    private final static int LUZ1 = GL_LIGHT1;
    private final static int LUZ2 = GL_LIGHT2;
    private PlanoTextura plano, plano2;
    private EsferaLuz foco;
    private Esferatextura balon;
    private Cono pista1, pista2, pista3,copa,copa2;
    private ObjModel dona, langosta;
    private Cilindro poste, poste2, poste3, poste4;

    private float[] posLuz0 = {0.0f, -1.0f, -3.0f, 1.0f};
    private float[] colorBlanco = {0.8f, 0.8f, 0.8f, 1.0f};
    private float[] colorRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] colorVerde = {0.0f, 1.0f, 0.0f, 1.0f};
    private float[] colorAzul = {0.0f, 0.0f, 1.0f, 1.0f};
    private float[] colorNegro = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] colorCafe = {0.65f, 0.40f, 0.12f, 1.0f};
    private float[] colorVerdeArbol = {0.0f, 0.5f, 0.0f, 1.0f};
    private float[] colorRosado= {1.0f, 0.4f, 0.7f, 1.0f};
    private float[] colorAmarillo= {1.0f, 1.0f, 0.0f, 1.0f};
    private float[] colorLuz0 = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] colorLuzModel = {0.9f, 0.9f, 0.9f, 1.0f};

    //Materiales
    private float[] materialBlanco = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] materialRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] materialVerde = {0.0f, 1.0f, 0.0f, 1.0f};
    private float[] materialAzul = {0.0f, 0.0f, 1.0f, 1.0f};
    private float[] materialRosado= {1.0f, 0.4f, 0.7f, 1.0f};
    private float[] materialNegro = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] materialCafe = {0.65f, 0.40f, 0.12f, 1.0f};
    private float[] materialVerdeArbol = {0.0f, 0.5f, 0.0f, 1.0f};
    private float[] materialAmarillo = {1.0f, 1.0f, 0.0f, 1.0f};


    private float[] posLu1 = {0.0f, -1.0f, -5.0f, 1.0f};
    private float[] colorLuz1 = {0.0f, 1.0f, 1.0f, 1.0f};
    private int[] arrayTexturas = new int[2];
    private Context context;
    private boolean Rotacion = true;
    private float incremento = -0.1f, distancia = -2.0f;
    double[][] coloresCilindros = {
            {0.5, 0.5, 0.5, 1.0}, //sombrilla
    };


    public RendererSumativa2(Context context, boolean rotacion) {
        this.context = context;
        this.Rotacion = rotacion;
    }
    float[] posicionLuz0 = {-20f, -18f, 0.0f, 1.0f};
    float[] posicionLuz1 = {-20f, -18f, 0.0f, 1.0f};
    float[] posicionLuz2 = {-20f, -18f, 0.0f, 1.0f};

    float[] luzAmarilla = {
            0.9f, 0.9f, 0.0f, 1.0f
    };
    float[] luzVerde = {
            0f, 0.8f, 0.0f, 1.0f
    };
    float[] luzRoja = {
            0.8f, 0.0f, 0.0f, 1.0f
    };
    private float[] spotDir1;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(GL10.GL_BLEND);
        gl.glEnable(gl.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        dona = new ObjModel("donaBlender.obj", new float[]{0.902f, 0.0706f, 0.7922f, 1}, this.context);
        langosta = new ObjModel("langosta.obj", new float[]{1, 0f, 0f, 1}, this.context);

        pista1 = new Cono(5,0.2f,30,coloresCilindros[0]);
        pista2 = new Cono(3.5f,0.2f,30,coloresCilindros[0]);
        pista3 = new Cono(2,0.2f,30,coloresCilindros[0]);
        copa = new Cono(2,4f,20,coloresCilindros[0]);
        copa2 = new Cono(2,4f,20,coloresCilindros[0]);

        // Inicializar objeto Cilindro
        float cilindroRadius = 1.0f;
        float cilindroHeight = 2.0f;
        int cilindroNumSlices = 30;

        poste = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);
        poste2 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);
        poste3 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);
        poste4 = new Cilindro(cilindroRadius, cilindroHeight, cilindroNumSlices, coloresCilindros[0]);
        gl.glEnable(gl.GL_TEXTURE_2D);

        gl.glEnable(gl.GL_NORMALIZE);
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posLu1));


        gl.glGenTextures(2, arrayTexturas, 0);

        Bitmap bitmap2;
        plano = new PlanoTextura();

        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pastor);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap2, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap2.recycle();

        Bitmap bitmap;
        plano2 = new PlanoTextura();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gato);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[1]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap.recycle();

        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(LUZ0);

        balon = new Esferatextura(30,30,1,1);

        //Luz 0---------------------------------------------------------------------
        gl.glLightfv(LUZ0, gl.GL_POSITION, Funciones.generarBuffer(posicionLuz0));
        gl.glLightfv(LUZ0, gl.GL_DIFFUSE, Funciones.generarBuffer(luzAmarilla));

        //Luz 1---------------------------------------------------------------------
        gl.glLightfv(LUZ1, gl.GL_POSITION, Funciones.generarBuffer(posicionLuz1));
        gl.glLightfv(LUZ1, gl.GL_DIFFUSE, Funciones.generarBuffer(luzVerde));

        //LUZ2---------------------------------------------------------------------
        gl.glLightfv(LUZ2, gl.GL_POSITION, Funciones.generarBuffer(posicionLuz2));
        gl.glLightfv(LUZ2, gl.GL_DIFFUSE, Funciones.generarBuffer(luzRoja));

        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(LUZ0);
        gl.glEnable(LUZ1);
        gl.glEnable(LUZ2);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        gl.glViewport(0, 0, ancho, alto);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-1.0f, 1, bottom, top, 1.5f, 200.0f);
        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);

        GLU.gluLookAt(gl,
                0f, 0f, 5,
                0, 0, 0,
                0, 1, 0
        );

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        vIncremento += 0.05f;

        float[] posicion = {//luz para la posicion del plano inferior
                (float) (Math.cos(vIncremento)), -0.8f, 0f, 1.0f
        };
        float[] posicion2 = {//luz para la posicion del plano de atras
                0, (float) (Math.cos(vIncremento)), 0f, 1.0f
        };
        float[] posicion3 = {0.9f, (float) (Math.cos(vIncremento)), 0, 1f
        };

        float[] posicionBalon = {
                (float) (Math.cos(vIncremento)), 0.0f, 1f, 0.1f
        };
        float[] color = { //color del plano Izquierdo
                1.0f, 1.2f, 1.2f, 1.0f
        };
        float[] colorAmbient = {//color del plano inferior
                0.2f, 0.2f, 0.2f, 1.0f
        };
        float[] colorDifuso = {//color del plano Derecho
                0.3f, 0.8f, 0.3f, 1.0f
        };

        gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAmbient));
          gl.glLightfv(LUZ0, gl.GL_POSITION, Funciones.generarBuffer(posicion));

        if (Rotacion) {
            //TODA LA ESCENA--------------------------------------------------
            gl.glRotatef(vIncremento * 2, 0, 1, 0);//vincremento estaba en 2
            //----------------------------------------------------------------
        }
        gl.glEnable(LUZ0);

        //AFECTA A TODA LA ESCENA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        gl.glScalef(1.0f, 1.0f, 1.0f);
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        gl.glPushMatrix();
        {//Plano INFERIOR
         gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT,materialVerde,0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorVerde));
            gl.glTranslatef(0, -2f, 0);
            gl.glScalef(0.5f,0.1f,0.5f);
            //gl.glRotatef(180, 1, 0, 0);
            pista1.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT,materialNegro,0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorNegro));
            gl.glTranslatef(0, -1.9f, 0);
            gl.glScalef(0.5f,0.12f,0.5f);
            //gl.glRotatef(180, 1, 0, 0);
            pista2.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT,materialVerde,0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorVerde));

            gl.glTranslatef(0, -1.7f, 0);
            gl.glScalef(0.5f,0.14f,0.5f);
            //gl.glRotatef(180, 1, 0, 0);
            pista3.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glTranslatef(0, 0.5f, 0);
            gl.glScalef(0.1f, 0.1f, 0.2f);

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialCafe, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorCafe));

                gl.glTranslatef(0, -18f, 0);
                gl.glScalef(2f, 5f, 2f);
                //gl.glRotatef(180, 1, 0, 0);
                poste.dibujar(gl);

            }
            gl.glPopMatrix();

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialVerdeArbol, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorVerdeArbol));

                gl.glTranslatef(0, -15f, 0);
                gl.glScalef(2f, 3f, 2f);
                //gl.glRotatef(180, 1, 0, 0);
                copa.dibujar(gl);

            }
            gl.glPopMatrix();

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialCafe, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorCafe));

                gl.glTranslatef(-12, -23f, -9);
                gl.glScalef(0.8f, 1.5f, 0.8f);
                //gl.glRotatef(180, 1, 0, 0);
                poste2.dibujar(gl);

            }
            gl.glPopMatrix();

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialVerdeArbol, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorVerdeArbol));

                gl.glTranslatef(-12, -22f, -9);
                gl.glScalef(0.7f, 1.3f, 0.7f);
                //gl.glRotatef(180, 1, 0, 0);
                copa2.dibujar(gl);

            }
            gl.glPopMatrix();

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialBlanco, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorBlanco));

                gl.glTranslatef(22, -18f, 0);
                gl.glScalef(0.5f, 5.5f, 0.5f);
                //gl.glRotatef(180, 1, 0, 0);
                poste3.dibujar(gl);

            }
            gl.glPopMatrix();

            gl.glPushMatrix();
            {
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialBlanco, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorBlanco));

                gl.glTranslatef(-20, -18f, 0);
                gl.glScalef(0.5f, 4.5f, 0.5f);
                //gl.glRotatef(180, 1, 0, 0);
                poste4.dibujar(gl);

            }
            gl.glPopMatrix();

        }gl.glPopMatrix();

        gl.glPushMatrix();
        {
        //Cambio de posición de luz amarilla
        //float[] nuevaPosicionLuz1 = {-1f, (float) Math.cos(vIncremento), 0.0f, 0.0f};
        gl.glLightfv(LUZ1, gl.GL_POSITION, FloatBuffer.wrap(posicionLuz1));
        //-----------------------

        gl.glPushMatrix();
        {//Plano1
            spotDir1 = new float[]{-1, 0, 0};
            gl.glLightfv(LUZ1, gl.GL_SPOT_DIRECTION, FloatBuffer.wrap(spotDir1));
            gl.glLightf(LUZ1, gl.GL_SPOT_CUTOFF, 00);
            gl.glLightf(LUZ1, gl.GL_SPOT_EXPONENT, 1);
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialAmarillo, 0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAmarillo));
            gl.glTranslatef(2.2f,-0.6f,0);
            gl.glScalef(0.2f,0.2f,0.2f);
            balon.dibujar(gl);
        }
        gl.glPopMatrix();
        }gl.glPopMatrix();

        gl.glPushMatrix();
        {

            //TODO EL CUADRADO--------------------------------------------------
            gl.glTranslatef(0,-0.7f,0);
            gl.glScalef(1.2f,1.2f,1.2f);
            //gl.glRotatef(vIncremento * 2, 0f, 1, 0);
            //----------------------------------------------------------------

        gl.glPushMatrix();
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);
            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialBlanco, 0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorBlanco));
            gl.glTranslatef(-2, 0.2f, 0);
            gl.glScalef(0.3f,0.2f,0.05f);
            gl.glDisable(gl.GL_BLEND);
            gl.glEnable(gl.GL_BLEND);
            gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
            plano.dibujar(gl);
        }
        gl.glPopMatrix();

            gl.glPushMatrix();
            {

                gl.glEnable(gl.GL_BLEND);
                gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_DST_ALPHA);
                gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]);
                gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialBlanco, 0); //ambiental todo
                gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorBlanco));
                gl.glTranslatef(-1.5f, 0.1f, 0);
                gl.glScalef(0.3f,0.2f,0.05f);
                plano2.dibujar(gl);

                gl.glDisable(gl.GL_BLEND);
            }
            gl.glPopMatrix();
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glRotatef(-rotacion, 0, 1, 0);

            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialAzul, 0);
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorAzul));
            gl.glTranslatef(0.8f, -1.8f, 0f);
            gl.glScalef(0.07f,0.07f,0.07f);
            gl.glRotatef(90, 1, 0, 0);
            gl.glRotatef(90, 0, 0, 1);
            gl.glRotatef(180, 0, 1, 0);
            langosta.dibujar(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();
        {
            gl.glRotatef(-rotacion, 0, 1, 0);

            gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, materialRosado, 0); //ambiental todo
            gl.glLightModelfv(gl.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(colorRosado));
            gl.glTranslatef(-0.8f, -1.6f, 0f);
            gl.glScalef(0.2f,0.1f,0.2f);
            dona.dibujar(gl);
        }
        gl.glPopMatrix();

        rotacion += 0.9f;
        translacion += 0.05f;
    }
}
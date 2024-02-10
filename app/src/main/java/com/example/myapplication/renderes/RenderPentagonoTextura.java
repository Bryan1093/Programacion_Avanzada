package com.example.myapplication.renderes;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.example.myapplication.R;
import com.example.myapplication.modelos.CuadradoTextura;
import com.example.myapplication.modelos.HexagonoTextura;
import com.example.myapplication.modelos.PentagonoTextura;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
public class RenderPentagonoTextura implements GLSurfaceView.Renderer {
    private float vIncremento;
    private PentagonoTextura pentagonoArriba, pentagonoAbajo;
    private CuadradoTextura lado1,lado2,lado3,lado4,lado5;
    private int[] arrayTexturas = new int[7];//Se utilizará para almacenar las identificaciones de textura generadas por OpenGL.
    private Context context;

    public RenderPentagonoTextura(Context context){
        this.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.234f,0.247f,0.255f,1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glEnable(gl.GL_TEXTURE_2D);

        gl.glGenTextures(7,arrayTexturas,0);//Genera una textura en OpenGL y guarda su identificación en el array arrayTexturas.

        pentagonoArriba = new PentagonoTextura();

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ldu1);//Decodifica un recurso de imagen en forma de Bitmap.
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[0]);//Vincula la textura generada anteriormente para su uso en OpenGL.
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap, 0);//Carga la imagen en la textura vinculada.
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);//Configura el filtro de ampliación de la textura.
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);//Configura el filtro de reducción de la textura.

        bitmap.recycle();

        Bitmap bitmap1;
        pentagonoAbajo = new PentagonoTextura();

        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ronaldo);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[1]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap1, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap1.recycle();

        Bitmap bitmap2;
        lado1 = new CuadradoTextura();

        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.messi);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[2]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap2, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap2.recycle();

        Bitmap bitmap3;
        lado2 = new CuadradoTextura();

        bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.united);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[3]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap3, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap3.recycle();

        Bitmap bitmap4;
        lado3 = new CuadradoTextura();

        bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.albion);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[4]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap4, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap4.recycle();

        Bitmap bitmap5;
        lado4 = new CuadradoTextura();

        bitmap5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.shady);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[5]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap5, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap5.recycle();

        Bitmap bitmap6;
        lado5 = new CuadradoTextura();

        bitmap6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.uce);
        gl.glBindTexture(gl.GL_TEXTURE_2D, arrayTexturas[6]);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D, 0, bitmap6, 0);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        gl.glTexParameterf(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);

        bitmap6.recycle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        float aspectRatio = (float) ancho / alto;
        float left = -1.0f;
        float right = 1.0f;
        float bottom = -1.0f / aspectRatio;
        float top = 1.0f / aspectRatio;
        float near = 23.0f;
        float far = 150.0f;
        gl.glViewport(0, 0, ancho, alto);//origen "x=0" y "y=0" por defecto alto y ancho de la pantalla, es practicamente la ventana de copordenas donde se va a dibujar
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();//
        gl.glFrustumf(left, right, bottom, top, near, far);

        gl.glTexEnvf(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_REPLACE);//Configura el modo de mezcla de textura. Aqui usado para que no se mezcle con los colores definidos en la geometria.

        GLU.gluLookAt(gl,
                -20, 20, 20,
                0, 0, 0,
                0, 1, 0
        );

    }
    @Override
    public void onDrawFrame(GL10 gl) {
        vIncremento += 0.22f;

        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();

        //AFECTA TODA LA ESCENA--------------------------
        gl.glTranslatef(0, 0,0);
        gl.glRotatef(-vIncremento * 4, 0f, 1f, 0f);
        gl.glRotatef(-vIncremento, 1f, 0f, 0f);
        gl.glRotatef(-vIncremento, 0f, 0f, 1f);
        //-----------------------------------------------

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[0]);
            gl.glTranslatef(0,-2.025f,0);
            gl.glScalef(2,3,2);
            gl.glRotatef(90,1,0,0);
            pentagonoAbajo.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[1]);
            gl.glTranslatef(0,-0.41f,0);
            gl.glScalef(2,3,2);
            gl.glRotatef(90,1,0,0);
           pentagonoArriba.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[2]);
            gl.glTranslatef(0.82f,-1.22f,0.39f);
            gl.glScalef(0.46f,0.8f,0.46f);
            gl.glRotatef(64,0,1,0);
            gl.glRotatef(180,1,0,0);
            lado1.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

              gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[3]);
            gl.glTranslatef(-0.82f,-1.22f,0.39f);
            gl.glScalef(0.45f,0.8f,0.45f);
            gl.glRotatef(-64,0,1,0);
            gl.glRotatef(180,1,0,0);
            lado2.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[4]);
            gl.glTranslatef(0.5f,-1.22f,-0.205f);
            gl.glScalef(0.54f,0.8f,0.54f);
            gl.glRotatef(22,0,-1,0);
            gl.glRotatef(180,1,0,0);
            lado3.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[5]);
            gl.glTranslatef(-0.5f,-1.22f,-0.205f);
            gl.glScalef(0.54f,0.8f,0.54f);
            gl.glRotatef(-22,0,-1,0);
            gl.glRotatef(180,1,0,0);
            lado4.dibujar(gl);

        }gl.glPopMatrix();

        gl.glPushMatrix(); {

            gl.glBindTexture(GL10.GL_TEXTURE_2D, arrayTexturas[6]);
            gl.glTranslatef(0f,-1.22f,0.8f);
            gl.glScalef(0.61f,0.8f,0.61f);
            gl.glRotatef(180,0,1,0);
            gl.glRotatef(180,1,0,0);
            lado5.dibujar(gl);

        }gl.glPopMatrix();
    }
}

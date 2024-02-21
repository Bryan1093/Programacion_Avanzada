package com.example.myapplication.modelos.gl20;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindAttribLocation;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.utilidades.Funciones;
import com.example.myapplication.utilidades.Funciones20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cubo20 {
    private static final int COMPVERT = 3;
    private static final int COMPCOLO = 4;
    private static final int BYTEFLOAT = 4;
    private static final int STRIDE = (COMPVERT + COMPCOLO) * BYTEFLOAT;
    private FloatBuffer bufferVertices;
    private ByteBuffer bufferIndices;
    private FloatBuffer bufferColores;
    private int idPrograma;
    private Context contexto;

    public Cubo20(Context context) {
            this.contexto = context;

        float[] vertices = {
                // X     Y     Z     P    R      G     B    A
                   1,    1,    1,  //0
                   1,   -1,    1,  //1
                  -1,   -1,    1,  //2
                  -1,    1,    1,  //3
                   1,    1,   -1,  //4
                   1,   -1,   -1,  //5
                  -1,   -1,   -1,  //6
                  -1,    1,   -1   //7


        };

        float[] colores = {
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,

        };

        byte[] indices = {
                //caras frontales sentido horario
                0,1,2,
                0,2,3,
                0,3,7,
                0,7,4,
                0,4,5,
                0,5,1,
                //caras posteriores
                6,5,1,
                6,1,2,
                6,2,3,
                6,3,7,
                6,7,4,
                6,4,5
        };

        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferVertices = buffer.asFloatBuffer();
        bufferVertices.put(vertices);
        bufferVertices.position(0);


        // bufferIndices
        bufferIndices = ByteBuffer.allocateDirect(indices.length);
        bufferIndices.order(ByteOrder.nativeOrder());
        bufferIndices.put(indices);
        bufferIndices.position(0);

        //bufferColores

        buffer = ByteBuffer.allocateDirect(colores.length * BYTEFLOAT);
        buffer.order(ByteOrder.nativeOrder());
        bufferColores = buffer.asFloatBuffer();
        bufferColores.put(colores);
        bufferColores.position(0);

        /*Manejo Shaders*/

        //Crear source vertex shader
        StringBuilder sourceV = new StringBuilder();
        sourceV.append("attribute vec3 idVertice;");
        sourceV.append("\n");
        sourceV.append("void main() {");
        sourceV.append("\n");
        sourceV.append("gl_Position = vec4(idVertice, 1);");
        sourceV.append("\n");
        sourceV.append("}");

        //Crear source fragment shader
        StringBuilder sourceF = new StringBuilder();
        sourceF.append("precision mediump float;");
        sourceF.append("\n");
        sourceF.append("uniform vec4 color;");
        sourceF.append("\n");
        sourceF.append("void main() {");
        sourceF.append("\n");
        sourceF.append("gl_FragColor = color;");
        sourceF.append("\n");
        sourceF.append("}");

        //Sombreador de vertice
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int[] statusCompilation = new int[1];
        //glShaderSource(vertexShader, String.valueOf(sourceV));
        glShaderSource(vertexShader, Funciones20.leerArchivo(R.raw.vertex_shader, contexto));
        glCompileShader(vertexShader);
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, statusCompilation,0);
        if (statusCompilation[0] == 0){
            glDeleteShader(vertexShader);
            Log.w("Vertex ERROR", "Se produjo un error al compilar el sombreador de vertice");
            Log.w("Codigo: ", sourceV.toString());
            return;
        }

        //Fragment shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        //glShaderSource(fragmentShader, String.valueOf(sourceF));
        glShaderSource(fragmentShader, Funciones20.leerArchivo(R.raw.fragment_shader, contexto));
        glCompileShader(fragmentShader);
        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, statusCompilation,0);
        if (statusCompilation[0] == 0){
            glDeleteShader(fragmentShader);
            Log.w("Fragment ERROR", "Se produjo un error al compilar el sombreador de fragmento");
            Log.w("Codigo: ", sourceF.toString());
            return;
        }

        idPrograma = glCreateProgram();
        glAttachShader(idPrograma, vertexShader);
        glAttachShader(idPrograma, fragmentShader);
        glLinkProgram(idPrograma);
        int[] statusVinculation = new int[1];
        glGetProgramiv(idPrograma, GL_LINK_STATUS, statusVinculation,0);
        if(statusCompilation[0] == 0){
            glDeleteShader(idPrograma);
            Log.w("Program ERROR", "Se produjo un error al compilar el programa");
            return;
        }
    }

    public void dibujar(GL10 gl) {
        GLES20.glFrontFace(GLES20.GL_CCW);

        glUseProgram(idPrograma);

        bufferVertices.position(0);

        int idVertexShader = glGetAttribLocation(idPrograma, "idVertice");
        glVertexAttribPointer(idVertexShader, COMPVERT, GL_FLOAT, false, 0, bufferVertices);
        glEnableVertexAttribArray(idVertexShader);
        glBindAttribLocation(idPrograma, 0,"idVertice");

        int idFragmentShader = glGetUniformLocation(idPrograma, "color");
        glUniform4f(idFragmentShader, 1.0f,1.0f,0.0f,1.0f);

        bufferIndices.position(0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, 18, GLES20.GL_UNSIGNED_BYTE, bufferIndices);

        bufferIndices.position(18);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, 18, GLES20.GL_UNSIGNED_BYTE, bufferIndices);


        glDisableVertexAttribArray(idVertexShader);
        GLES20.glFrontFace(gl.GL_CCW);
    }

}
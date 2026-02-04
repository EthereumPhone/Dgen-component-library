package com.example.dgenlibrary.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.example.dgenlibrary.opengl.shapes.Head
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HeadRenderer : GLSurfaceView.Renderer {

    private lateinit var head: Head

    private val vertexBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer

    // Shader code remains the same
    private val vertexShaderCode = """
        uniform mat4 uMVPMatrix;
        attribute vec4 vPosition;
        void main() {
            gl_Position = uMVPMatrix * vPosition;
        }
    """.trimIndent()

    private val fragmentShaderCode = """
        precision mediump float;
        uniform vec4 uColor;
        void main() {
            gl_FragColor = uColor;
        }
    """.trimIndent()

    private var program: Int = 0
    private var positionHandle: Int = 0
    private var mvpMatrixHandle: Int = 0
    private var colorHandle: Int = 0

    private var angle = 0f
    private val mvpMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)

    init {
        head = Head()
        vertexBuffer = ByteBuffer.allocateDirect(head.vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer().apply {
                put(head.vertices)
                position(0)
            }

        indexBuffer = ByteBuffer.allocateDirect(head.indices.size * 2)
            .order(ByteOrder.nativeOrder()).asShortBuffer().apply {
                put(head.indices)
                position(0)
            }
    }

    override fun onSurfaceCreated(gl: GL10?, p1: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        // Enable blending for transparency
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }

        // Check for linking errors
        val linkStatus = IntArray(1)
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            Log.e("GlobeRenderer", "Error linking program: ${GLES20.glGetProgramInfoLog(program)}")
            GLES20.glDeleteProgram(program)
            program = 0
        }


        // Use the program and draw the globe
        GLES20.glUseProgram(program)



        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        colorHandle = GLES20.glGetUniformLocation(program, "uColor")

        GLES20.glDeleteShader(vertexShader)
        GLES20.glDeleteShader(fragmentShader)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        GLES20.glUseProgram(program)

        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        // Rotate and set the matrix
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.perspectiveM(projectionMatrix, 0, 45f, 1f, 1f, 10f)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        angle += 1f
        val rotationMatrix = FloatArray(16)
        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 1f, 0f)
        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, rotationMatrix, 0)

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
        GLES20.glUniform4f(colorHandle, 0.5f, 0.8f, 0.2f, 1f) // Wireframe color

        GLES20.glDrawElements(
            GLES20.GL_LINES,
            head.indices.size,
            GLES20.GL_UNSIGNED_SHORT,
            indexBuffer
        )

        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also {
            GLES20.glShaderSource(it, shaderCode)
            GLES20.glCompileShader(it)
            val compileStatus = IntArray(1)
            GLES20.glGetShaderiv(it, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] == 0) {
                Log.e("WireframeHeadRenderer", "Error compiling shader: ${GLES20.glGetShaderInfoLog(it)}")
                GLES20.glDeleteShader(it)
            }
        }
    }
}

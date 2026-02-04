package org.ethosmobile.contacts.opengl.views


import android.content.Context
import android.graphics.PixelFormat
import android.opengl.GLSurfaceView
import com.example.dgenlibrary.opengl.renderer.HeadRenderer

class OpenGLHeadView(context: Context) : GLSurfaceView(context) {

    private val renderer: HeadRenderer

    init {

        // Set the EGL configuration to allow for transparency (RGBA)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)

        // Set the surface format to translucent (supports transparency)
        holder.setFormat(PixelFormat.TRANSLUCENT)

        // Set Z-order to be on top so that the surface can be transparent
        setZOrderOnTop(true)

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = HeadRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)

        // Optionally, set the render mode to continuously animate the sphere
        //renderMode = RENDERMODE_CONTINUOUSLY
    }


}
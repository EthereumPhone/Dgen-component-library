package com.example.dgenlibrary.opengl.shapes

class Head {

    val vertices: FloatArray
    val indices: ShortArray

    init {
        vertices = floatArrayOf(
            // X, Y, Z coordinates
            0f, 1f, 0f,  // Top vertex
            0.5f, 0.5f, 0f,  // Mid-upper-right
            -0.5f, 0.5f, 0f,  // Mid-upper-left
            0.5f, -0.5f, 0f,  // Mid-lower-right
            -0.5f, -0.5f, 0f,  // Mid-lower-left
            0f, -1f, 0f,  // Bottom vertex
            0f, 0f, 0.5f,  // Front-center
            0f, 0f, -0.5f  // Back-center
        )

        indices = shortArrayOf(
            // Connecting the top vertex
            0, 1,  // Top to upper-right
            0, 2,  // Top to upper-left

            // Connecting the bottom vertex
            5, 3,  // Bottom to lower-right
            5, 4,  // Bottom to lower-left

            // Connecting the middle vertices
            1, 3,  // Upper-right to lower-right
            2, 4,  // Upper-left to lower-left
            1, 2,  // Upper-right to upper-left
            3, 4,  // Lower-right to lower-left

            // Front-back connectors
            6, 1,  // Front-center to upper-right
            6, 2,  // Front-center to upper-left
            6, 3,  // Front-center to lower-right
            6, 4,  // Front-center to lower-left
            7, 1,  // Back-center to upper-right
            7, 2,  // Back-center to upper-left
            7, 3,  // Back-center to lower-right
            7, 4   // Back-center to lower-left
        )


    }
}

package com.example.nous.service

import android.net.Uri
import kotlinx.coroutines.delay
import java.util.UUID

class VideoUploadService {

    // Simulates video upload by returning a mock URL or local path
    suspend fun uploadVideo(uri: Uri): String {
        // Simulate a delay to mimic upload time
        delay(2000)

        // Return a local file path or mock URL
        return "file://${uri.path}"
    }
}
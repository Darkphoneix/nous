
package com.example.nous.service

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class VideoUploadService @Inject constructor() {
    private val storage = FirebaseStorage.getInstance()
    private val videosRef = storage.reference.child("videos")

    suspend fun uploadVideo(uri: Uri): String {
        val filename = "${UUID.randomUUID()}.mp4"
        val videoRef = videosRef.child(filename)

        return try {
            videoRef.putFile(uri).await()
            videoRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception("Failed to upload video: ${e.message}")
        }
    }
}
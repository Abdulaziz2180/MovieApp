package com.example.movieapp2.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.FileProvider
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.UUID

@Composable
fun rememberImagePicker(
    onImageCropped: (Uri) -> Unit
): ImagePicker {
    val context = androidx.compose.ui.platform.LocalContext.current
    val tempFile = remember { File(context.cacheDir, "temp_photo_${UUID.randomUUID()}.jpg") }
    val croppedFile = remember { File(context.cacheDir, "cropped_photo_${UUID.randomUUID()}.jpg") }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val outputUri = UCrop.getOutput(result.data!!)
            outputUri?.let { onImageCropped(it) }
        }
    }

    fun startCrop(sourceUri: Uri, destinationFile: File) {
        val destinationUri = Uri.fromFile(destinationFile)
        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(512, 512)
        cropLauncher.launch(uCrop.getIntent(context))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { startCrop(it, croppedFile) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempFile
            )
            startCrop(uri, croppedFile)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    return ImagePicker(
        pickFromGallery = { galleryLauncher.launch("image/*") },
        pickFromCamera = {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempFile
            )
            cameraLauncher.launch(uri)
        },
        requestPermissions = { permissions ->
            permissionLauncher.launch(permissions)
        }
    )
}

data class ImagePicker(
    val pickFromGallery: () -> Unit,
    val pickFromCamera: () -> Unit,
    val requestPermissions: (Array<String>) -> Unit
)
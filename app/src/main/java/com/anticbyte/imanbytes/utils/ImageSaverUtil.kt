package com.anticbyte.imanbytes.utils

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun saveBitmapToPng(context: Context, fileName: String, bitmap: Bitmap) {
    val cacheDir = File(context.cacheDir, "images").apply {
        if (!this.exists())
            this.mkdirs()
    }


    val imageFile = File(cacheDir, "verse_image.png")

    runCatching {
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // PNG compression at 100% quality
        }

        val imageURI = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileProvider",
            imageFile
        )


        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            // This flag grants permission to the app the user selects.
            // The Chooser itself gets temporary permission to show the preview.

            ClipData.newUri(context.contentResolver, "Image", imageURI).apply {
                clipData = this
            }


            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, imageURI)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))



        println("Image saved to: ${imageFile.absolutePath}")
    }.onFailure {
        // Handle the error (e.g., show a toast to the user)
        println("Error saving image: ${it.message}")

    }
}



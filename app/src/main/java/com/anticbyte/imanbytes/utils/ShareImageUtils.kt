package com.anticbyte.imanbytes.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.FileProvider
import com.anticbyte.imanbytes.domain.model.Quran
import java.io.File
import java.io.FileOutputStream

@Composable
fun rememberSnackBarHostState(): SnackbarHostState {
    return remember { SnackbarHostState() }
}

fun shareText(context: Context, verseData: Quran, shareTitle: String) {
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
        putExtra(Intent.EXTRA_TEXT, "${verseData.verseTitle}\n${verseData.verse}")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, shareTitle))
}

fun shareImage(context: Context, imageBitmap: Bitmap, shareTitle: String) {
    runCatching {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val file = File(cachePath, "verse_image.png")
        FileOutputStream(file).use { out ->
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        val imageURI = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileProvider",
            file
        )
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageURI)
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, shareTitle))
    }.onFailure {
        Toast.makeText(context, "Error sharing image: ${it.message}", Toast.LENGTH_SHORT).show()
    }
}
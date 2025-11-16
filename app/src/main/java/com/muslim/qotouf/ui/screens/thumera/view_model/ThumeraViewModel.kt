package com.muslim.qotouf.ui.screens.thumera.view_model

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.muslim.qotouf.enum.QuranSurah
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ThumeraViewModel @Inject constructor(
    @ApplicationContext val context: Context
): ViewModel() {
    fun saveBitmapToGallery(bitmap: Bitmap, chapter: Int, verse: Int ): Uri? {
        val surah = QuranSurah.getSurahName(chapter)
        val fileName = "سورة ${surah}_${verse}}.png"
        val resolver = context.contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Qotouf")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: return null

        resolver.openOutputStream(uri).use { outputStream ->
            if (outputStream == null) return null
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)

        return uri
    }
    fun shareBitmapDirectly(bitmap: Bitmap) {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")

        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(
            Intent.createChooser(intent, "مشاركة الصورة").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

}

class ScreenshotController {
    internal var requestCapture: (() -> Unit)? = null
    internal var onBitmapReady: ((Bitmap) -> Unit)? = null

    fun capture(onResult: (Bitmap) -> Unit) {
        onBitmapReady = onResult
        requestCapture?.invoke()
    }
}
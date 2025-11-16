package com.muslim.qotouf.ui.screens.thumera.view_model

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.muslim.qotouf.enum.QuranSurah
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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

}

class ScreenshotController {
    internal var requestCapture: (() -> Unit)? = null
    internal var onBitmapReady: ((Bitmap) -> Unit)? = null

    fun capture(onResult: (Bitmap) -> Unit) {
        onBitmapReady = onResult
        requestCapture?.invoke()
    }
}
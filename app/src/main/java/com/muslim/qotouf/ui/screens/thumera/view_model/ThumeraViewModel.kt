package com.muslim.qotouf.ui.screens.thumera.view_model

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.utils.quran_parser.getWholeQuranVerses
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ThumeraViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _versesList = MutableStateFlow<List<Verse>>(emptyList())
    val versesList = _versesList.asStateFlow()
    private var allVerses: List<Verse> = emptyList()
    private val _isPreviousEnabled = MutableStateFlow(true)
    val isPreviousEnabledState = _isPreviousEnabled.asStateFlow()
    private val _isNextEnabled = MutableStateFlow(true)
    val isNextEnabledState = _isNextEnabled.asStateFlow()

    init {
        allVerses = getWholeQuranVerses(context)
    }

    fun saveBitmapToGallery(bitmap: Bitmap, chapter: Int, verse: Int): Uri? {
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

    fun initAyah(ayah: Verse) {
        _versesList.value = listOf(ayah)
        updateFlags()

    }

    fun onPreviousAyah(currentAyah: Verse) {
        val currentList = _versesList.value

        if (totalTextLength(currentList) > 800) {
            _isPreviousEnabled.value = false
            _isNextEnabled.value = false
            return
        }

        val prevText = getPreviousAyah(getCurrentIndex(currentAyah), currentAyah.chapter)

        if (prevText.isEmpty()) {
            _isPreviousEnabled.value = false
            return
        }

        val prevVerse = allVerses[getCurrentIndex(currentAyah) - 1]
        val updated = currentList.toMutableList()
        updated.add(0, prevVerse)
        _versesList.value = updated

        updateFlags()
    }

    fun onNextAyah(ayah: Verse) {

        val currentList = _versesList.value

        if (totalTextLength(currentList) > 800) {
            _isPreviousEnabled.value = false
            _isNextEnabled.value = false
            return
        }

        val nextText = getNextAyah(getCurrentIndex(ayah), ayah.chapter)

        if (nextText.isEmpty()) {
            _isNextEnabled.value = false
            return
        }

        val nextVerse = allVerses[getCurrentIndex(ayah) + 1]
        val updated = currentList.toMutableList()
        updated.add(nextVerse)
        _versesList.value = updated

        updateFlags()
    }

    private fun updateFlags() {
        val currentList = _versesList.value
        val firstIndex = getCurrentIndex(currentList.first())
        val lastIndex = getCurrentIndex(currentList.last())
        _isPreviousEnabled.value =
            firstIndex > 0 && allVerses[firstIndex - 1].chapter == currentList.first().chapter
        _isNextEnabled.value =
            lastIndex < allVerses.size - 1 && allVerses[lastIndex + 1].chapter == currentList.last().chapter
    }


    private fun getNextAyah(currentIndex: Int, surahNumber: Int): String {
        val nextIndex = currentIndex + 1
        if (nextIndex >= allVerses.size) {
            return ""
        }
        val nextAyah = allVerses[nextIndex]
        if (nextAyah.chapter != surahNumber) {
            return ""
        }
        return nextAyah.text

    }

    private fun getPreviousAyah(currentIndex: Int, surahNumber: Int): String {
        val previousIndex = currentIndex - 1
        if (previousIndex < 0) {
            return ""
        }
        val previousAyah = allVerses[previousIndex]
        if (previousAyah.chapter != surahNumber) {
            return ""
        }
        return previousAyah.text

    }

    private fun totalTextLength(list: List<Verse>): Int {
        return list.sumOf { it.text.length }
    }

    private fun getCurrentIndex(ayah: Verse): Int {
        return allVerses.indexOfFirst {
            it.chapter == ayah.chapter && it.verse == ayah.verse
        }
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

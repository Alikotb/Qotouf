package com.muslim.qotouf.ui.screens.thumera.view_model

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.Verse
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

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    init {
        allVerses = MyApp.allVerses
    }

    fun clearMessage() {
        _message.value = null
    }

    fun saveBitmapToGallery(bitmap: Bitmap, surah: String, verse: Int): Uri? {
        val fileName = "${surah}_${verse}.png"
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
        clearMessage()

        val currentList = _versesList.value

        if (totalTextLength(currentList) > 700) {
            _isPreviousEnabled.value = false
            _isNextEnabled.value = false
            _message.value = "لا يمكن إضافة المزيد من الآيات"
            return
        }

        val currentIndex = getCurrentIndex(currentAyah)
        val previousIndex = currentIndex - 1

        if (previousIndex < 0) {
            _isPreviousEnabled.value = false
            _message.value = "هذه بداية السورة"
            return
        }

        val previousAyah = allVerses[previousIndex]

        if (previousAyah.chapter != currentAyah.chapter) {
            _isPreviousEnabled.value = false
            _message.value = "هذه بداية السورة"
            return
        }

        val updated = currentList.toMutableList()
        updated.add(0, previousAyah)
        _versesList.value = updated

        updateFlags()

        val newFirstIndex = getCurrentIndex(updated.first())
        if (newFirstIndex == 0 || allVerses[newFirstIndex - 1].chapter != updated.first().chapter) {
            _message.value = "بداية السورة"
        }
    }

    fun onNextAyah(currentAyah: Verse) {
        clearMessage()

        val currentList = _versesList.value

        if (totalTextLength(currentList) > 700) {
            _isPreviousEnabled.value = false
            _isNextEnabled.value = false
            _message.value = "لا يمكن إضافة المزيد من الآيات"
            return
        }

        val currentIndex = getCurrentIndex(currentAyah)
        val nextIndex = currentIndex + 1

        if (nextIndex >= allVerses.size) {
            _isNextEnabled.value = false
            _message.value = "نهاية المصحف"
            return
        }

        val nextAyah = allVerses[nextIndex]

        if (nextAyah.chapter != currentAyah.chapter) {
            _isNextEnabled.value = false
            _message.value = "نهاية السورة"
            return
        }

        val updated = currentList.toMutableList()
        updated.add(nextAyah)
        _versesList.value = updated

        updateFlags()

        val newLastIndex = getCurrentIndex(updated.last())
        if (newLastIndex == allVerses.size - 1 || allVerses[newLastIndex + 1].chapter != updated.last().chapter) {
            _message.value = "نهاية السورة"
        }
    }

    private fun updateFlags() {
        val currentList = _versesList.value
        if (currentList.isEmpty()) return

        val firstIndex = getCurrentIndex(currentList.first())
        val lastIndex = getCurrentIndex(currentList.last())

        _isPreviousEnabled.value = firstIndex > 0 &&
                allVerses[firstIndex - 1].chapter == currentList.first().chapter

        _isNextEnabled.value = lastIndex < allVerses.size - 1 &&
                allVerses[lastIndex + 1].chapter == currentList.last().chapter
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
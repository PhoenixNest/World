package io.module.media.ui.viewmodel

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.module.media.MediaType
import io.module.media.MediaType.*
import io.module.media.audio.AudioManager
import io.module.media.audio.model.AudioInfoModel
import io.module.media.image.ImageManager
import io.module.media.image.model.ImageInfoModel
import io.module.media.model.MediaBaseInfoModel
import io.module.media.utils.MediaPermissionDetector
import io.module.media.video.VideoManager
import io.module.media.video.model.VideoInfoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    private val _permissionFlow = MutableStateFlow<Boolean>(false)
    val permissionFlow: StateFlow<Boolean> get() = _permissionFlow

    private val imageList = mutableListOf<ImageInfoModel>()
    private val videoList = mutableListOf<VideoInfoModel>()
    private val audioList = mutableListOf<AudioInfoModel>()

    companion object {
        private const val TAG = "MediaViewModel"
    }

    fun getMediaList(type: MediaType): List<MediaBaseInfoModel> {
        return when (type) {
            IMAGE -> imageList.toList()
            VIDEO -> videoList.toList()
            AUDIO -> audioList.toList()
        }
    }

    fun checkAndRequestMediaPermission(
        activity: ComponentActivity,
        type: MediaType
    ) {
        when (type) {
            IMAGE,
            VIDEO -> MediaPermissionDetector.PERMISSION_ARRAY_ALBUM

            AUDIO -> MediaPermissionDetector.PERMISSION_ARRAY_AUDIO
        }.filter { permissionString ->
            // Filter the denied permission first.
            !MediaPermissionDetector.checkPermission(
                context = activity,
                requestPermission = permissionString
            )
        }.forEach { permissionString ->
            // Then, request the runtime permission one by one.
            MediaPermissionDetector.requestRuntimePermission(
                activity = activity,
                requestPermission = permissionString,
                permissionListener = object : MediaPermissionDetector.MediaPermissionListener {
                    override fun onPermissionGranted() {
                        _permissionFlow.tryEmit(true)
                        queryLocalPhotos()
                    }

                    override fun onPermissionDenied() {
                        _permissionFlow.tryEmit(false)
                    }
                }
            )
        }
    }

    fun queryLocalPhotos() {
        viewModelScope.launch {
            val localImageList = ImageManager
                .queryLocalPhotos(application.applicationContext)
                .filter {
                    it.contentUri != null
                }

            imageList.addAll(localImageList)
        }
    }

    fun queryLocalVideo() {
        viewModelScope.launch {
            val localVideoList = VideoManager
                .queryLocalVideo(application.applicationContext)
                .filter {
                    it.contentUri != null
                }

            videoList.addAll(localVideoList)
        }
    }

    fun queryLocalAudio() {
        viewModelScope.launch {
            val localAudioList = AudioManager
                .queryLocalAudio(application.applicationContext)
                .filter {
                    it.contentUri != null
                }

            audioList.addAll(localAudioList)
        }
    }
}
package com.example.myweather.presentation.utils

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

fun Context.getVideoUri(rawId: Int): Uri {
    val videoUri = "android.resource://$packageName/$rawId"
    return Uri.parse(videoUri)
}

fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

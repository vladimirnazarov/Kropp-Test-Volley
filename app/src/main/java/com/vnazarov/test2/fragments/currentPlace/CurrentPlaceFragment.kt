package com.vnazarov.test2.fragments.currentPlace

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.vnazarov.test2.MainActivity
import com.vnazarov.test2.R
import com.vnazarov.test2.data.*
import com.vnazarov.test2.databinding.FragmentCurrentPlaceBinding
import com.vnazarov.test2.helpers.disablePopBack
import com.vnazarov.test2.helpers.enablePopBack
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CurrentPlaceFragment : Fragment() {

    private lateinit var mBinding: FragmentCurrentPlaceBinding
    private val mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCurrentPlaceBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        mBinding.currentPlaceTitle.text = currentPlace.name
        mBinding.currentPlaceImage.load(currentPlace.photo)

        var htmlTextDecoded = currentPlace.text
        htmlTextDecoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlTextDecoded, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(htmlTextDecoded).toString()
        }

        initializeAudioPlayer()

        mBinding.currentPlaceText.text = "$htmlTextDecoded${currentPlace.creationDate}"
        (activity as MainActivity).title = currentCity.cityName
        enablePopBack(activity as MainActivity, (activity as MainActivity).mToolbar)
    }

    override fun onStop() {
        super.onStop()

        disablePopBack(activity as MainActivity)
        if (mediaPlayer.isPlaying){
            mediaPlayer.stop()
        }
    }

    private fun initializeAudioPlayer() {

        if (currentPlace.sound != "") {

            mediaPlayer.setDataSource(currentPlace.sound)
            mediaPlayer.prepare()

            maxSeconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.duration.toLong())
            maxMinutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.duration.toLong())

            maxSeconds = convertSeconds(maxSeconds, maxMinutes)
            mBinding.apPlayProgress.max = mediaPlayer.duration

            if (maxSeconds < 10){
                mBinding.apPlayTimeMax.text = "$maxMinutes:0$maxSeconds"
            } else mBinding.apPlayTimeMax.text = "$maxMinutes:$maxSeconds"

            mBinding.apPlayButton.setOnClickListener {
                if (!mediaPlayer.isPlaying) {
                    try {
                        mediaPlayer.start()
                        lifecycleScope.launch {
                            progressLoading(mediaPlayer)
                        }
                        mBinding.apPlayButton.setImageResource(R.drawable.ic_pause)
                    } catch (e: Exception) {
                        Log.e("Audio Stream Error", e.message.toString())
                    }
                } else {
                    try {
                        mediaPlayer.pause()
                        mBinding.apPlayButton.setImageResource(R.drawable.ic_play_arrow)
                    } catch (e: Exception) {
                        Log.e("Audio Stream Error", e.message.toString())
                    }
                }
            }

            mBinding.apPlayProgress.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {

                    if (changed) {
                        mediaPlayer.seekTo(position)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

        } else {
            mBinding.currentPlaceAudioPlayer.visibility = View.GONE
        }
    }

    private suspend fun progressLoading(mediaPlayer: MediaPlayer) {
        while (mediaPlayer.currentPosition < mediaPlayer.duration) {
            currentSeconds = mBinding.apPlayProgress.progress / 1000
            currentMinutes = (mBinding.apPlayProgress.progress / 1000) / 60

            currentSeconds = convertSeconds(currentSeconds.toLong(), currentMinutes.toLong()).toInt()

            if (currentSeconds < 10){
                mBinding.apCurrentPlayTime.text = "$currentMinutes:0$currentSeconds"
            } else mBinding.apCurrentPlayTime.text = "$currentMinutes:$currentSeconds"

            mBinding.apPlayProgress.progress = mediaPlayer.currentPosition
            delay(1000)
        }

        mediaPlayer.setOnCompletionListener {
            mBinding.apPlayButton.setImageResource(R.drawable.ic_play_arrow)
            mBinding.apPlayProgress.progress = 0
        }
    }

    private fun convertSeconds(seconds: Long, minutes: Long): Long {
        return if (seconds > 59){
            val a = seconds - (minutes * 60)
            a
        } else seconds
    }
}
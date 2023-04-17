package com.example.videoplayer

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.ActivityWatchBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class WatchActivity : AppCompatActivity() {
    lateinit var bind: ActivityWatchBinding
    var isFullScreen = false
    var isLock = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityWatchBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val simpleExoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()

        val btFullScreen = findViewById<ImageView>(R.id.buttonFullScreen)
        val btLockScreen = findViewById<ImageView>(R.id.exo_lock)

        btFullScreen.setOnClickListener{
            if (!isFullScreen){
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit_24))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            }
            else{
                btFullScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit_24))
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            isFullScreen = !isFullScreen
        }
        btLockScreen.setOnClickListener {
            if (!isLock){
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock_24))
            }
            else
            {
                btLockScreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_lock_open_24))

            }
            isLock = !isLock
            lockScreen(isLock)
            
        }


        bind.player.player = simpleExoPlayer
        bind.player.keepScreenOn = true
        simpleExoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    bind.exoProgressBar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY){
                    bind.exoProgressBar.visibility = View.GONE
                }
            }
        })
        val videoSource = Uri.parse("https:/www.rmp-streaming.com/media/big-buck-bunny-360p.mp4")
        val mediaItem = MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }

    private fun lockScreen(lock: Boolean) {
        val sec_mid = findViewById<LinearLayout>(R.id.sec_controlvid1)
        val sec_bot = findViewById<LinearLayout>(R.id.sec_contolvid2)
        if (lock) {
            sec_mid.visibility = View.INVISIBLE
            sec_bot.visibility = View.INVISIBLE
        }
        else {
            sec_mid.visibility = View.VISIBLE
            sec_bot.visibility = View.VISIBLE
        }

    }
}
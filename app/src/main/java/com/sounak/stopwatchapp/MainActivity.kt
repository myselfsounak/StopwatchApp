package com.sounak.stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var tvmilliTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var timeElapsed: Long = 0
    private var isRunning: Boolean = false
    private val handler = Handler()

    // Runnable to update the timer
    private val runnable: Runnable by lazy {
        Runnable {
            if (isRunning) {
                timeElapsed += 10 // Increase timeElapsed by 10 milliseconds
                updateTimerText()
                handler.postDelayed(runnable, 10) // Repeat every 10 milliseconds
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        tvmilliTimer = findViewById(R.id.tvmilliTimer)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        // Start button click listener
        btnStart.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                btnStart.alpha = 0.5f
                btnPause.alpha= 1f
                btnReset.alpha= 1f
                handler.post(runnable) // Start the timer
            }
        }

        // Pause button click listener
        btnPause.setOnClickListener {
            isRunning = false // Pause the timer
            btnStart.alpha = 1f
            btnPause.alpha= 0.5f
            btnReset.alpha= 1f
        }

        // Reset button click listener
        btnReset.setOnClickListener {
            isRunning = false // Pause the timer
            timeElapsed = 0 // Reset timeElapsed to 0
            updateTimerText() // Update the display to show 00:00:00.000
            btnStart.alpha = 1f
            btnPause.alpha= 0.5f
            btnReset.alpha= 0.5f
        }
    }

    private fun updateTimerText() {
        val hours = (timeElapsed / 3600000)
        val minutes = (timeElapsed % 3600000) / 60000
        val seconds = (timeElapsed % 60000) / 1000
        val milliseconds = (timeElapsed % 1000)

        // Manually formatting the time string
        val formattedHours = if (hours < 10) "0$hours" else hours.toString()
        val formattedMinutes = if (minutes < 10) "0$minutes" else minutes.toString()
        val formattedSeconds = if (seconds < 10) "0$seconds" else seconds.toString()
        val formattedMilliseconds = if (milliseconds < 100) {
            if (milliseconds < 10) "00$milliseconds" else "0$milliseconds"
        } else {
            milliseconds.toString()
        }

        // Concatenating to form the final time string
        val timeString = "$formattedHours:$formattedMinutes:$formattedSeconds"
        tvTimer.text = timeString // Update the timer display
        tvmilliTimer.text = formattedMilliseconds

    }
}

package com.example.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private var currentHold: Int = 0

    // Constants for zones
    private val blueZoneMax = 3
    private val greenZoneMax = 6
    private val redZoneMax = 9
    private val maxScore = 18
    private val minScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore saved state if available
        savedInstanceState?.let {
            score = it.getInt("score", 0)
            currentHold = it.getInt("currentHold", 0)
        }

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val climbButton: Button = findViewById(R.id.climbButton)
        val fallButton: Button = findViewById(R.id.fallButton)
        val resetButton: Button = findViewById(R.id.resetButton)

        updateScoreDisplay(scoreTextView)

        climbButton.setOnClickListener {
            climb(scoreTextView)
        }

        fallButton.setOnClickListener {
            fall(scoreTextView)
        }

        resetButton.setOnClickListener {
            reset(scoreTextView)
        }
    }

    private fun climb(scoreTextView: TextView) {
        if (currentHold < redZoneMax) {
            currentHold++
            score += when {
                currentHold <= blueZoneMax -> 1
                currentHold <= greenZoneMax -> 2
                else -> 3
            }
            score = score.coerceAtMost(maxScore) // Ensure score doesn't exceed max
            updateScoreDisplay(scoreTextView)
        }
    }

    private fun fall(scoreTextView: TextView) {
        if (currentHold >= 1 && currentHold < redZoneMax) {
            score = (score - 3).coerceAtLeast(minScore) // Ensure score doesn't drop below 0
            updateScoreDisplay(scoreTextView)
        }
    }

    private fun reset(scoreTextView: TextView) {
        score = 0
        currentHold = 0
        updateScoreDisplay(scoreTextView)
    }

    private fun updateScoreDisplay(scoreTextView: TextView) {
        scoreTextView.text = score.toString()

        // Xác định màu vùng dựa trên vị trí Hold
        val backgroundColor = when {
            currentHold == 0 -> android.R.color.transparent // Không áp dụng màu ở Hold 0
            currentHold in 1..blueZoneMax -> R.color.blueZone
            currentHold in (blueZoneMax + 1)..greenZoneMax -> R.color.greenZone
            else -> R.color.redZone
        }
        scoreTextView.setBackgroundColor(resources.getColor(backgroundColor, theme))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("currentHold", currentHold)
    }
}

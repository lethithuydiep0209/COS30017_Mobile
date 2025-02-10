package com.example.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private var currentHold: Int = 0

    // xac dinh gioi han cua tung vung mau
    private val blueZoneMax = 3
    private val greenZoneMax = 6
    private val redZoneMax = 9
    private val maxScore = 18
    private val minScore = 0
        //khoi tao man hinh chinh khi ung dung chay
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khoi phuc trang thai ( khi xoay man hinh
        savedInstanceState?.let {
            score = it.getInt("score", 0)
            currentHold = it.getInt("currentHold", 0)
        }
        //lay view tu layout
        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val climbButton: Button = findViewById(R.id.climbButton)
        val fallButton: Button = findViewById(R.id.fallButton)
        val resetButton: Button = findViewById(R.id.resetButton)
        //xu li su kien nut bam
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
    //xu li khi nguoi choi leo len
    private fun climb(scoreTextView: TextView) {
        if (currentHold < redZoneMax) { //chi leo len khi chua dat hold 9
            currentHold++
            score += when {
                currentHold <= blueZoneMax -> 1
                currentHold <= greenZoneMax -> 2
                else -> 3
            }
            score = score.coerceAtMost(maxScore) // gioi han toi da
            updateScoreDisplay(scoreTextView)
        }
    }
//xu li khi nguoi choi roi
    private fun fall(scoreTextView: TextView) {
        if (currentHold >= 1 && currentHold < redZoneMax) { //chi cho phep roi khi da len hold 1
            score = (score - 3).coerceAtLeast(minScore) //giam 3 diem (khong duoi 0)
            updateScoreDisplay(scoreTextView)
        }
    }
//reset ung dung (ve trang thai ban dau (hold0, score0)
    private fun reset(scoreTextView: TextView) {
        score = 0
        currentHold = 0
        updateScoreDisplay(scoreTextView)
    }
//update mau nen theo vung
    private fun updateScoreDisplay(scoreTextView: TextView) {
        scoreTextView.text = score.toString()

        // Xác định màu vùng dựa trên vị trí Hold
        val backgroundColor = when {
            currentHold == 0 -> android.R.color.transparent // nen trong suot, k mau
            currentHold in 1..blueZoneMax -> R.color.blueZone
            currentHold in (blueZoneMax + 1)..greenZoneMax -> R.color.greenZone
            else -> R.color.redZone
        }
        scoreTextView.setBackgroundColor(resources.getColor(backgroundColor, theme))
    }
// giup luu du lieu truoc khi ung dung bi tat hoac xoay man hinh
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", score)
        outState.putInt("currentHold", currentHold)
    }
}

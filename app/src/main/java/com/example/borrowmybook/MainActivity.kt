package com.example.borrowmybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    lateinit var borrowCard : CardView
    lateinit var returnCard : CardView
    lateinit var  trackerCard : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        var loader : ProgressBar = findViewById(R.id.loader)

        borrowCard = findViewById(R.id.borrowCard)
        returnCard = findViewById(R.id.returnCard)
        trackerCard = findViewById(R.id.trackerCard)



        borrowCard.setOnClickListener{
            loader.visibility=View.VISIBLE
            val intentBorrow = Intent(this, BorrowActivity::class.java)
            startActivity(intentBorrow)
            loader.visibility=View.INVISIBLE
        }

        returnCard.setOnClickListener{
            loader.visibility=View.VISIBLE
            val intentReturn= Intent(this, ReturnActivity::class.java)
            startActivity(intentReturn)
            loader.visibility=View.INVISIBLE
        }

        trackerCard.setOnClickListener{
            loader.visibility=View.VISIBLE
            val intentTracker= Intent(this, TrackerActivity::class.java)
            startActivity(intentTracker)
            loader.visibility=View.INVISIBLE
        }
    }
}
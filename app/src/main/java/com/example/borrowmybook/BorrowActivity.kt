package com.example.borrowmybook

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class BorrowActivity : AppCompatActivity() {
    private val repo: BookRepository by lazy{
        BookRepository(this)
    }
    var validText = true

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrow)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val loader : ProgressBar = findViewById(R.id.loader)

        val bookNameHelper : TextView = findViewById(R.id.bookNameHelper)
        val authorHelper : TextView = findViewById(R.id.authorHelper)
        val borrowNameHelper :TextView = findViewById(R.id.borrowNameHelper)
        val dateTvHelper : TextView = findViewById(R.id.dateTvHelper)

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        val submit : Button  = findViewById(R.id.submitBtn)
        val reset : Button  = findViewById(R.id.resetBtn)

        val bookName : TextInputEditText = findViewById(R.id.bookName)
        val author : TextInputEditText = findViewById(R.id.author)
        val borrowName : TextInputEditText = findViewById(R.id.borrowName)
        val dateTv : TextInputEditText = findViewById(R.id.dateTv)

       bookName.setOnFocusChangeListener { view, b ->
           if(!textValidator(bookName.text.toString()))
           {
               bookName.text = null
               bookNameHelper.text = "Alphabets only"
               bookNameHelper.visibility = View.VISIBLE
           }else
           if(textValidator(bookName.text.toString()))
           {
               bookNameHelper.visibility = View.INVISIBLE
           }
       }

        author.setOnFocusChangeListener { view, b ->
            if(!textValidator(author.text.toString()))
            {
                author.text = null
                authorHelper.text = "Alphabets only"
                authorHelper.visibility = View.VISIBLE
            }else
            if(textValidator(author.text.toString()))
            {
                authorHelper.visibility = View.INVISIBLE
            }
        }

        borrowName.setOnFocusChangeListener { view, b ->
            if(!textValidator(borrowName.text.toString()))
            {
                borrowName.text = null
                borrowNameHelper.text = "Alphabets only"
                borrowNameHelper.visibility = View.VISIBLE
            }else
            if(textValidator(borrowName.text.toString()))
            {
                borrowNameHelper.visibility = View.INVISIBLE
                dateTvHelper.visibility = View.INVISIBLE
            }
        }

        fun validator():Boolean{
            validText = true

            if(bookName.length()==0){
                bookNameHelper.text = "Required"
                bookNameHelper.visibility = View.VISIBLE
                validText = false
            }
            if(author.length()==0){
                authorHelper.text = "Required"
                authorHelper.visibility = View.VISIBLE
                validText= false
            }
            if(borrowName.length()==0){
                borrowNameHelper.text = "Required"
                borrowNameHelper.visibility = View.VISIBLE
                validText=false
            }
            if(dateTv.length()==0){
                dateTvHelper.text = "Required"
                dateTvHelper.visibility = View.VISIBLE
                validText=false
            }
            return validText
        }

        floatingBtn.setOnClickListener{
            loader.visibility=View.VISIBLE
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            loader.visibility=View.INVISIBLE
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }

        dateTv.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { datePicker: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                dateTv.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear) },year, month,day)
            dpd.datePicker.maxDate = System.currentTimeMillis()+ (1000 * 60 * 60*24)
            dpd.show()
            dateTv.setTextColor(resources.getColor(R.color.black))
            dateTv.setTypeface(null,Typeface.BOLD)
        }

        submit.setOnClickListener {
            validator()
            if(!validText){
                Toast.makeText(applicationContext,"Required fields not filled out",Toast.LENGTH_LONG).show()
            }

            if(validText) {
                repo.insertBook(Books (null,bookName.text.toString(),author.text.toString(),
                    borrowName.text.toString(),dateTv.text.toString(),null))
                loader.visibility=View.VISIBLE
                Toast.makeText(applicationContext,"Entry recorded",Toast.LENGTH_LONG).show()
                submit.isEnabled=false
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                loader.visibility=View.INVISIBLE
            }
        }

        reset.setOnClickListener {
            bookNameHelper.visibility = View.INVISIBLE
            authorHelper.visibility = View.INVISIBLE
            borrowNameHelper.visibility = View.INVISIBLE
            dateTvHelper.visibility = View.INVISIBLE
            bookName.text = null
            author.text = null
            borrowName.text = null
            dateTv.text=null
            Toast.makeText(this,"All fields have been reset",Toast.LENGTH_LONG).show()
        }
    }

    private fun textValidator(tempText:String):Boolean{
        for (c in tempText)
        {
            if (c !in 'A'..'Z' && c !in 'a'..'z'&& c != ' ') {
                return false
            }
        }
        return true
    }
}
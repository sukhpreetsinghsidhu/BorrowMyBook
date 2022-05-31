package com.example.borrowmybook

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class ReturnForm : AppCompatActivity() {
    val repo : BookRepository by lazy {
        BookRepository(this)
    }

    var validDate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_form)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val loader : ProgressBar = findViewById(R.id.loader)

        val bookName : TextView = findViewById(R.id.bookName_R)
        val author : TextView = findViewById(R.id.author_R)
        val dateTvReturn: TextInputEditText = findViewById(R.id.dateTvReturn_R)

        val returnedDateHelper : TextView = findViewById(R.id.returnedDateHelper_F)

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        val resetBtn : Button = findViewById(R.id.resetBtn_R)
        val returnBtn : Button = findViewById(R.id.returnBtn_R)
        val backBtn : Button = findViewById(R.id.backBtn_R)

        val id : Int = intent.getIntExtra("id",0)
        val borrowName : String = intent.getStringExtra("borrowName").toString()
        val dateTv : String = intent.getStringExtra("dateTv").toString()
        bookName.text = intent.getStringExtra("bookName")
        author.text = intent.getStringExtra("author")
        dateTvReturn.setText(intent.getStringExtra("dateTvReturn"))

        fun dateChecker(): Boolean{
            validDate = true
            if (dateTvReturn.text.toString()==""){
                returnedDateHelper.text = "Required"
                returnedDateHelper.visibility = View.VISIBLE
                validDate = false
            }
            else
            if ( dateTvReturn.text.toString()!=null && dateTvReturn.text.toString()!="") {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val fd: Date = sdf.parse(dateTv.toString())
                val sd: Date = sdf.parse(dateTvReturn.text.toString())

                if (fd.after(sd)) {
                    dateTvReturn.text = null
                    returnedDateHelper.text = "Returned date cannot be before borrowed date"
                    returnedDateHelper.visibility = View.VISIBLE
                    validDate = false
                }
            }
            return validDate
        }

        floatingBtn.setOnClickListener{
            loader.visibility=View.VISIBLE
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            loader.visibility=View.INVISIBLE
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }

        dateTvReturn.setOnClickListener {
            returnedDateHelper.visibility = View.INVISIBLE
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { datePicker: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                dateTvReturn.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear) },year, month,day)
            dpd.datePicker.maxDate = System.currentTimeMillis()+ (1000 * 60 * 60*24)
            dpd.show()
            dateTvReturn.setTextColor(resources.getColor(R.color.black))
            dateTvReturn.setTypeface(null, Typeface.BOLD)
        }

        returnBtn.setOnClickListener{
            dateChecker()
            if (validDate){
                repo.updateBook(
                    Books(id.toString().toInt(), bookName.text.toString(),
                        author.text.toString(), borrowName.toString(), dateTv.toString(),
                        dateTvReturn.text.toString()))
                loader.visibility=View.VISIBLE
                returnBtn.isEnabled=false
                Toast.makeText(applicationContext, "Book Returned", Toast.LENGTH_LONG).show()
                val myIntent = Intent(this, MainActivity::class.java)
                startActivity(myIntent)
                loader.visibility=View.INVISIBLE
            }
        }

        resetBtn.setOnClickListener{
            bookName.text = intent.getStringExtra("bookName")
            author.text = intent.getStringExtra("author")
            dateTvReturn.text = null
            returnedDateHelper.visibility = View.INVISIBLE
            Toast.makeText(applicationContext,"Form Reset", Toast.LENGTH_LONG).show()
        }

        backBtn.setOnClickListener {
            loader.visibility=View.VISIBLE
            val myIntent = Intent(this, ReturnActivity::class.java)
            startActivity(myIntent)
            loader.visibility=View.INVISIBLE
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }
    }
}
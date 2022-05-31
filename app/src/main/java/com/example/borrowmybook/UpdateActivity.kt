package com.example.borrowmybook

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*


class UpdateActivity : AppCompatActivity() {
    private val repo : BookRepository by lazy {
        BookRepository(this)
    }
    var validText = true
    var validDate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN



        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val loader : ProgressBar = findViewById(R.id.loader)

        val bookNameHelper : TextView = findViewById(R.id.bookNameHelper_U)
        val authorHelper : TextView = findViewById(R.id.authorHelper_U)
        val borrowNameHelper : TextView = findViewById(R.id.borrowNameHelper_U)
        val returnedDateHelper : TextView = findViewById(R.id.returnedDateHelper_U)

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        val update : Button = findViewById(R.id.updateBtn)
        val back : Button = findViewById(R.id.backBtn)
        val cancel: Button = findViewById(R.id.cancelBtn)
        val deleteBtn : Button = findViewById(R.id.deleteBtn)
        val bnrBtn : Button = findViewById(R.id.bnrBtn)

        val dateTvReturn : TextInputEditText = findViewById(R.id.dateTvReturn_U)
        val dateTv: TextInputEditText = findViewById(R.id.dateTv_U)
        val bookName : TextInputEditText = findViewById(R.id.bookName_U)
        val author : TextInputEditText = findViewById(R.id.author_U)
        val borrowName : TextInputEditText = findViewById(R.id.borrowName_U)

        val id : Int = intent.getIntExtra("id",0)
        bookName.setText(intent.getStringExtra("bookName"))
        author.setText(intent.getStringExtra("author"))
        borrowName.setText(intent.getStringExtra("borrowName"))
        dateTv.setText(intent.getStringExtra("dateTv"))
        dateTvReturn.setText(intent.getStringExtra("dateTvReturn"))

        if(dateTvReturn.length()==0){
            bnrBtn.isEnabled=false
        }

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
                }
        }

        fun validator():Boolean{
            validText= true

            if(bookName.length()==0){
                bookNameHelper.setText("Required")
                bookNameHelper.setVisibility(View.VISIBLE)
                validText = false
            }
            if(author.length()==0){
                authorHelper.setText("Required")
                authorHelper.setVisibility(View.VISIBLE)
                validText= false
            }
            if(borrowName.length()==0){
                borrowNameHelper.setText("Required")
                borrowNameHelper.setVisibility(View.VISIBLE)
                validText=false
            }
            return validText
        }

        floatingBtn.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }

        dateTv.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { datePicker: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                dateTv.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear) },year, month,day)
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis()+ (1000 * 60 * 60*24))
            dpd.show()
            dateTv.setTextColor(resources.getColor(R.color.black))
            dateTv.setTypeface(null, Typeface.BOLD)
        }

        dateTvReturn.setOnClickListener {
            returnedDateHelper.setVisibility(View.INVISIBLE)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
            { datePicker: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                dateTvReturn.setText(""+mDay+"/"+(mMonth+1)+"/"+mYear) },year, month,day)
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis()+ (1000 * 60 * 60*24))
            dpd.show()
            dateTvReturn.setTextColor(resources.getColor(R.color.black))
            dateTvReturn.setTypeface(null,Typeface.BOLD)
        }

        fun dateChecker(): Boolean{
            validDate = true
            if ( dateTvReturn.text.toString()!=null && dateTvReturn.text.toString()!="") {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val fd: Date = sdf.parse(dateTv.text.toString())
                val sd: Date = sdf.parse(dateTvReturn.text.toString())

                if (fd.after(sd)) {
                    dateTvReturn.setText(null)
                    returnedDateHelper.text = "Returned date cannot be before borrowed date"
                    returnedDateHelper.visibility = View.VISIBLE
                    validDate = false
                }
            }
            return validDate
        }

        update.setOnClickListener{
            validator()
            dateChecker()

            if(!validText){
                Toast.makeText(applicationContext,"Required fields not filled out",Toast.LENGTH_LONG).show()
            }

            if(validText && validDate) {
                repo.updateBook(Books(id.toString().toInt(), bookName.text.toString(),
                        author.text.toString(), borrowName.text.toString(), dateTv.text.toString(),
                        dateTvReturn.text.toString()))
                loader.visibility=View.VISIBLE
                update.isEnabled=false
                Toast.makeText(applicationContext, "Updating Records.", Toast.LENGTH_LONG).show()
                val myIntent = Intent(this, TrackerActivity::class.java)
                startActivity(myIntent)
                loader.visibility=View.INVISIBLE
            }
        }

        fun cancel(){
            bookName.setText(intent.getStringExtra("bookName"))
            author.setText(intent.getStringExtra("author"))
            borrowName.setText(intent.getStringExtra("borrowName"))
            dateTv.setText(intent.getStringExtra("dateTv"))
            dateTvReturn.setText(intent.getStringExtra("dateTvReturn"))
            authorHelper.visibility = View.INVISIBLE
            bookNameHelper.visibility = View.INVISIBLE
            borrowNameHelper.visibility = View.INVISIBLE
            returnedDateHelper.visibility = View.INVISIBLE
        }

        cancel.setOnClickListener {
            cancel()
            Toast.makeText(applicationContext,"Form Reset", Toast.LENGTH_LONG).show()
        }
        fun delete(){
            repo.deleteBook(Books (id.toString().toInt(),bookName.text.toString(),
                author.text.toString(),borrowName.text.toString(),dateTv.text.toString(),
                dateTvReturn.text.toString()))
            loader.visibility=View.VISIBLE
            deleteBtn.isEnabled=false
            Toast.makeText(applicationContext,"Record Deleted",Toast.LENGTH_LONG).show()
            val intentTracker= Intent(this, TrackerActivity::class.java)
            startActivity(intentTracker)
            loader.visibility=View.INVISIBLE
        }

        deleteBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Please confirm")
            builder.setMessage("Delete record forever?")
            builder.setPositiveButton("Yes") { DialogInterface: DialogInterface, i: Int -> delete() }
            builder.setNegativeButton("No") { DialogInterface: DialogInterface, i: Int ->
                cancel()
                Toast.makeText(applicationContext, "Delete cancelled", Toast.LENGTH_LONG).show()
            }
            builder.show()
        }

        bnrBtn.setOnClickListener {
            repo.updateBook(
                Books(
                    id.toString().toInt(), bookName.text.toString(),
                    author.text.toString(), borrowName.text.toString(), dateTv.text.toString(),
                    null
                )
            )
            dateTvReturn.setText(null)
            bnrBtn.isEnabled = false
            Toast.makeText(applicationContext, "Updating Records", Toast.LENGTH_LONG).show()
        }

        back.setOnClickListener {
            loader.visibility=View.VISIBLE
            val myIntent = Intent(this, TrackerActivity::class.java)
            startActivity(myIntent)
            loader.visibility=View.INVISIBLE
            Toast.makeText(applicationContext,"Redirecting to book tracker", Toast.LENGTH_LONG).show()
        }
    }

    fun textValidator(tempText:String):Boolean{
        for (c in tempText)
        {
            if (c !in 'A'..'Z' && c !in 'a'..'z'&& c != ' ') {
                return false
            }
        }
        return true
    }
}
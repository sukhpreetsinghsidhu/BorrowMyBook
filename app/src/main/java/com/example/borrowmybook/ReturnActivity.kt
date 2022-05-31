package com.example.borrowmybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReturnActivity : AppCompatActivity() {

    lateinit var booklist : List<Books>
    val repo: BookRepository by lazy{
        BookRepository(this)
    }

    lateinit var loader : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        loader=  findViewById(R.id.loader)

        val sortSpinner : Spinner = findViewById(R.id.sortSpinner)
        val sortArray = arrayOf("Sort Date Ascending","Sort Date Decending")
        var sortCondtion: String

        val aa = ArrayAdapter(this,android.R.layout.simple_spinner_item,sortArray)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner!!.setAdapter(aa)

        val searchTxt : EditText = findViewById(R.id.searchTxt_R)

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        val sortBtn : Button = findViewById(R.id.sortBtn)

        val emptyView : TextView = findViewById(R.id.empty_view)

        booklist = repo.searchRD()

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BookAdapter({ position -> onCardClick(position) },booklist)

        recyclerView.adapter = adapter

        if (adapter.itemCount==0) {
            recyclerView.setVisibility(View.GONE)
            emptyView.setVisibility(View.VISIBLE)
        }
        else {
            recyclerView.setVisibility(View.VISIBLE)
            emptyView.setVisibility(View.GONE)
        }

        floatingBtn.setOnClickListener{
            loader.visibility=View.VISIBLE
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            loader.visibility=View.INVISIBLE
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }

        searchTxt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                booklist= repo.searchRD2(searchTxt.text.toString())
                val adapter_New = BookAdapter({ position -> onCardClick(position) }, booklist)
                recyclerView.adapter = adapter_New

                if(adapter_New.itemCount==0){
                    recyclerView.setVisibility(View.GONE)
                    emptyView.setVisibility(View.VISIBLE)
                    emptyView.setText("No records found for ${searchTxt.text.toString()}")
                }
            }
        })

        sortBtn.setOnClickListener{
            sortCondtion =sortSpinner.selectedItem.toString()
            if (sortCondtion == "Sort Date Ascending"){
                booklist= repo.sortRDAsc()
                val adapter_Asc = BookAdapter({ position -> onCardClick(position) }, booklist)
                recyclerView.adapter = adapter_Asc

                if(adapter_Asc.itemCount==0){
                    recyclerView.setVisibility(View.GONE)
                    emptyView.setVisibility(View.VISIBLE)
                    emptyView.setText("No records found for ${searchTxt.text.toString()}")
                }
            }
            else
                if (sortCondtion == "Sort Date Decending"){
                    booklist= repo.sortRDDsc()
                    val adapter_Dsc = BookAdapter({ position -> onCardClick(position) }, booklist)
                    recyclerView.adapter = adapter_Dsc

                    if(adapter_Dsc.itemCount==0){
                        recyclerView.setVisibility(View.GONE)
                        emptyView.setVisibility(View.VISIBLE)
                        emptyView.setText("No records found for ${searchTxt.text.toString()}")
                    }
                }
        }
    }

    fun onCardClick(position: Int){
        loader.visibility=View.VISIBLE
        val myIntent = Intent(this, ReturnForm::class.java)
        myIntent.putExtra("id", booklist[position].bookId)
        myIntent.putExtra("bookName", booklist[position].bookName)
        myIntent.putExtra("author", booklist[position].author)
        myIntent.putExtra("borrowName", booklist[position].borrowerName)
        myIntent.putExtra("dateTv", booklist[position].date)
        myIntent.putExtra("dateTvReturn", booklist[position].returnDate)
        startActivity(myIntent)
        loader.visibility=View.INVISIBLE
    }
}
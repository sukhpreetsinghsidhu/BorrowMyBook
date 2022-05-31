package com.example.borrowmybook

import android.content.Context

class BookRepository(context : Context) {
    var db : BookDao? = AppDatabase.getInstance(context)?.bookDao()!!

    fun getAllBooks(): List<Books> {
        return db?.selectBook()?: listOf<Books>()
    }

    fun insertBook(books: Books){
        db?.insertBook(books)
    }

    fun updateBook(books: Books){
        db?.updateBook(books)
    }

    fun deleteBook(books: Books){
        db?.deleteBook(books)
    }

    fun search(name: String): List<Books>{
        return db?.search("%$name%")?: listOf<Books>()
    }

    fun searchRD():List<Books>{
        return db?.searchRD()?: listOf<Books>()
    }

    fun searchRD2(name: String):List<Books>{
        return db?.searchRD2("%$name%")?: listOf<Books>()
    }

    fun sortRDAsc():List<Books>{
        return db?.sortRDAsc()?: listOf<Books>()
    }

    fun sortRDDsc():List<Books>{
        return db?.sortRDDsc()?: listOf<Books>()
    }

    fun getAllBooksAsc(): List<Books> {
        return db?.selectBookAsc()?: listOf<Books>()
    }

    fun getAllBooksDsc(): List<Books> {
        return db?.selectBookDsc()?: listOf<Books>()
    }
}
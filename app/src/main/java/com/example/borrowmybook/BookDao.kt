package com.example.borrowmybook

import androidx.room.*

@Dao
interface BookDao {
    @Insert
    fun insertBook(book : Books)

    @Query("select * from books")
    fun selectBook():List<Books>

    @Update
    fun updateBook(book:Books)

    @Delete
    fun deleteBook(book : Books)

    @Query("select * from books where bookName like :searchQuery or author like :searchQuery or borrowerName like :searchQuery")
    fun search(searchQuery : String):List<Books>

    @Query("select * from books where returnDate is null")
    fun searchRD():List<Books>

    @Query("select * from books where returnDate is null and bookName like :searchQuery or author like :searchQuery or borrowerName like :searchQuery")
    fun searchRD2(searchQuery : String):List<Books>

    @Query("select * from books where returnDate is null order by cast (date as unsigned) ASC ")
    fun sortRDAsc():List<Books>

    @Query("select * from books where returnDate is null order by cast (date as unsigned) DESC ")
    fun sortRDDsc():List<Books>

    @Query("select * from books order by cast (date as unsigned) ASC ")
    fun selectBookAsc():List<Books>

    @Query("select * from books order by cast (date as unsigned) DESC ")
    fun selectBookDsc():List<Books>
}
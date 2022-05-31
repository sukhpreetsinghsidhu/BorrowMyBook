package com.example.borrowmybook

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Books(@PrimaryKey(autoGenerate = true)var bookId:Int?,
                @ColumnInfo(name="bookName")var bookName: String?,
                @ColumnInfo(name="author")var author:String?,
                @ColumnInfo(name="borrowerName")var borrowerName: String?,
                @ColumnInfo(name="date")var date: String?,
                @ColumnInfo(name="returnDate")var returnDate: String?){
}
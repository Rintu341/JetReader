package com.example.jetareader.model

import com.example.jetareader.R
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName

//FireBase Best Practices ->
//every time we have field with more then one words then words are should be separated by '_'


data class MBook(
    @Exclude
    var id:String? = null,
    var title:String? = null,
    var authors:String? = null,
    var notes:String? = null,
    @get:PropertyName("photo_url")
    @set:PropertyName("photo_url")
    var photoUrl:String? = null,
    var categories:String? = null,
    @get:PropertyName("published_date")
    @set:PropertyName("published_date")
    var publishedDate:String? = null,
    var rating:Double? = null,
    var description:String? = null,
    @get:PropertyName("page_count")
    @set:PropertyName("page_count")
    var pageCount:Int? = null,
    @get:PropertyName("user_id")
    @set:PropertyName("user_id")
    var userId:String? = null,
    @get:PropertyName("start_reading")
    @set:PropertyName("start_reading")
    var startReading: Timestamp? = null,
    @get:PropertyName("finished_reading")
    @set:PropertyName("finished_reading")
    var finishedReading: Timestamp? = null,
    @get:PropertyName("google_book_id")
    @set:PropertyName("google_book_id")
    var googleBookId:String? = null
)

fun getListOfBook():List<MBook>
{
    val books = listOf<MBook>(
        MBook(
            id = "1",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = "",
            publishedDate = "1997",
            categories = "Business"
        ),
        MBook(
            id = "2",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = "",
            publishedDate = "1997",
            categories = "Business"
        ),
        MBook(
            id = "3",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = "",
            publishedDate = "1997",
            categories = "Business"
        ),
        MBook(
            id = "4",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = "",
            publishedDate = "1997",
            categories = "Business"
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = "",
            publishedDate = "1997",
            categories = "Business"
        )
    )
    return books
}

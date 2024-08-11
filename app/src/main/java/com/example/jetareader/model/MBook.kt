package com.example.jetareader.model

import com.example.jetareader.R

data class MBook(
    var id:String? = null,
    var title:String? = null,
    var authors:String? = null,
    var notes:String? = null,
    var photoUrl:Int? = null,
    var categories:List<String>? = null,
    var publishedDate:String? = null,
)

fun getListOfBook():List<MBook>
{
    val books = listOf<MBook>(
        MBook(
            id = "1",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "2",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "3",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "4",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        ),
        MBook(
            id = "5",
            title = "Rich Dad Poor Dad",
            authors = "Robert Kiyosaki and Sharon L. Lechter",
            notes = "Note1",
            photoUrl = R.drawable.richdadpoordad,
            publishedDate = "1997",
            categories = listOf("Personal Finance", "Business")
        )
    )
    return books
}

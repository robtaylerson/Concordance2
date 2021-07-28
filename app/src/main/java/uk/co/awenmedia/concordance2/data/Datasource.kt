package uk.co.awenmedia.concordance2.data

import uk.co.awenmedia.concordance2.R
import uk.co.awenmedia.concordance2.model.Book

class Datasource {

    fun loadBooks() : List<Book> {
        return listOf<Book>(
            Book(R.string.book1),
            Book(R.string.book2),
            Book(R.string.book3),
            Book(R.string.book4),
            Book(R.string.book5),
            Book(R.string.book6),
            Book(R.string.book7),
            Book(R.string.book8),
            Book(R.string.book9),
        )
    }
}
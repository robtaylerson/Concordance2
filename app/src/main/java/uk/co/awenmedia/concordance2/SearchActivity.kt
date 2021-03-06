package uk.co.awenmedia.concordance2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity


class SearchActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        actionBar = getSupportActionBar()
        actionBar!!.setTitle("Bible Search")
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        var spinner: Spinner = findViewById(R.id.books_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.books_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        return true
    }
}
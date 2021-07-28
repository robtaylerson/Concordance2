package uk.co.awenmedia.concordance2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.awenmedia.concordance2.adapter.ItemAdapter
import uk.co.awenmedia.concordance2.data.Datasource

class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDataset = Datasource().loadBooks()
        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this,myDataset)
        recyclerView.setHasFixedSize(true)
        actionBar = getSupportActionBar()
        actionBar!!.setTitle("Bible Companion")
        actionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_menu, menu) // Associate searchable configuration with the SearchView
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, SearchActivity::class.java)
        this.startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}
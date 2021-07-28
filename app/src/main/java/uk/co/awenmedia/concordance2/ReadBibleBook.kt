package uk.co.awenmedia.concordance2



import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.cardview.widget.CardView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.co.awenmedia.concordance2.databinding.ActivityReadBibleBookBinding
import uk.co.awenmedia.concordance2.model.BibleBook
import java.io.IOException


class ReadBibleBook : AppCompatActivity() {

    var bookName : String = ""
    var bookText : String = ""
    var bookFilename : String = ""
    var currentChapterIndex = 1
    var chapterNumbers : MutableList<Int> = ArrayList()

    private lateinit var binding: ActivityReadBibleBookBinding
    private lateinit var bibleBook: BibleBook
    private var actionBar: ActionBar? = null
    private lateinit var optionsMenu: Menu
    private lateinit var chapter_layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBibleBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        chapter_layout = findViewById(R.id.outer_linear_layout)
        bookName = intent?.extras?.getString("bookName").toString()
        bookFilename = intent?.extras?.getString("bookFilename").toString()
        loadBook(bookFilename)
        actionBar = getSupportActionBar()
        actionBar!!.setTitle(bookName)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        updateChapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.chapter_menu, menu)
        for (i in chapterNumbers) {
            val menuItem : MenuItem  = menu.add(Menu.NONE,i,1,"Chapter " + i)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title != null) {
            currentChapterIndex = item.itemId
            updateChapter()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadBook(filename : String) {
            // read json, format book text and save to new file
            val jsonString = getJsonDataFromAsset(this,filename);
            val gson = Gson()
            val bibleBookType = object : TypeToken<BibleBook>() {}.type
            bibleBook = gson.fromJson(jsonString,bibleBookType)


            for (i in bibleBook.chapters) {
                chapterNumbers.add((i.chapter).toInt())
            }
            this.invalidateOptionsMenu()
    }

    fun updateChapter() {
        //update chapter
        val chapter = bibleBook.chapters[currentChapterIndex - 1]

        //get handle to outer layout
        val layout : LinearLayout = findViewById(R.id.outer_linear_layout)

        //clear existing verses
        layout.removeAllViews()

        //update content
        findViewById<TextView>(R.id.chapter_title).setText(Html.fromHtml("<b>Chapter: " + chapter.chapter + "</b><br>"))
        for (verse in chapter.verses) {
            var verseText = ""
            verseText += "Verse " + verse.verse + ":<br>"
            verseText += verse.text
            val card = createVerseView(verseText,(verse.verse).toInt())
            layout.addView(card)
        }
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun createVerseView(verse_text : String, verse_number: Int): CardView {
        var cv = CardView(ContextThemeWrapper(this, R.style.CardViewStyle), null, 0)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val margin: Int = 8
        params.setMargins(0, margin, margin, margin)
        cv.setLayoutParams(params)

        val cardInner =
            LinearLayout(ContextThemeWrapper(this, R.style.Widget_CardContent))

        //add verse text view
        val tv = TextView(this)
        tv.setText(Html.fromHtml(verse_text))
        cardInner.addView(tv)

        //add favourite button
        val btn  = Button(this)
        btn.setText("add to favourite")
        btn.setOnClickListener {
            Log.d("RTA-BIBLE","my favourite verse " + verse_number)
        }
        cardInner.addView(btn)
        cv.addView(cardInner)
        return cv
    }
}
package uk.co.awenmedia.concordance2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.co.awenmedia.concordance2.R
import uk.co.awenmedia.concordance2.ReadBibleBook
import uk.co.awenmedia.concordance2.model.Book

class ItemAdapter(
    private val context : Context,
    private val dataset: List<Book>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.book_title)
        val imageButton: ImageButton = view.findViewById(R.id.go_to_chapters_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_layout, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val bookName = context.resources.getString(item.stringResourceId)
        holder.textView.text = bookName
        holder.imageButton.setOnClickListener {
            val context = holder.view.context
            val intent = Intent(context, ReadBibleBook::class.java)
            intent.putExtra("bookName", bookName)
            intent.putExtra("bookFilename", bookName.replace(" ","",true) + ".json")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
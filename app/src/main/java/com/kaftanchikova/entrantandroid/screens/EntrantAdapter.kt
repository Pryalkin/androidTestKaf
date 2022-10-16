package com.kaftanchikova.entrantandroid.screens

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaftanchikova.entrantandroid.R
import com.kaftanchikova.entrantandroid.databinding.ItemEntrantBinding
import com.kaftanchikova.entrantandroid.model.Entrant

interface EntrantActionListener {
    fun onEntrantDelete(entrant: Entrant)
    fun onEntrantDetails(entrant: Entrant)
    fun onEntrantEnroll(entrant: Entrant)
}

class EntrantDiffCallback(
    private val oldList: List<Entrant>,
    private val newList: List<Entrant>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntrant = oldList[oldItemPosition]
        val newEntrant = newList[newItemPosition]
        return oldEntrant.id == newEntrant.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEntrant = oldList[oldItemPosition]
        val newEntrant = newList[newItemPosition]
        return oldEntrant == newEntrant
    }
}

class EntrantAdapter(
    private val actionListener: EntrantActionListener
) : RecyclerView.Adapter<EntrantAdapter.EntrantViewHolder>(), View.OnClickListener {

    var entrants: List<Entrant> = emptyList()
        set(newValue) {
            val diffCallback = EntrantDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEntrantBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return EntrantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntrantViewHolder, position: Int) {
        val entrant = entrants[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = entrant
            moreImageViewButton.tag = entrant
            userNameTextView.text = entrant.name
            userEnrollTextView.text = if (entrant.status) context.getString(R.string.enroll) else context.getString(R.string.do_not_enroll)
            Glide.with(photoImageView.context)
                .load(R.drawable.ic_user)
                .into(photoImageView)
        }
    }

    override fun getItemCount(): Int = entrants.size

    class EntrantViewHolder(val binding: ItemEntrantBinding): RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val entrant = v.tag as Entrant
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onEntrantDetails(entrant)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val entrant = v.tag as Entrant
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))
        if (!entrant.status) {
            popupMenu.menu.add(0, ID_ENROLL, Menu.NONE, context.getString(R.string.enroll))
        }
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ID_REMOVE -> {
                    actionListener.onEntrantDelete(entrant)
                }
                ID_ENROLL -> {
                    actionListener.onEntrantEnroll(entrant)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ID_REMOVE = 1
        private const val ID_ENROLL = 2
    }

}
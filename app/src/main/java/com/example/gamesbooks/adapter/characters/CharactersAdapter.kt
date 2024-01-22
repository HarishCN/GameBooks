package com.example.gamesbooks.adapter.characters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesbooks.data.local.entity.CharactersEntity
import com.example.gamesbooks.databinding.ItemCharBinding

class CharactersAdapter() :
    RecyclerView.Adapter<CharactersAdapter.CharListViewHolder>() {

    private val charactersList = mutableListOf<CharactersEntity>()

    fun setCharacters(characters: List<CharactersEntity>?) {
        characters?.let {
            val startRange = if (charactersList.isNotEmpty()) charactersList.size - 1 else 0
            charactersList.addAll(characters)
            notifyItemRangeChanged(startRange, characters.size)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharListViewHolder {
        val binding =
            ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharListViewHolder, position: Int) {
        holder.bind(charactersList[position], position)

    }

    override fun getItemCount(): Int = charactersList.size

    class CharListViewHolder(
        private val binding: ItemCharBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharactersEntity, position: Int) {
            binding.apply {

                with(character) {
                    setupTextView(charName, binding.txtName)
                    setupTextView(charGender, binding.txtGender)
                    setupTextView(titles.getOrNull(0), binding.txtTitle)
                    setupTextView(alias.getOrNull(0), binding.txtAlias)
                }
            }
        }

        private fun setupTextView(value: String?, textView: TextView) {
            if (!value.isNullOrBlank()) {
                textView.visibility = View.VISIBLE
                textView.text = value
            } else {
                textView.visibility = View.GONE
            }
        }
    }
}

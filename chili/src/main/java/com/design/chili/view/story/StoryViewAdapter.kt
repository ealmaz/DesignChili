package com.design.chili.view.story

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.design.chili.R

class StoryViewAdapter(private val listener: OnStoryClickListener): RecyclerView.Adapter<StoryViewAdapter.StoryHighlightsVH>() {
    private val stories = ArrayList<Story>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHighlightsVH {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.chili_item_story, parent, false)
        return StoryHighlightsVH(view)
    }

    override fun onBindViewHolder(holder: StoryHighlightsVH, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    fun addItems(items: List<Story>) {
        this.stories.addAll(items)
        this.stories.add(Story(8, "Все события", true, StoryType.ALL_STORIES, null))
        notifyDataSetChanged()
    }


    inner class StoryHighlightsVH(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var imageView: ImageView
        private lateinit var textView: TextView
        private lateinit var whiteBorderView: CardView
        private lateinit var colorfulBorderView: FrameLayout

        fun bind(item: Story) {
            imageView = itemView.findViewById(R.id.image)
            textView = itemView.findViewById(R.id.title)
            whiteBorderView = itemView.findViewById(R.id.white_border_frame)
            colorfulBorderView = itemView.findViewById(R.id.border_frame)

            if (item.type == StoryType.ALL_STORIES)
                setAllStoriesCell()
            else
                setupStory(item)

            setupClickListener()
        }

        private fun setupClickListener() {
            whiteBorderView.setOnClickListener {
                if (adapterPosition == 7)
                    listener.onAllStoriesClick()
                else
                    listener.onStoryClick(stories.take(7), adapterPosition)
            }
        }

        private fun setupStory(item: Story) {
            loadImage(item.imageUri)
            textView.text = item.description
            setStoryBorder(item)
            setStoryRightIcon(item)
        }

        private fun loadImage(imageUri: String?) {
            Glide.with(itemView)
                .load(imageUri)
                .centerCrop()
                .into(imageView)
        }

        private fun setStoryBorder(story: Story) {
            if (story.isRead == true) {
                colorfulBorderView.setBackgroundResource(0)
                whiteBorderView.setBackgroundColor(Color.TRANSPARENT)
            }
            else setBorderByType(story.type)
        }

        private fun setBorderByType(type: StoryType?) {
            val res = when (type) {
                StoryType.ALGA -> R.drawable.gradiented_border_alga
                StoryType.FOR_YOU -> R.drawable.chili_for_you_border
                StoryType.GENERAL -> R.drawable.gradiented_border
                else -> 0
            }
            colorfulBorderView.setBackgroundResource(res)
        }

        private fun setStoryRightIcon(story: Story) {
            val rightCornerIcon = itemView.findViewById<ImageView>(R.id.right_corner_icon)
            rightCornerIcon.isVisible = story.type == StoryType.FOR_YOU
        }

        private fun setAllStoriesCell() {
            itemView.findViewById<ImageView>(R.id.all_stories_icon).isVisible = true
            imageView.isVisible = false
            textView.setTextColor(ContextCompat.getColor(itemView.context, R.color.black_1))
            setStoryBorder(stories[7])
            textView.text = stories[7].description
        }
    }
}

interface OnStoryClickListener {
    fun onStoryClick(stories: List<Story>, index: Int)
    fun onAllStoriesClick()
}
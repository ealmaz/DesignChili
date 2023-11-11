package com.design.chili.view.story.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.design.chili.R
import com.design.chili.view.story.Story
import com.design.chili.view.story.StoryCallbackListener
import com.design.chili.view.story.StoryType

class DetailedStoryFragment(private val clickListener: StoryCallbackListener) : Fragment() {
    private var story: Story? = null
    private lateinit var storyView: StoryView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_story, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyView = view.findViewById(R.id.story_view)

        story?.stories?.let { storyView.setStoriesList(it) }
        storyView.setOnCallbackListener(clickListener)

        setupViewByType(story?.type)
    }

    private fun setupViewByType(type: StoryType?) {
        if (type == StoryType.FOR_YOU) {
            storyView.showBadge()
            storyView.showBorder()
        }
    }

    companion object {
        fun newInstance(story: Story, clickListener: StoryCallbackListener): DetailedStoryFragment {
            val fragment = DetailedStoryFragment(clickListener)
            fragment.story = story
            return fragment
        }
    }
}
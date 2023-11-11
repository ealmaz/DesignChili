package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.design.app.MainActivity
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentStoryPlayerBinding
import com.design.chili.view.story.Story
import com.design.chili.view.story.StoryCallbackListener
import com.design.chili.view.story.detail.DetailedStoriesPagerAdapter

class StoryPlayerFragment : BaseFragment<FragmentStoryPlayerBinding>(), StoryCallbackListener {

    private lateinit var stories: List<Story>
    private var index: Int = 0

    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).hideToolbar()
        viewPager = view.findViewById(R.id.viewPager)
        initPager()
    }

    private fun initPager() {
        val adapter = DetailedStoriesPagerAdapter(requireActivity(), stories, this)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(index, false)
    }

    override fun inflateViewBinging(): FragmentStoryPlayerBinding {
        return FragmentStoryPlayerBinding.inflate(layoutInflater)
    }

    override fun onStoriesEnd() {
        if (viewPager.currentItem + 1 < stories.size)
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        else onCancelClick()
    }

    override fun tapToPreviousStory() {
        if (viewPager.currentItem > 0)
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    override fun onSwipeDown() {
        (requireActivity() as MainActivity).supportFragmentManager.popBackStack()
    }

    override fun onCancelClick() {
        (requireActivity() as MainActivity).supportFragmentManager.popBackStack()
    }

    override fun onDetailButtonClick() {
        Toast.makeText(requireActivity(), "Переход по диплинку", Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(stories: List<Story>, index: Int): StoryPlayerFragment {
            val fragment = StoryPlayerFragment()
            fragment.stories = stories
            fragment.index = index
            return fragment
        }
    }
}
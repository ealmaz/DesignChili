package com.design.chili.view.story.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.design.chili.view.story.Story
import com.design.chili.view.story.StoryCallbackListener

class DetailedStoriesPagerAdapter(fragmentActivity: FragmentActivity, private val storiesList: List<Story>, private val clickListener: StoryCallbackListener) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return storiesList.size
    }

    override fun createFragment(position: Int): Fragment {
        return DetailedStoryFragment.newInstance(storiesList[position], clickListener)
    }
}

package com.design.chili.view.story

interface StoryCallbackListener {
    fun onCancelClick()
    fun onDetailButtonClick()
    fun onSwipeDown()
    fun onStoriesEnd()
    fun tapToPreviousStory()
}
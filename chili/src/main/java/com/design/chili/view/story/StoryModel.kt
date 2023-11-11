package com.design.chili.view.story

data class Story(
    val id: Int? = null,
    val description: String? = null,
    val isRead: Boolean? = null,
    val type: StoryType? = null,
    val imageUri: String? = null,
    val stories: ArrayList<DetailedStory>? = null
)

enum class StoryType {
    GENERAL,
    ALGA,
    FOR_YOU,
    ALL_STORIES
}

data class DetailedStory(
    val imageUri: String?,
    val title: String? = "",
    val subtitle: String? = "",
    val details: String? = ""
)
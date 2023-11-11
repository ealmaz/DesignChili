package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentStoriesBinding
import com.design.chili.view.story.DetailedStory
import com.design.chili.view.story.OnStoryClickListener
import com.design.chili.view.story.Story
import com.design.chili.view.story.StoryType
import com.design.chili.view.story.StoryViewAdapter

class StoriesFragment : BaseFragment<FragmentStoriesBinding>(), OnStoryClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)

        val uris = ArrayList<DetailedStory>()
        uris.add(
            DetailedStory(
                "https://picsum.photos/200/300",
                "\uD83D\uDD25 Для абонентов действует выгодное предложение!",
                "Мы рекомендуем отталкиваться от своих потребностей. Следуйте своим предпочтениям в карусели\uD83D\uDC46",
                "Мы рекомендуем отталкиваться от своих потребностей. Следуйте своим предпочтениям в карусели\uD83D\uDC46\n" +
                        "\n" +
                        "Не забывайте и о наших специальных тарифах для:\n" +
                        "✅ обучения,\n" +
                        "✅ семьи,\n" +
                        "✅ умных устройств,\n" +
                        "✅ роутеров и модемов,\n" +
                        "✅ регионов.\n" +
                        "\n" +
                        "Подключите на 4 недели всего за 495 сомов вместо 750 сомов! \uD83D\uDCA5\n" +
                        "\uD83D\uDCA1Самое приятное – продлевать действие этого тарифа по сниженной стоимости можно ещё 2 раза."
            )
        )
        uris.add(
            DetailedStory("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__340.jpg")
        )
        uris.add(
            DetailedStory("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCv_Gx6Fde6mja_lLmll0fzrxRvcKLHGrPxnqMrQLWKqXi9IYy&usqp=CAU")
        )
        val uris2 = ArrayList<DetailedStory>()
        uris2.add(
            DetailedStory("https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__340.jpg")
        )
        uris2.add(
            DetailedStory(
                "https://picsum.photos/200/300",
                "\uD83D\uDD25 Для абонентов действует выгодное предложение!",
                "Мы рекомендуем отталкиваться от своих потребностей. Следуйте своим предпочтениям в карусели\uD83D\uDC46",
                "Мы рекомендуем отталкиваться от своих потребностей. Следуйте своим предпочтениям в карусели\uD83D\uDC46\n" +
                        "\n" +
                        "Не забывайте и о наших специальных тарифах для:\n" +
                        "✅ обучения,\n" +
                        "✅ семьи,\n" +
                        "✅ умных устройств,\n" +
                        "✅ роутеров и модемов,\n" +
                        "✅ регионов.\n" +
                        "\n" +
                        "Подключите на 4 недели всего за 495 сомов вместо 750 сомов! \uD83D\uDCA5\n" +
                        "\uD83D\uDCA1Самое приятное – продлевать действие этого тарифа по сниженной стоимости можно ещё 2 раза."
            )
        )
        val stories = listOf(
            Story(1, "Получи 10 ГБ", false, StoryType.ALGA, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCv_Gx6Fde6mja_lLmll0fzrxRvcKLHGrPxnqMrQLWKqXi9IYy&usqp=CAU", uris),
            Story(2, "Переходи на О!", false, StoryType.GENERAL, "https://picsum.photos/200/300", uris2),
            Story(3, "Билеты на Open Air", true, StoryType.GENERAL, "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__340.jpg", uris),
            Story(4, "Билеты на Open Air", false, StoryType.FOR_YOU, "https://picsum.photos/200/300", uris2),
            Story(5, "Билеты на Open Air", false, StoryType.FOR_YOU, "https://picsum.photos/200/300", uris),
            Story(6, "Билеты на Open Air", false, StoryType.GENERAL, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCv_Gx6Fde6mja_lLmll0fzrxRvcKLHGrPxnqMrQLWKqXi9IYy&usqp=CAU\"", uris2),
            Story(7, "Билеты на Open Air", true, StoryType.GENERAL, "https://picsum.photos/200/300", uris)
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vb.storyRecyclerView.layoutManager = layoutManager
        val adapter = StoryViewAdapter(this)
        adapter.addItems(stories)
        vb.storyRecyclerView.adapter = adapter
    }

    override fun inflateViewBinging(): FragmentStoriesBinding {
        return FragmentStoriesBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showToolbar()
    }


    override fun onStoryClick(stories: List<Story>, index: Int) {
        openFragment(StoryPlayerFragment.newInstance(stories, index))
    }

    override fun onAllStoriesClick() {
        Toast.makeText(requireActivity(), "Переход на экран событий!", Toast.LENGTH_SHORT).show()
    }
}
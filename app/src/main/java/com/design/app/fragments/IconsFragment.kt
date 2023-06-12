package com.design.app.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentIconsBinding
import com.design.chili.R
import com.design.chili.util.IconSize
import com.design.chili.util.ItemDecorator
import com.design.chili.view.cells.MultiIconedAdapter

class IconsFragment: BaseFragment<FragmentIconsBinding>() {

    val icons = arrayListOf("https://minio.o.kg/lkab/joy/services/music/yandex_radio.png","https://minio.o.kg/lkab/joy/services/music/sberzvuk.png","https://minio.o.kg/lkab/joy/services/music/applemusic.png","https://minio.o.kg/lkab/joy/services/music/spotify.png","https://minio.o.kg/lkab/joy/services/music/yandex.png")

    override fun inflateViewBinging(): FragmentIconsBinding {
        return FragmentIconsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setIcons(icons)
    }

    private fun setIcons(icons: ArrayList<String>?) = with(vb) {
        if (icons.isNullOrEmpty()) {
            rvIcons.visibility = View.GONE
        } else {
            val adapter = MultiIconedAdapter(IconSize.LARGE)
            rvIcons.visibility = View.VISIBLE
            rvIcons.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvIcons.addItemDecoration(ItemDecorator(requireContext().resources.getDimension(R.dimen.padding_desc_4dp).toInt()))
            rvIcons.adapter = adapter
            adapter.addIcons(icons)
        }
    }
}
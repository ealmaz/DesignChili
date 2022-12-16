package com.design.chili.model

data class Option<T>(
    val type: T,
    val title: String? = "",
    val subTitle: String? = "",
    val description: String? = "",
    val isInfoBtnVisible: Boolean? = false,
    val icons: ArrayList<String>? = null,
    val color: String? = null,
    val isActive: Boolean = false,
    val isUnavailable: Boolean = false
)
package com.design.chili.util

enum class IconType {
    PLUS, CHEVRON
}

enum class IconStatus {
    SELECTED, ACTIVE, UNSELECTED, UNAVAILABLE
}

enum class IconSize(val value: Int) {
    SMALL(-1), MEDIUM(-2), LARGE(-3)
}

enum class RoundedCornerMode(var value: Int) {
    SINGLE(0), TOP(1), MIDDLE(2), BOTTOM(3),WITHOUT_ROUNDS(4)
}
package com.gmail.maystruks08.hikingfood

class ToolbarDescriptor private constructor(
    val visible: Boolean = false,
    val title: String? = null,
    val navigationIcon: Int? = null,
    val collapse: Boolean = false,
    val color: Int? = null
) {

    class Builder(
        private var visible: Boolean = false,
        private var title: String? = null,
        private var navigationIcon: Int? = null,
        private var collapse: Boolean = false,
        private var color: Int = R.color.colorPrimary
    ) {

        fun visibility(visible: Boolean) = apply {
            this.visible = visible
            if (!visible) {
                this.collapse = true
                this.navigationIcon = null
                this.title = null
            }
        }

        fun title(title: String?) = apply {
            this.title = title
        }

        fun navigationIcon(navigationIcon: Int?) = apply {
            this.navigationIcon = navigationIcon
        }

        fun collapse(collapse: Boolean) = apply {
            this.collapse = collapse
        }

        fun color(color: Int) = apply {
            this.color = color
        }

        fun build() = ToolbarDescriptor(
            visible,
            title,
            navigationIcon,
            collapse,
            color
        )
    }
}
package com.anticbyte.imanbytes.navigation

import androidx.annotation.DrawableRes
import com.anticbyte.imanbytes.R

enum class NavigationBarItem (
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val itemLabel: String,
    val navRoute: Any? = null
) {
    HOME(
        unselectedIcon = R.drawable.ic_nav_home,
        selectedIcon = R.drawable.ic_nav_home_fill,
        itemLabel = "Home",
        navRoute = HomeBaseRoute
    ),
    KNOWLEDGE(
        unselectedIcon = R.drawable.ic_nav_knowledge,
        selectedIcon = R.drawable.ic_nav_knowledge_fill,
        itemLabel = "Knowledge",
        navRoute = KnowledgeBaseRoute
    ),
    PROFILE(
        unselectedIcon = R.drawable.ic_nav_profile,
        selectedIcon = R.drawable.ic_nav_profile_fill,
        itemLabel = "Profile",
        navRoute = ProfileBaseRoute
    )
}
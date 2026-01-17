package com.anticbyte.imanbytes.navigation

import androidx.annotation.DrawableRes
import com.anticbyte.imanbytes.R

enum class NavigationBarItem(
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val itemLabel: String,
    val navRoute: Any
) {
    HOME(
        unselectedIcon = R.drawable.ic_nav_home,
        selectedIcon = R.drawable.ic_nav_home_fill,
        itemLabel = "Home",
        navRoute = HomeBaseRoute
    ),
    QURAN(
        unselectedIcon = R.drawable.ic_nav_knowledge,
        selectedIcon = R.drawable.ic_nav_knowledge_fill,
        itemLabel = "Quran",
        navRoute = RecitationBaseRoute
    ),
    KNOWLEDGE(
        unselectedIcon = R.drawable.ic_bulb,
        selectedIcon = R.drawable.ic_bulb_fill,
        itemLabel = "Knowledge",
        navRoute = KnowledgeBaseRoute
    )
    // TODO: Add this in the next update.
/*    PROFILE(
        unselectedIcon = R.drawable.ic_nav_profile,
        selectedIcon = R.drawable.ic_nav_profile_fill,
        itemLabel = "Profile",
        navRoute = PrayerTimeRamadanRoute
    )*/
}
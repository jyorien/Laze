package com.example.laze.tab_navigation

import androidx.compose.runtime.Composable
import com.example.laze.tabs.PostedTab
import com.example.laze.tabs.TakenUpTab

sealed class TabItem(var title: String, var screen: @Composable () -> Unit) {
    object Posted : TabItem("Posted", { PostedTab() })
    object TakenUp : TabItem("Taken Up", { TakenUpTab() })
}

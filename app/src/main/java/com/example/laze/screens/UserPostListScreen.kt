package com.example.laze.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.laze.tab_navigation.TabItem
import com.example.laze.tab_navigation.Tabs
import com.example.laze.tab_navigation.TabsContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun UserPostListScreen() {
    val tabs = listOf(TabItem.Posted, TabItem.TakenUp)
    val pagerState = rememberPagerState()
    Scaffold() {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
    
}
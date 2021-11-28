package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Scaffold() { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
    
}
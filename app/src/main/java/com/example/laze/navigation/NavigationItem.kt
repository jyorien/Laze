package com.example.laze.navigation

import com.example.laze.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("ViewPostScreen", R.drawable.ic_baseline_home_24, "Home")
    object NewPost : NavigationItem("AddPostScreen", R.drawable.ic_baseline_add_24, "New Post")
    object ChatList : NavigationItem("ChatListScreen", R.drawable.ic_baseline_chat_bubble_24, "Chats")
    object UserPosts : NavigationItem("UserPostListScreen", R.drawable.ic_baseline_list_24, "Posts")
}
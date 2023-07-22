package com.clavecillascc.wikinomergeco.ui

sealed class BottomBarScreen(
    val route: String,
    val title: String
) {
    object TranslateScreen : BottomBarScreen(
        route = "translatescreen",
        title = "TranslateScreen"
    )
    object LibraryScreen : BottomBarScreen(
        route = "libraryscreen",
        title = "LibraryScreen"
    )
    object FavoritesScreen : BottomBarScreen(
        route = "favoritesscreen",
        title = "FavoritesScreen"
    )
    object CollaboratorScreen : BottomBarScreen(
        route = "collaborationscreen",
        title = "CollaborationScreen"
    )
}

package com.clavecillascc.wikinomergeco

sealed class NavigationItems(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItems("home", R.drawable.translate, "Home")
    object Translate : NavigationItems("translate", R.drawable.library, "Translate")
    object Library : NavigationItems("library", R.drawable.library, "Library")
    object Collaborator : NavigationItems("collaborator", R.drawable.collaboration, "Collaborator")

}
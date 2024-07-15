package com.edwin.sekai.ui.feature.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.edwin.sekai.ui.feature.categories.CategoriesRoute

const val CATEGORIES_ROUTE = "categories_route"

fun NavController.navigateToCategories(navOptions: NavOptions? = null) =
    navigate(CATEGORIES_ROUTE, navOptions)

fun NavGraphBuilder.categoriesRoute() {
    composable(route = CATEGORIES_ROUTE) {
        CategoriesRoute()
    }
}

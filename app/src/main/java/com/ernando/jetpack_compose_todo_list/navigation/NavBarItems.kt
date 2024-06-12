package com.ernando.jetpack_compose_todo_list.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RowScope.NavBarItems(
    selectedNavItem: NavItem,
    navItems: List<NavItem>,
    onSelectedNavItem: (navItem: NavItem) -> Unit,
) {
    
    navItems.forEach { navItem ->
        NavigationBarItem(selected = selectedNavItem == navItem, onClick = {
            onSelectedNavItem(navItem)
        }, label = { Text(text = navItem.title) }, icon = {
            Icon(
                imageVector = navItem.icon, contentDescription = navItem.title
            )
        })
    }
    
}
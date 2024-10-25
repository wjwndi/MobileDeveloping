package com.example.myapplication.ui.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.myapplication.utils.DrawerEvents
import com.example.myapplication.utils.IdArrayList
import com.example.myapplication.utils.ListItem
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context, onClick: (ListItem) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val mainList = remember {
        mutableStateOf(getListItemsByIndex(0, context))
    }
    val topBarTitle = remember {
        mutableStateOf("Грибы")
    }
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu { event ->
                    when (event) {
                        is DrawerEvents.OnItemClick -> {
                            topBarTitle.value = event.title
                            mainList.value = getListItemsByIndex(
                                event.index,
                                context
                            )
                        }
                    }
                    scope.launch {
                        drawerState.close()
                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                MainTopBar(topBarTitle.value, drawerState)
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(mainList.value) { item ->
                    MainListItem(item = item){ listitem ->
                        onClick(listitem)
                    }
                }
            }
        }
    }
}


private fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
    val list = ArrayList<ListItem>()
    val arrayList = context.resources.getStringArray(IdArrayList.listId[index])
    arrayList.forEach { item ->
        val itemArray = item.split("|")
        list.add(
            ListItem(
                itemArray[0],
                itemArray[1],
                itemArray[2]
            )
        )
    }
    return list
}

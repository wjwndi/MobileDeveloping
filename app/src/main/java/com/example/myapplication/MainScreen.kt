package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.CatFactModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    model: MutableState<List<CatFactModel>>,
    viewModel: MainViewModel = viewModel(),
    onClickSync: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("I Like Cats") },
                navigationIcon = {
                    IconButton({}) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Menu",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            viewModel.onHomeClick()
                        }
                    ) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    IconButton(onClick = {
                        viewModel.onListClick()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = "List",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        val list = model.value
        when {
            viewModel.isHomeClicked.value -> Home(paddingValues, onClickSync)
            viewModel.isListClicked.value -> MainList(paddingValues, list)
        }
    }
}

@Composable
fun Home(paddingValues: PaddingValues, onClickSync: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Button(
                    onClick = {
                        onClickSync.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Text("Click to reload facts")
                }
            }
            items(10) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    Column {
                        Text(
                            text = "Fact ${index + 1}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        Text(
                            text = "F13rrfgdfg"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MainList(paddingValues: PaddingValues, list: List<CatFactModel>) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        value = "Text count of breeds",
                        onValueChange = {}
                    )
                    Button(
                        onClick = {},
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text("GET")
                    }
                }
            }
            item {
                Text(
                    text = "What are the breeds of kitties",
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }
            items(list){
                    cat ->
                CatList(cat)
            }
        }
    }
}

@Composable
fun CatList(cat: CatFactModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_svgrepo_com),
            contentDescription = "Image",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(
                text = cat.breed,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp, top = 5.dp)
            )
            Text(
                text = "From country:" + cat.country,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Coat:" + cat.coat,
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
    HorizontalDivider(
        color = Color.LightGray,
        thickness = 2.dp
    )
}
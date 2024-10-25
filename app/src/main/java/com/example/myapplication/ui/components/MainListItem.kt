package com.example.myapplication.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MainRed
import com.example.myapplication.utils.ListItem

@Composable
fun MainListItem(item: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, top = 100.dp, 20.dp)
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MainRed)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AssetImage(imageName = item.imageName, contentDescription = item.title)
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainRed)
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AssetImage(imageName: String, contentDescription: String) {
    val context = LocalContext.current
    val assetManager = context.assets
    val inputString = assetManager.open(imageName)
    val bitmap = BitmapFactory.decodeStream(inputString)
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
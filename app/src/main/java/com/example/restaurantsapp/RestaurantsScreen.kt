package com.example.restaurantsapp

import android.graphics.drawable.Icon
import android.inputmethodservice.Keyboard.Row
import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.example.restaurantsapp.ui.theme.RestaurantsAppTheme

@Composable
fun RestaurantScreen() {
    val viewModel: RestaurantsViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tambahkan judul, nama, dan NIM
        Text(
            text = "PAPB - Restaurant App",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Text(
            text = "AHMAD ZAKI",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        Text(
            text = "225150201111025",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
        ) {
            items(viewModel.state.value) { restaurant ->
                RestaurantItem(restaurant){
                    id ->
                    viewModel
                        .toggleFavorite(id)
                }
            }
        }
    }
}

@Composable
fun RestaurantItem(item: Restaurant,
                   onClick: (id: Int) -> Unit){
    val favoriteState = remember {
        mutableStateOf(false)
    }
    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)){
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.weight(0.15f)
            )
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.weight(0.7f))
            RestaurantIcon(icon, Modifier.weight(0.15f)){
                onClick(item.id)
            }

        }
    }
}

@Composable
private fun RestaurantIcon(icon: ImageVector, modifier: Modifier,
                           onClick: () -> Unit ={ }){
    Image(imageVector = icon, contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}

@Composable
private fun RestaurantDetails(title: String, description: String,
    modifier: Modifier){
    Column(modifier = modifier) {
        Text (text = title,
            style = MaterialTheme.typography.bodyMedium)
        CompositionLocalProvider(LocalContentAlpha provides
            ContentAlpha.medium){
                Text(text = description,
                    style = MaterialTheme.typography.bodyMedium)
            }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultlPreview() {
    RestaurantsAppTheme {
        RestaurantScreen()
    }
}

@Composable
private fun FavoriteIcon(
    icon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit){
    val favoriteState = remember {
        mutableStateOf(false)
    }
    val icon = if (favoriteState.value)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Image(imageVector = icon,
        contentDescription = "Favorite restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick })
}
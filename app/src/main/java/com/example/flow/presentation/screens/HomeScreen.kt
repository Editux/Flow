package com.example.flow.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.flow.domain.model.Article
import com.example.flow.domain.model.Response.*
import com.example.flow.presentation.screens.components.ProgressBar
import com.example.flow.presentation.screens.navigation.NavScreens
import com.example.flow.utils.Utils.Companion.printError

//newsList:List<Article> = getArticles(),
@Composable
fun HomeScreen (navController: NavController, viewModel: ArticleViewModel = hiltViewModel()){
    var expanded = remember { mutableStateOf(false) }
    val articleResponse = viewModel.articleState.value


    Scaffold(topBar ={
        //TopBar
        TopAppBar(backgroundColor = Color(0xfff48fb1),
            elevation = 0.dp) {
            Row(horizontalArrangement = Arrangement.Start) {
                Spacer(modifier =Modifier.width(170.dp) )
                Text(text = "Flow", fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier =Modifier.width(170.dp))
                IconButton(onClick = {
                    expanded.value = true // 2
                }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu", tint = Color.White)
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        DropdownMenuItem(onClick = {
                            expanded.value = false
                            navController.navigate(NavScreens.CreateScreen.name)
                            // 3
                        }) {
                            Text("Create")
                        }
                    }
                }
            }
        }
    }
    ) {
        when (articleResponse) {
             is Loading -> ProgressBar()
            is Success ->LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(items = articleResponse.data) {
                    ArticleList(model = it) { model ->
                        Log.d("TAG", "Clicked on $model")
                        navController.navigate(route = NavScreens.DetailScreen.name + "/$model")

                    }


                }
            }
            is Error -> printError(articleResponse.message)
        }
    }
}
@Composable
fun ArticleList(model: Article, onItemClick:(String) -> Unit = {} ){

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 6.dp,
                end = 6.dp,
            )
            .clickable {
                onItemClick(model.id.toString())

            }
            .fillMaxWidth(),

        elevation = 8.dp

    ) {
        Column {
            Image(
               painter = rememberAsyncImagePainter(model = model.image) ,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            ) {
                Text(
                    text = model.title.toString(),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
            }
            Text(
                text = "Author : ${model.author}",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),


                style = MaterialTheme.typography.h6
            )

        }
    }

}







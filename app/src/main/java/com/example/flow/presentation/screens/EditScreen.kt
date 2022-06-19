package com.example.flow.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flow.domain.model.Article
import com.example.flow.domain.model.Response
import com.example.flow.presentation.screens.components.ProgressBar
import com.example.flow.presentation.screens.navigation.NavScreens

@Composable
fun EditScreen(navController: NavController,articleId: String?,viewModel: ArticleViewModel = hiltViewModel()) {
    val articleResponse = viewModel.articleState.value
    when (articleResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success-> ContentScreen(navController = navController, articleResponse.data,
            articleId.toString()
        )
    }

}
@Composable
private fun ContentScreen(navController: NavController, model: List<Article>,articleId:String,viewModel: ArticleViewModel = hiltViewModel()){
    val context = LocalContext.current
    val newArticle = model.firstOrNull { article->
        article.id == articleId
    }
    if (newArticle != null) {
        var title by remember { mutableStateOf(newArticle.title) }
        var author by remember { mutableStateOf(newArticle.author) }
        var category by remember { mutableStateOf(newArticle.category) }
        var content by remember { mutableStateOf(newArticle.content) }
        Scaffold(topBar = {
            //TopBar
            TopAppBar(
                backgroundColor = Color(0xfff48fb1),
                elevation = 0.dp
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                    Spacer(modifier = Modifier.width(150.dp))
                    Text(text = "Flow", fontWeight = FontWeight.Bold, color = Color.White)
                }

            }
        }
        ) {
            Column(modifier = Modifier.padding(all = 50.dp)) {
                Spacer(Modifier.height(10.dp))

                Spacer(Modifier.height(20.dp))
                TextField(
                    value = title.toString(),
                    onValueChange = {
                        title = it
                    },
                    label = { Text(text = "Your Label") },
                    placeholder = { Text(text = "Title") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedLabelColor = Color(0xFF560027),
                    )
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = author.toString(),
                    onValueChange = {
                        author = it
                    },
                    label = { Text(text = "Author") },
                    placeholder = { Text(text = "Author") },
                    colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027))

                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = category.toString(),
                    onValueChange = {
                        category = it
                    },
                    label = { Text(text = "Category") },
                    placeholder = { Text(text = "Category") },
                    colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027))
                )
                Spacer(Modifier.height(15.dp))
                TextField(
                    value = content.toString(),
                    onValueChange = {
                        content = it
                    },
                    label = { Text(text = "Content") },
                    placeholder = { Text(text = "Content") },
                    colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027))
                )
                Spacer(Modifier.height(40.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF560027),
                        contentColor = Color.White
                    ),
                    onClick = { viewModel.editArticle(articleId,
                        title.toString(),
                        author.toString(), category.toString(), content.toString()
                    )
                        Toast.makeText(context,"The article is updated", Toast.LENGTH_SHORT).show()
                        navController.navigate(NavScreens.HomeScreen.name)         },
                ) {
                    Text(text = "Save")
                }

            }
        }
    }
}
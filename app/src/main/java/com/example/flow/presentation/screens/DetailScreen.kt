package com.example.flow

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.flow.domain.model.Response.*
import com.example.flow.domain.model.Response.Loading
import com.example.flow.presentation.screens.ArticleViewModel
import com.example.flow.presentation.screens.components.ProgressBar
import com.example.flow.presentation.screens.navigation.NavScreens
import com.example.flow.utils.Utils
import androidx.compose.material.AlertDialog as AlertDialog



@Composable
fun DetailsUI(navController: NavController, articleId: String?,viewModel: ArticleViewModel = hiltViewModel()) {
 var expanded = remember { mutableStateOf(false) }
 val articleResponse = viewModel.articleState.value
 val mContext = LocalContext.current

 Scaffold(
  topBar = {
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
    Spacer(modifier =Modifier.width(170.dp))
    Text(text = "Flow", fontWeight = FontWeight.Bold, color = Color.White,)
    Spacer(modifier =Modifier.width(150.dp))
    IconButton(
     onClick = {
     expanded.value = true // 2
    }) {
     Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Menu", tint = Color.White)
     DropdownMenu(
      expanded = expanded.value,
      onDismissRequest = { expanded.value = false },
     ) {
      DropdownMenuItem(onClick = {
       expanded.value = false
       navController.navigate(NavScreens.EditScreen.name+"/$articleId")
       // 3
      }) {
       Text("Edit")
      }

      Divider()

      DropdownMenuItem(onClick = {
       expanded.value = false

       viewModel.openDialogState.value=true 

      }) {
       Text("Delete")
      }
     }
    }

   }
  }
 }

 ) {
  if(viewModel.openDialogState.value){
   presentDialogue(articleId = articleId, mContext = mContext)
  }
  when (articleResponse) {
   is Loading -> ProgressBar()
   is Success ->Column() {
    val newArticle = articleResponse.data.firstOrNull { article->
     article.id == articleId
    }
    if (newArticle != null) {
     ArticleHeader(image = newArticle.image)
     ArticleTitle(title = newArticle.title, author = newArticle.author)
    ArticleContent(content = newArticle.content)

    } else {
    Text("Article Not Found");
    }

   }


   is Error -> Utils.printError(articleResponse.message)
  }
 }

  }





@Composable
 private fun ArticleHeader(image:String?){
 Image(
  painter = rememberAsyncImagePainter(model = image),
  contentDescription = null,
  modifier = Modifier
   .fillMaxWidth()
   .height(195.dp),
  contentScale = ContentScale.Crop,
 )
}

@Composable
 private fun ArticleTitle(title:String?, author:String?){
 Column(modifier = Modifier.padding(start = 8.dp, end =8.dp,bottom=4.dp,top= 16.dp)) {
  Text(
   text = title.toString(),
   style = MaterialTheme.typography.h3,
   fontWeight = FontWeight.Bold
  )
 }
 Row(Modifier.padding(start = 8.dp, end =8.dp,bottom=4.dp,top= 4.dp)){
  Text(
   text = "Author :",
   style = MaterialTheme.typography.h4,
   fontWeight = FontWeight.Bold
  )
  Text(
   text = author.toString(),
   style = MaterialTheme.typography.h4,
   fontWeight = FontWeight.Bold
  )
 }
}
@Composable
private fun ArticleContent(content:String?) {
 Column(modifier = Modifier.padding(start = 4.dp, end = 2.dp)) {
  Text(
   text = content.toString(),
   style = MaterialTheme.typography.h6,
   color = Color.DarkGray
  )
 }
}
 @Composable
 private fun presentDialogue(viewModel: ArticleViewModel = hiltViewModel(), articleId: String?, mContext: Context) {
  if (viewModel.openDialogState.value) {
   AlertDialog(

    onDismissRequest = {
     viewModel.openDialogState.value = false
    },
    title = { Text("Are you sure want to delete this article ?") },
    confirmButton = {
     TextButton(onClick = {
      viewModel.openDialogState.value = false
      viewModel.deleteArticle(articleId.toString())
      showDeleteMessage(mContext)
     })
     { Text(text = "Yes") }
    },
    dismissButton = {
     TextButton(onClick = { viewModel.openDialogState.value = false }) {
      Text(text = "No")
     }
    }

   )
  }
 }

private fun showDeleteMessage(mContext: Context){

 Toast.makeText(mContext, "Article is deleted", Toast.LENGTH_SHORT).show()
}

 







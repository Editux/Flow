package com.example.flow.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.flow.presentation.screens.navigation.NavScreens

@Composable
fun CreateScreen(navController: NavController, viewModel: ArticleViewModel = hiltViewModel()) {
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImage = uri
    }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current
Scaffold(topBar ={
    //TopBar
    TopAppBar(backgroundColor = Color(0xfff48fb1),
        elevation = 0.dp ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                tint= Color.White ,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                })
            Spacer(modifier = Modifier.width(150.dp))
            Text(text = "Flow", fontWeight = FontWeight.Bold, color = Color.White)
        }

    }
}
) {
    Column(modifier= Modifier
        .padding(all = 50.dp)
        .verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(10.dp))
        ImageContent(selectedImage) {
            launcher.launch("image/*")
        }
        Spacer(Modifier.height(20.dp))
        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = { Text(text = "Your Label") },
            placeholder = { Text(text = "Title")},
            colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027),
            )
        )
        Spacer(Modifier.height(15.dp))
        TextField(
            value = author,
            onValueChange = {
                author = it
            },
            label = { Text(text = "Author") },
            placeholder = { Text(text = "Author")},
            colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027)  )

        )
        Spacer(Modifier.height(15.dp))
        TextField(
            value = category,
            onValueChange = {
                category = it
            },
            label = { Text(text = "Category") },
            placeholder = { Text(text = "Category")},
            colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027))
        )
        Spacer(Modifier.height(15.dp))
        TextField(
            value = content,
            onValueChange = {
                content = it
            },
            label = { Text(text = "Content") },
            placeholder = { Text(text = "Content")},
            colors = TextFieldDefaults.textFieldColors(focusedLabelColor = Color(0xFF560027))
        )
        Spacer(Modifier.height(40.dp))
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF560027),
           contentColor = Color.White ),
            onClick = {
                    viewModel.addArticle(selectedImage.toString(),title,author,category,content)
                Toast.makeText(context,"Article is added to the database",Toast.LENGTH_SHORT).show()
                navController.navigate(NavScreens.HomeScreen.name)
            },
        ) {
            Text(text = "Save")
        }

}



}
}
@Composable
private fun ImageContent(
    selectedImage: Uri? = null,
    onImageClick: () -> Unit
) {
            if (selectedImage != null)
                Image(
                    painter = rememberAsyncImagePainter(model = selectedImage),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clickable {
                            onImageClick()
                        })
            else
                OutlinedButton(onClick = onImageClick, colors = ButtonDefaults.buttonColors(
                    contentColor =  Color(0xFF560027), backgroundColor =Color.White ),) {
                    Text(text = "Choose Image")
                }

    }

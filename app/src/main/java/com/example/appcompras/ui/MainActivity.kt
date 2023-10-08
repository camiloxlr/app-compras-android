package com.example.appcompras.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appcompras.model.ItemData
import com.example.appcompras.model.RequestStatus
import com.example.appcompras.ui.theme.AppComprasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppComprasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GroceryListScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val data = viewModel.state

    var newItem by remember { mutableStateOf((""))}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar (
            title = { Text(text = "Lista de Compras", color = Color.Black) },
            Modifier.background(Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputSection(
            newItem,
            onNewItemChange = {
                newItem = it
            },
            onAddItemClick = {
                if (newItem.isNotBlank()) {
                    viewModel.addItem(newItem)
                    newItem = ""
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoadingProgressBar(data.value.isLoading)
        
        GroceryListSection(
            data,
            onItemCheckedChanged = {
                viewModel.updateItem(it)

            },
            onItemClick = {
                viewModel.removeItem(it)
            }
        )
    }
}

@Composable
fun LoadingProgressBar(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp).padding(16.dp)
        )
    }
}

@Composable
fun InputSection(
    newValue: String,
    onNewItemChange: (String) -> Unit,
    onAddItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlaceholderTextField(
            value = newValue,
            onValueChange = {
                onNewItemChange(it)
            },
            textStyle = MaterialTheme.typography.body1,
            placeholder = "Adicionar novo",
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)

        )

        Button(
            onClick = {
                onAddItemClick() // Call the lambda when the button is clicked
            }
        ) {
            Text(text= "Adicionar")
        }
    }
}

@Composable
fun GroceryListSection(
    list: State<RequestStatus>,
    onItemCheckedChanged: (ItemData) -> Unit,
    onItemClick: (ItemData) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        items(list.value.data) { item ->
            GroceryItemCard(item, onItemCheckedChanged, onItemClick)
        }
    }
}

@Composable
fun GroceryItemCard(item: ItemData, onItemCheckedChanged: (ItemData) -> Unit, onItemClick: (ItemData) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium, // Set the shape here
        elevation = 8.dp, // Set the shadow elevation here
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = item.checked, onCheckedChange = {
                onItemCheckedChanged(item)
            },
                modifier = Modifier.padding(end = 16.dp)
            )

            Text(text = item.title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1,
            textDecoration = if (item.checked) TextDecoration.LineThrough else TextDecoration.None)
            
            IconButton(onClick = { onItemClick(item) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar")
            }
        }
        
    }
}

@Composable
fun PlaceholderTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    placeholder: String,

) {

    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = if (isFocused) MaterialTheme.colors.primary else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                }
                .focusRequester(FocusRequester())
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = textStyle.copy(color = Color.Gray),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppComprasTheme {

    }
}
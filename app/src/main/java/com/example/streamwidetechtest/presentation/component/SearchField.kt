package com.example.streamwidetechtest.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.streamwidetechtest.R


@Composable
fun SearchField(widthFraction: Float, placeHolder: String, initialText: String, onTextChange: (String) -> Unit) {
    // State for the text value
    var text by remember { mutableStateOf(TextFieldValue(initialText)) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(widthFraction).clip(RoundedCornerShape(16.dp)),
        value = text,
        onValueChange = {
            text = it
            onTextChange(text.text)
        },
        placeholder = {
            Text(text = placeHolder)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search"
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor =  Color(0xFFe8f1f3),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewSearchField() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        SearchField(widthFraction = .8f, placeHolder = "Search", ""){}
    }
}
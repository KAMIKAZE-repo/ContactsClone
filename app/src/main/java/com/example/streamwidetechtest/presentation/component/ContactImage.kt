package com.example.streamwidetechtest.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.streamwidetechtest.R

@Composable
fun ContactImage(uri: String?) {
    Image(
        painter = if (uri != null) {
            rememberImagePainter(
                data = uri,
            )
        } else {
               painterResource(id = R.drawable.placeholder_person)
        },
        contentDescription = null,
        modifier = Modifier.size(48.dp).clip(CircleShape)
    )
}
package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felisreader.manga.domain.model.Manga

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleField(manga: Manga) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = manga.title,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            lineHeight = 30.sp
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = manga.originalTitle,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        AssistChip(
            modifier = Modifier
                .widthIn(50.dp, 200.dp),
            onClick = { /*TODO*/ },
            label = {
                Text(
                    text = manga.author.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(0.dp)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Person icon"
                )
            }
        )
    }
}
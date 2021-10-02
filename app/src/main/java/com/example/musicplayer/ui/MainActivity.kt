package com.example.musicplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.OnError
import com.example.musicplayer.OnSuccess
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.SongRepository
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.viewModel.SongViewModel
import com.example.musicplayer.viewModel.SongViewModelFactory
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {

    val viewModelProviderFactory = SongViewModelFactory(SongRepository())
    lateinit var songViewModel: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songViewModel = ViewModelProvider(this,viewModelProviderFactory).get(SongViewModel::class.java)

        setContent {
            MusicPlayerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SongList(songViewModel)
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun SongList(
    songViewModel: SongViewModel
){

    when (val songList = songViewModel.getAllSongs().collectAsState(initial = null).value) {

        is OnError -> {
            Text(text = "Please try after sometime")
        }

        is OnSuccess -> {
            val listOfSongs = songList.querySnapshot?.toObjects(Song::class.java)
            listOfSongs?.let {
                Column {
                    Text(
                        text = "All Songs",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(listOfSongs) {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), shape = RoundedCornerShape(16.dp)) {
                                SongDetails(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SongDetails(song: Song) {
    val songCoverImageSize = 80.dp
    Column(modifier = Modifier.clickable {
    }) {
        Row(modifier = Modifier.padding(12.dp)) {
            CoilImage(
                data = song.image,
                contentDescription = "Song cover page",
                fadeIn = true,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(songCoverImageSize)
            )

            Column {
                Text(text = song.name, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                Text(text = song.artistName, style = TextStyle(fontWeight = FontWeight.Light, fontSize = 12.sp))
            }
        }
    }
}
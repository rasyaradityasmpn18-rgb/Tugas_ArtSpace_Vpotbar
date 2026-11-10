package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

// Model data untuk menyimpan informasi karya seni
data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Composable
fun ArtSpaceApp() {
    // State untuk melacak indeks karya seni yang sedang aktif (mulai dari 0)
    var currentIndex by remember { mutableStateOf(0) }

    // Daftar 5 contoh karya seni (pastikan kamu punya resource gambar R.drawable.artwork1 s.d artwork5)
    // Jika menggunakan template bawaan Android Studio, bisa gunakan R.drawable.android_i_a atau sejenisnya
    val artworks = listOf(
        Artwork(R.drawable.artwork1, "Foto Bersama", "Rayhan Hannan", "Pemain Persija Jakarta & Tim Nasional Indonesia"),
        Artwork(R.drawable.artwork2, "Foto Bersama", "Carlos Pena", "Mantan Pelatih Persija Jakarta"),
        Artwork(R.drawable.artwork3, "Foto Bersama", "Andritany", "Kiper Persija Jakarta & Kiper Timnas Indonesia"),
        Artwork(R.drawable.artwork4, "Foto Bersama", "Fandi Ahmad", "Pernha Membela Tim Nasional Indonesia U17"),
        Artwork(R.drawable.artwork5, "Foto Bersama", "Teman-Teman", "Di Persija SS Sawangan Depok")
    )

    val currentArtwork = artworks[currentIndex]

    // Layout utama menggunakan Column dengan kemampuan scroll untuk menyesuaikan berbagai ukuran layar (Tablet/Desktop/HP)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Bagian Image dengan Card untuk memberikan efek bayangan (shadow) di sekitarnya
        Card(
            modifier = Modifier
                .padding(16.dp)
                .size(300.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Memberikan efek bayangan
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = currentArtwork.imageRes),
                    contentDescription = currentArtwork.title,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 2. Bagian Deskripsi / Judul karya seni (Kotak abu-abu muda di bawah gambar)
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            color = Color((0xFFECEEF4)), // Warna latar belakang deskripsi
            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = currentArtwork.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = currentArtwork.artist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = " (${currentArtwork.year})",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Bagian Tombol Previous dan Next menggunakan Row agar berdampingan
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    // Logika tombol Previous: jika di awal, kembali ke karya terakhir (index 4)
                    currentIndex = if (currentIndex > 0) currentIndex - 1 else artworks.size - 1
                },
                modifier = Modifier.width(120.dp)
            ) {
                Text(text = "Previous")
            }

            Button(
                onClick = {
                    // Logika tombol Next: jika di akhir, kembali ke karya pertama (index 0)
                    currentIndex = if (currentIndex < artworks.size - 1) currentIndex + 1 else 0
                },
                modifier = Modifier.width(120.dp)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240") // Preview untuk ukuran Tablet/Desktop
@Preview(showBackground = true) // Preview untuk ukuran HP standar
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        Surface {
            ArtSpaceApp()
        }
    }
}
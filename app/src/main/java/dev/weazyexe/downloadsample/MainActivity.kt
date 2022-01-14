package dev.weazyexe.downloadsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.downloadsample.ui.theme.DownloadsampleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val downloader = FileDownloader(applicationContext)

        setContent {
            DownloadsampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Downloader {
                        downloader.download(DUMMY_PDF_FILE_URL, "kek.pdf")
                        downloader.download(DUMMY_PDF_FILE_URL, "kek1.pdf")
                    }
                }
            }
        }
    }

    companion object {

        private const val DUMMY_PDF_FILE_URL =
            "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    }
}

@Composable
fun Downloader(onDownloadClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onDownloadClick.invoke() }
        ) {
            Text(text = "Download file")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DownloadsampleTheme {
        Downloader()
    }
}
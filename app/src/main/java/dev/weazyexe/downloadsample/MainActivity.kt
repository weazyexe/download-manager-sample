package dev.weazyexe.downloadsample

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
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

    private var downloadId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    Log.d("DOWNLOADER", "done!")
                }
            }
        }

        setContent {
            DownloadsampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Downloader {
                        val request = DownloadManager.Request(Uri.parse(DUMMY_PDF_FILE_URL))
                            .setDescription("Dummy File")
                            .setTitle("dummy.pdf")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(
                                Environment.DIRECTORY_DOWNLOADS,
                                "dummy.pdf"
                            )

                        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        downloadId = manager.enqueue(request)
                        registerReceiver(
                            receiver,
                            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                        )
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
package dev.weazyexe.downloadsample

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.util.Log

private typealias FileId = Long

/**
 * File downloader via [DownloadManager]
 *
 * @param context application context
 */
class FileDownloader(private val context: Context) {

    /**
     * Download a file
     *
     * @param url file URL
     * @param fileName file name in device's file system
     */
    fun download(url: String, fileName: String) {
        val receiver = prepareReceiver()
        val request = prepareRequest(Uri.parse(url), fileName)
        val manager = prepareDownloadManager()

        manager.enqueue(request)
        context.registerReceiver(
            receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun prepareReceiver(): BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.d("DOWNLOADER", "Downloading file with ID=$id has finished!")
            context?.unregisterReceiver(this)
        }
    }

    private fun prepareRequest(uri: Uri, fileName: String): DownloadManager.Request =
        DownloadManager.Request(uri)
            .setTitle(fileName)
            .setDescription(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

    private fun prepareDownloadManager(): DownloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
}
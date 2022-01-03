package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import tech.hostlematedevelopers.hostelmate.R

class AddTiffin : AppCompatActivity() {
    private lateinit var webViewAddTiffin: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tiffin)

        webViewAddTiffin = findViewById(R.id.webViewAddTiffin)
        webViewAddTiffin.webViewClient = WebViewClient()

        webViewAddTiffin.apply {
            loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSed04xtccUeQk7AN4EwPyqrOBuNkJnRyKm6H98StJqqhOhwaw/viewform")
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}
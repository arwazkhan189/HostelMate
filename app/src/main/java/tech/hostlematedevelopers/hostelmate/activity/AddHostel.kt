package tech.hostlematedevelopers.hostelmate.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import tech.hostlematedevelopers.hostelmate.R

class AddHostel : AppCompatActivity() {
    private lateinit var webViewAddHostel: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hostel)

        webViewAddHostel = findViewById(R.id.webViewAddHostel)
        webViewAddHostel.webViewClient = WebViewClient()

        webViewAddHostel.apply {
            loadUrl("            https://docs.google.com/forms/d/e/1FAIpQLScShCs22mptuMDNtiuPBO7uCZnlFVfVZ_OVzf95XzfYwG055A/viewform")
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}
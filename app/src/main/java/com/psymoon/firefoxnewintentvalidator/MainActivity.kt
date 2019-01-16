package com.psymoon.firefoxnewintentvalidator

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

private const val YOUTUBE = "https://youtube.com/tv"

class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        // We override onSaveInstanceState to not save state (for handling Clear Data), so startup flow
        // goes through onCreate.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlInputEdit.addTextChangedListener(this)
        urlInputButton.setOnClickListener(this)
        openYoutubeButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var url: String = when (v?.id) {
            R.id.urlInputButton -> urlInputEdit.text.toString()
            R.id.openYoutubeButton -> YOUTUBE
            else -> "https://www.mozilla.org"
        }

        openLocalFirefoxWithUrl(url)
    }

    override fun afterTextChanged(s: Editable?) {
        if (!s.toString().startsWith("https://") && !s.isNullOrEmpty()) {
            s!!.insert(0, "https://")
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    private fun openLocalFirefoxWithUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(url)

        startActivity(intent)
    }

    private fun isAppInstalled(packageName: String) : Boolean {
        val pm = applicationContext.packageManager
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (ex: Exception) {

        }
        return false
    }
}
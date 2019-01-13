package com.shliama.augmentedvideotutorial

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private val openGlVersion by lazy {
        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (openGlVersion.toDouble() >= MIN_OPEN_GL_VERSION) {
            supportFragmentManager.inTransaction { replace(R.id.fragmentContainer, ArVideoFragment()) }
        } else {
            AlertDialog.Builder(this)
                .setTitle("Device is not supported")
                .setMessage("OpenGL ES 3.0 or higher is required. The device is running OpenGL ES $openGlVersion.")
                .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                .show()
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    companion object {
        private const val MIN_OPEN_GL_VERSION = 3.0
    }
}
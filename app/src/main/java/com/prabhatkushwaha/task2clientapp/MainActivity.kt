package com.prabhatkushwaha.task2clientapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.prabhatkushwaha.task2serverapp.IAIDLAppRotation

class MainActivity : AppCompatActivity() {
    private var mService: IAIDLAppRotation? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mService = IAIDLAppRotation.Stub.asInterface(p1)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService(Intent("com.data.AIDLService").apply {
            setPackage("com.prabhatkushwaha.task2serverapp")
        }, mConnection, BIND_AUTO_CREATE)
        val tvRotationAxis: TextView = findViewById(R.id.tvRotationAxis)
        findViewById<AppCompatButton>(R.id.btShowRotation).setOnClickListener {
            if (mService != null) {
                tvRotationAxis.setText(mService?.rotation)
            } else {
                tvRotationAxis.setText("")
                Toast.makeText(this, "please wait", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
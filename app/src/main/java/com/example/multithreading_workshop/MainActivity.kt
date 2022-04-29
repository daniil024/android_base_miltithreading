package com.example.multithreading_workshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var threadButton: Button? = null
    private var threadTextView: TextView? = null

    private val mHandler = MyHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        threadButton?.setOnClickListener{
//            startThread()
            startRunnable()
        }
    }

    override fun onDestroy() {
        threadButton = null
        threadTextView = null
        super.onDestroy()
    }

    private fun findViews() {
        threadButton = findViewById(R.id.thread_button)
        threadTextView = findViewById(R.id.thread_text_view)
    }

    private fun startThread() {
        printMessage(getString(R.string.wait))
        MyThread().start()
    }

    private fun startRunnable() {
        printMessage(getString(R.string.wait))
        MyRunnable().run()
    }

    private fun printMessage(mes: String) {
        threadTextView?.text = mes
    }

    inner class MyHandler : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            printMessage(msg.data.getString(MESSAGE_KEY, ""))
        }
    }

    inner class MyThread : Thread() {
        override fun run() {
            Log.i("my_log:", currentThread().name)
            sleep(4000)
            val msg = Message.obtain().also {
                it.data = Bundle().apply {
                    putString(MESSAGE_KEY, "myMessageThread")
                }
            }
            mHandler.sendMessage(msg)
        }
    }

    inner class MyRunnable : Runnable {
        override fun run() {
            Log.i("my_log:", Thread.currentThread().name)
            Thread.sleep(4000)
            val mes = Message.obtain().apply {
                data = Bundle().apply {
                    putString(MESSAGE_KEY, "myMessageRunnable")
                }
            }
            mHandler.sendMessage(mes)

        }
    }

    companion object {
        private const val MESSAGE_KEY = "key"
    }
}
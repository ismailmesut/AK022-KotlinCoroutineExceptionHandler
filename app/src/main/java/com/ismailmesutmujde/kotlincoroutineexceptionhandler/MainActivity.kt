package com.ismailmesutmujde.kotlincoroutineexceptionhandler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Coroutinelerde hatayı yakalamanın doğru yolu; CoroutineExceptionHandler kullanmaktır.

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("exception: " + throwable.localizedMessage)
        }
        /*
        lifecycleScope.launch(handler) {
            throw Exception("error")
        }

         */
        /*
        lifecycleScope.launch(handler) {
            launch {
                throw Exception("error2")
            }
            launch {
                delay(500L)
                println("this is executed")
            }
        }
         */


        // Birden fazla launch kullanıyorsak; supervisorScope kullanmak gerekir.
        lifecycleScope.launch(handler) {
            supervisorScope {
                launch {
                    throw Exception("error2")
                }
                launch {
                    delay(500L)
                    println("this is executed")
                }
            }
        }


        CoroutineScope(Dispatchers.Main + handler).launch {
            launch {
                throw Exception("error in a coroutine scope")
            }
        }
    }
}
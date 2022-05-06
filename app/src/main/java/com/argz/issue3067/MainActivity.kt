package com.argz.issue3067

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val TAG = "remoteConfig"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            remoteConfig()
        }
    }

    /***
     * The timeout of 5 seconds is not followed, instead 60 seconds is being used
     */
    fun remoteConfig() {
        Log.d(TAG, "remoteConfig: ------starting------")
        val remoteConfigFetchInterval: Long = 5
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setFetchTimeoutInSeconds(10)
                .build()
        ).await()
        Log.d(TAG, "remoteConfig: added")
        firebaseRemoteConfig.fetch(remoteConfigFetchInterval).await()
        Log.d(TAG, "remoteConfig: fetching")
        firebaseRemoteConfig.activate().await()
        Log.d(TAG, "remoteConfig: activating")
    }

    private fun <T> Task<T>.await() = Tasks.await(this)
}
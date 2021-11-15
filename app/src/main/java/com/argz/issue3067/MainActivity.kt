package com.argz.issue3067

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class MainActivity : AppCompatActivity() {
    private val TAG = "remoteConfig"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            remoteConfig()
        }.start()
    }

    /***
     * The timeout of 5 seconds is not followed, instead 60 seconds is being used
     */
    fun remoteConfig() {
        Log.d(TAG, "remoteConfig: ------starting------")
        val timeout: Long = 5;

        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .setFetchTimeoutInSeconds(timeout)
                .build()
        )

        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Fetch and activate succeeded " + firebaseRemoteConfig.getDouble("random_value"))
                } else {
                    Log.d(TAG, "Fetch fails after 60 seconds instead of 5 seconds")
                }
            }
        }
}
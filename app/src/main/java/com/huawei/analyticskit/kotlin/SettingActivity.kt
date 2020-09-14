package com.huawei.analyticskit.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private val wrapper: HiAnalyticsWrapper by lazy {
        HiAnalyticsWrapper(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        wrapper.setUpUserId()

        btnSaveSettings.setOnClickListener {
            val strFavorSport = etFavouriteSport.text.toString().trim { it <= ' ' }
            // Save favorite sport by user setUserProperty
            wrapper.setUserProfile(strFavorSport)
        }
    }
}
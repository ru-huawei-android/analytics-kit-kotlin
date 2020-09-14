package com.huawei.analyticskit.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private val mWrapper: HiAnalyticsWrapper by lazy {
        HiAnalyticsWrapper(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mWrapper.setUpUserId()

        btnSaveSettings.setOnClickListener {
            val strFavorSport = etFavouriteSport.text.toString().trim { it <= ' ' }
            // Save favorite sport by user setUserProperty
            mWrapper.setUserProfile(strFavorSport)
        }
    }
}
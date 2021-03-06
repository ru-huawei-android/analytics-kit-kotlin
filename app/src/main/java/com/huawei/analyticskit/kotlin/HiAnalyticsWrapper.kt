package com.huawei.analyticskit.kotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import com.huawei.hms.analytics.type.HAEventType
import com.huawei.hms.analytics.type.HAParamType
import com.huawei.hms.api.ConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HiAnalyticsWrapper(private val context: Context) {

    private val sdf: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)

    private lateinit var instance: HiAnalyticsInstance

    init {
        // Initiate Analytics Kit
        // Enable Analytics Kit Log
        HiAnalyticsTools.enableLog()

        if (isHmsAvailable(context)) {
            // Generate the Analytics Instance
            instance = HiAnalytics.getInstance(context)
        }
    }

    private fun isHmsAvailable(context: Context): Boolean {
        return HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(context) == ConnectionResult.SUCCESS
    }

    /**
     * Report a customized Event
     * Event Name: Answer
     * Event Parameters:
     * -- question: String
     * -- answer: String
     * -- answerTime: String
     */
    fun reportAnswerEvt(question: String?, answer: String?) {

        // Initiate Parameters
        val bundle = Bundle().apply {
            putString(EVENT_QUESTION, question)
            putString(EVENT_QUESTION_ANSWER, answer)
            putString(EVENT_QUESTION_TIME, sdf.format(Date()))
        }

        // Report a customized Event
        instance.onEvent(EVENT_ANSWER, bundle)
    }

    /**
     * Report score by using SUBMITSCORE Event
     */
    fun postScore(score: Int) {
        // Initiate Parameters
        val bundle = Bundle().apply {
            putLong(HAParamType.SCORE, score.toLong())
        }

        // Report a predefined Event
        instance.onEvent(HAEventType.SUBMITSCORE, bundle)
    }

    fun setUserProfile(sport: String?) {
        instance.setUserProfile(USER_PROFILE_TAG, sport)
    }

    fun setUpUserId() {
        val id = HmsInstanceId.getInstance(context).id
        instance.setUserId(id)
        Log.i(TAG, "AAID: $id")
    }

    companion object {
        private const val TAG = "HI_ANALYTICS_WRAPPER"
        private const val USER_PROFILE_TAG = "favor_sport"
        private const val EVENT_ANSWER = "Answer"
        private const val EVENT_QUESTION = "question"
        private const val EVENT_QUESTION_ANSWER = "answer"
        private const val EVENT_QUESTION_TIME = "answerTime"
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}
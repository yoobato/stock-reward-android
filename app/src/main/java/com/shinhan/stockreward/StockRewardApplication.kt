package com.shinhan.stockreward

import android.app.Application
import com.onesignal.OneSignal

class StockRewardApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
    }
}